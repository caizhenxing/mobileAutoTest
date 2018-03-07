package com.bmtc.svn.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.common.utils.SvnRepoTree;
import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnUserAuthzInfo;
import com.bmtc.svn.service.RepositoryService;
import com.bmtc.svn.service.SvnRepoService;
import com.bmtc.svn.service.SvnService;
import com.bmtc.svn.service.SvnUserRightService;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;


/**
 * SVN用户信息管理业务逻辑实现
 * @author lpf7161
 *
 */
@Service("SvnRepoService")
public class SvnRepoServiceImpl implements SvnRepoService {
	private static Logger logger = Logger.getLogger(SvnRepoServiceImpl.class);
	
	@Autowired
	private SvnRepoDao svnRepoDao;
	
	/**
	 * SVN服务层
	 */
	@Resource(name = "SvnService")
	private SvnService svnService;
	
	/**
	 * SVN权限层
	 */
	@Resource(name = "SvnUserRightService")
	private SvnUserRightService svnUserRightService;
	
	/**
	 * SVN仓库服务层
	 */
	@Autowired
	RepositoryService repositoryService;
	
	/**
	 * 系统配置信息服务层
	 */
	@Autowired
	private ConfigService configService;
	
	/**
	 * 配置文件信息
	 */
	@Autowired
	BMTCConfig bmtcConfig;
	
	/**
	 * 保存。<br>
	 * 数据库里已经存在相同的路径或url的项目，不可以保存。<br>
	 * 如果仓库不存在，自动创建。<br>
	 * 增加SVN仓库
	 * @param svnRepo, svnConfFilesLocation 
	 * @throws Exception 
	 */
	@Transactional
	@Override
	public int addSvnRepo(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception {
		logger.info("SvnRepoServiceImpl.addSvnRepo() start");	
		
		int cnt = 0;
		int res = 0;
		//根据SVN库名查询是否存在该SVN仓库
		cnt = svnRepoDao.isExistSvnRepo(svnRepo.getSvnRepoName());
		if(cnt != 0) {
			logger.error("svn_repo表中已存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			return -2;
		}
		
		// svn仓库路径 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoPath())) {
			svnRepo.setSvnRepoPath(StringUtils.replace(svnRepo.getSvnRepoPath(), "\\", "/"));
		}
		
		// url 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoUrl())) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "\\", "/"));
		}
	
		// 添加svn://
		if(!svnRepo.getSvnRepoUrl().startsWith("svn://") && !svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl("svn://" + svnRepo.getSvnRepoUrl());
		}
		
		// url 把SVN://替换为svn://
		if(svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "SVN://", "svn://"));
		}
		
		// 是否可以增加svn仓库
		// insert为true时，说明svn仓库表中不存在名为svnRepo.getSvnRepoName()的svn仓库，否则存在
		boolean insert = svnRepoDao.querySvnRepoBySvnRepoName(svnRepo.getSvnRepoName()) == null;
		if (insert) {
			// 数据库里已经存在相同的路径或url的svn仓库
			if (svnRepoDao.getCount(svnRepo.getSvnRepoPath(), svnRepo.getSvnRepoUrl()) > 0) {
				logger.error("数据库里已经存在相同的路径或url的仓库项目，请检查路径或url");
				return -3;
			}
		} else {
			// 数据库里已经存在多个相同的路径或url的svn仓库
			if (svnRepoDao.getCount(svnRepo.getSvnRepoPath(), svnRepo.getSvnRepoUrl()) > 1) {
				logger.error("数据库里已经存在多个相同的路径或url的仓库项目，请检查路径或url");
				return -4;
			}
		}
		
		// 根据svn url截取svn服务器的IP地址
		String svnServerIp = svnRepo.getSvnRepoUrl().split("//")[1].split("/")[0];

		// 根据svn的IP地址查询svn服务器相关信息
		ConfigInfoDO configInfoDO = configService.getConfigInfo(svnServerIp);
		
		int port = Integer.parseInt(bmtcConfig.getPort());
		
		// 建立连接—远程服务器IP和端口号
		Connection con = new Connection(svnServerIp, port);
		
		// 进行连接操作
		con.connect();
		
		// svn服务器的用户名
		String svnServerUserName = configInfoDO.getSvnServerUserName();
		
		// svn服务器的登录口令，数据库中为密文存储，需要进行解密
		String svnServerPassword = EncryptUtil.decrypt(configInfoDO.getSvnServerPassword());
		
		// 进行连接访问授权验证
		boolean isAuthed = con.authenticateWithPassword(svnServerUserName, 
				svnServerPassword);
		
		if(isAuthed == false) {
			logger.error("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
			throw new RuntimeException("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
		} else {
			logger.info("svn服务器的用户名和登录口令认证成功");
		}
		
		// 建立SFTPv3客户端对象
		SFTPv3Client sFTPv3Client = new SFTPv3Client(con);
		
		// 开启会话，执行原生linux命令
		Session sess = con.openSession();
		
		// 判断远程仓库是否存在
		boolean iSExistSvnRepoPath = this.exists(svnRepo.getSvnRepoPath(), svnServerIp, 
				svnServerUserName, svnServerPassword);
		
		boolean createNewRepoSucceed = false;
		
		try {
			
			Vector<?> contents = null;
			
			if(iSExistSvnRepoPath == true) {
				// 判断远程仓库是否存在，判断svnRepoPath路径下是否存在文件，默认只有.和..两项
				contents = (Vector<?>) sFTPv3Client.ls(svnRepo.getSvnRepoPath());
			}
			
			// 不存在仓库，则新建仓库
			if(iSExistSvnRepoPath == false || contents.size() == 2) {		
				// 调用svn命令，创建远程svn仓库
				sess.execCommand("svnadmin create " + svnRepo.getSvnRepoPath());
/*				// 修改目录权限
				sess.execCommand("chmod 777 " + svnRepo.getSvnRepoPath());*/
				// 显示执行命令后的信息
				InputStream stdout = new StreamGobbler(sess.getStdout());
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
				while (true) {
					String line = reader.readLine();
					if (line == null) {
						logger.info("远程服务器返回信息:空");
						break;
					}
					logger.info("远程服务器返回信息:" + line);
				}
				if(sess.getExitStatus() == 0) {
					createNewRepoSucceed = true;
				}
				// 获得退出状态
				logger.info("ExitCode: " + sess.getExitStatus());
			} 
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			if(sFTPv3Client != null) {
				sFTPv3Client.close();
			}
			
			//关闭会话
			sess.close();
			
			// 关闭连接
			con.close();
		}
		
		// 将SVN仓库信息入库
		res = svnRepoDao.addSvnRepo(svnRepo);
		logger.info("添加" + res + "条SVN仓库信息完成");
		
		// 增加默认的权限
		SvnUserAuthzInfo svnUserAuthzInfo = new SvnUserAuthzInfo();
		svnUserAuthzInfo.setSvnRepoName(svnRepo.getSvnRepoName());
		svnUserAuthzInfo.setSvnRepoPath(this.svnUserRightService.formatRes(svnRepo, "/"));
		svnUserAuthzInfo.setSvnUserAuthz("rw");
	
		// 将数据库中的svn用户和权限信息写入passwd和authz文件中，并上传到远程svn服务器对应仓库的conf目录下
		svnService.exportConfigToSvnServer(svnRepo.getSvnRepoName(), bmtcConfig.getSvnConfFilesLocation());	

		logger.info("SvnRepoServiceImpl.addSvnRepo() end");
		if(res > 0 && createNewRepoSucceed == true) {
			return -5;
		} else if(res > 0 && createNewRepoSucceed == false) {
			return -6;
		}

		return res;
	}
	

	/**
	 * 根据SVN库名查询是否存在名为svnRepoName的SVN仓库
	 * @param svnRepoName
	 */
	@Transactional
	@Override
	public int isExistSvnRepo(String svnRepoName) {
		logger.info("SvnRepoServiceImpl.isExistSvnRepo() start");
		int res = 0;
		res = svnRepoDao.isExistSvnRepo(svnRepoName);
		logger.info("SvnRepoServiceImpl.isExistSvnRepo() end");
		return res;
	}

	/**
	 * 根据SVN库名删除SVN仓库
	 * @param svnRepoName
	 */
	@Transactional
	@Override
	public int deleteSvnRepo(String svnRepoName) {
		logger.info("SvnRepoServiceImpl.deleteSvnRepo() start");
		int cnt = 0;
		int res = -1;
		//根据SVN库名查询是否存在该SVN仓库
		cnt = svnRepoDao.isExistSvnRepo(svnRepoName);
		if(cnt == 0) {
			logger.error("svn_repo表中不存在svn库名为'" + svnRepoName + "'的记录");
		} else {
			res = 0; 
			//cnt不为0时，将删除SVN仓库写入数据库
			res = svnRepoDao.deleteSvnRepo(svnRepoName);
			if(res == 0) {
				logger.error("删除操作异常");
			} else {
				logger.info("删除SVN用户写入数据库成功");
			}
			logger.info("SvnRepoServiceImpl.deleteSvnRepo() end");
		}
		
		return res;
	}

	/**
	 * 根据SVN库名修改SVN仓库
	 * @param svnRepo
	 */
	@Transactional
	@Override
	public int updateSvnRepo(SvnRepo svnRepo) {
		logger.info("SvnRepoServiceImpl.updateSvnRepo() start");
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoPath())) {
			svnRepo.setSvnRepoPath(StringUtils.replace(svnRepo.getSvnRepoPath(), "\\", "/"));
		}
		
		// url 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoUrl())) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "\\", "/"));
		}
		
		// 添加svn://
		if(!svnRepo.getSvnRepoUrl().startsWith("svn://") && !svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl("svn://" + svnRepo.getSvnRepoUrl());
		}
		
		// url 把SVN://替换为svn://
		if(svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "SVN://", "svn://"));
		}
		
		int cnt = 0;
		int res = -1;
		//根据SVN库名查询是否存在该SVN仓库
		cnt = svnRepoDao.isExistSvnRepo(svnRepo.getSvnRepoName());
		if(cnt == 0) {
			logger.error("svn_repo表中不存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
		} else {
			res = 0;
			//将修改SVN仓库信息写入数据库
			res = svnRepoDao.updateSvnRepo(svnRepo);
			logger.info("根据svnRepoName修改" + res + "条SVN仓库完成");
		}
		
		logger.info("SvnRepoServiceImpl.updateSvnRepo() end");
		return res;
	}

	/**
	 * 保存SVN仓库，若存在此仓库（根据仓库名判断）则修改，否则新增
	 * @param svnRepo
	 * @return 保存记录条数
	 */
	@Transactional
	@Override
	public int saveSvnRepo(SvnRepo svnRepo) {
		logger.info("SvnRepoServiceImpl.saveSvnRepo() start");
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoPath())) {
			svnRepo.setSvnRepoPath(StringUtils.replace(svnRepo.getSvnRepoPath(), "\\", "/"));
		}
		
		// url 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoUrl())) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "\\", "/"));
		}
		
		// 添加svn://
		if(!svnRepo.getSvnRepoUrl().startsWith("svn://") && !svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl("svn://" + svnRepo.getSvnRepoUrl());
		}
		
		// url 把SVN://替换为svn://
		if(svnRepo.getSvnRepoUrl().startsWith("SVN://")) {
			svnRepo.setSvnRepoUrl(StringUtils.replace(svnRepo.getSvnRepoUrl(), "SVN://", "svn://"));
		}
		
		int cnt;
		int res = 0;
		//根据SVN库名查询是否存在该SVN仓库
		cnt = svnRepoDao.isExistSvnRepo(svnRepo.getSvnRepoName());
		if(cnt == 0) {
			//cnt为0时添加SVN仓库
			res = svnRepoDao.addSvnRepo(svnRepo);
			logger.info("添加" + res + "条SVN仓库信息完成");
		} else {
			//将修改SVN仓库信息写入数据库
			res = svnRepoDao.updateSvnRepo(svnRepo);
			logger.info("根据svnRepoName修改" + res + "条SVN仓库完成");
		}
		
		logger.info("SvnRepoServiceImpl.saveSvnRepo() end");
		return res;
	}
	
	/**
	 * 查询svn仓库记录数
	 * @param 
	 */
	@Transactional
	@Override
	public int countSvnRepo(Map<String, Object> params) {
		logger.info("SvnRepoServiceImpl.countSvnRepo() start");
		int res = 0;
		res = svnRepoDao.countSvnRepo(params);
		logger.info("SvnRepoServiceImpl.countSvnRepo() end");
		return res;
	}

	/**
	 * 查询svn仓库信息
	 * @param 
	 */
	@Override
	public List<SvnRepo> querySvnRepo(Map<String, Object> params) {
		logger.info("SvnRepoServiceImpl.querySvnRepo() start");
		//从数据库查询SVN仓库信息
		List<SvnRepo> list = svnRepoDao.querySvnRepo(params);
		logger.info("SvnRepoServiceImpl.querySvnRepo() end");
		return list;
	}

	/**
	 * 根据svn仓库名从svn仓库表中查询仓库信息
	 * @param svnRepoName
	 */
	@Override
	public SvnRepo querySvnRepoBySvnRepoName(String svnRepoName) {
		logger.info("SvnRepoServiceImpl.querySvnRepo() start");
		//从数据库查询SVN仓库信息
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoName(svnRepoName);
		logger.info("SvnRepoServiceImpl.querySvnRepo() end");
		return svnRepo;
	}
	
	/**
	 * 根据svn仓库名从svn仓库表中查询仓库id
	 * @param svnRepoName
	 */
	@Override
	public String querySvnRepoIdBySvnRepoName(String svnRepoName) {
		logger.info("SvnRepoServiceImpl.querySvnRepo() start");
		String res = null;
		res = svnRepoDao.querySvnRepoIdBySvnRepoName(svnRepoName);
		
		logger.info("SvnRepoServiceImpl.querySvnRepo() end");
		return res;
	}
	
	/**
	 * 获取项目的相对根路径.例如项目的path=e:/svn/projar，则返回projar。如果path为空，则返回项目ID
	 * @param pj 项目
	 * @return 项目的相对根路径
	 */
	@Override
	public String getRelateRootPath(SvnRepo svnRepo) {
		logger.info("SvnRepoServiceImpl.getRelateRootPath() start");
		String path = svnRepo.getSvnRepoPath();
		if(StringUtils.isBlank(path)){
			return svnRepo.getSvnRepoName();
		}
		
		path = StringUtils.replace(path, "\\", "/");
		
		while(path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		
		logger.info("SvnRepoServiceImpl.getRelateRootPath() end");
		return StringUtils.substringAfterLast(path, "/");
	}

	/**
	 * 根据SVN库id批量删除SVN仓库
	 * @param svnRepoIds
	 */
	@Transactional
	@Override
	public int batchRemoveSvnRepo(Long[] svnRepoIds) {
		logger.info("SvnRepoServiceImpl.batchRemoveSvnRepo() start");
		int count = svnRepoDao.batchRemoveSvnRepo(svnRepoIds);
		logger.info("SvnRepoServiceImpl.batchRemoveSvnRepo() end");
		return count;
	}

	/**
	 * 获取SVN仓库树形图数据
	 */
/*	@Override
	public Tree<SvnRepo> getTree() {
		logger.info("SvnRepoServiceImpl.getTree() start");
		List<Tree<SvnRepo>> trees = new ArrayList<Tree<SvnRepo>>();
		//设置树形图标题
		List<SvnRepo> svnRepos = svnRepoDao.querySvnRepo(new HashMap<String,Object>(5));
		for (SvnRepo svnRepo : svnRepos) {
				Tree<SvnRepo> tree = new Tree<SvnRepo>();
				tree.setId(String.valueOf(svnRepo.getId()));
				//tree.setParentId(svnRepo.getParentId().toString());
				tree.setText(svnRepo.getSvnRepoName());
				Map<String, Object> state = new HashMap<>(5);
				state.put("closed", true);
				tree.setState(state);
				trees.add(tree);
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<SvnRepo> t = BuildTree.build(trees);
		t.setText("SVN仓库");
		logger.info("SvnRepoServiceImpl.getTree() end");
		return t;
	}*/
	
	@Override
	public SvnRepoTree<SvnRepo> getTree() {
		logger.info("SvnRepoServiceImpl.getTree() start");
		List<SvnRepoTree<SvnRepo>> trees = new ArrayList<SvnRepoTree<SvnRepo>>();
		//设置树形图标题
		List<SvnRepo> svnRepos = svnRepoDao.querySvnRepo(new HashMap<String,Object>(5));
		for (SvnRepo svnRepo : svnRepos) {
			SvnRepoTree<SvnRepo> tree = new SvnRepoTree<SvnRepo>();
			tree.setId(String.valueOf(svnRepo.getId()));
			tree.setText(svnRepo.getSvnRepoName());
			tree.setSvnRepoPath(svnRepo.getSvnRepoPath());
			tree.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
			tree.setSvnRepoDes(svnRepo.getSvnRepoDes());
			Map<String, Object> state = new HashMap<>(5);
			state.put("closed", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		SvnRepoTree<SvnRepo> t = BuildTree.buildSvnRepoTree(trees);
		t.setText("SVN仓库");
		logger.info("SvnRepoServiceImpl.getTree() end");
		return t;
	}
	
	
	private Connection getConnection(String svnUrl, String svnServerUserName, String svnServerPassword) 
			throws IOException {
		logger.info("SvnRepoServiceImpl.getConnection() start");
		
		int port = Integer.parseInt(bmtcConfig.getPort());
		
		Connection conn = new Connection(svnUrl, port);

		// 进行连接操作
		conn.connect();

		boolean isAuthenticated = conn.authenticateWithPassword(svnServerUserName, svnServerPassword);
		if (isAuthenticated == false) {
			logger.error("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
			throw new IOException("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
		} else {
			logger.info("svn服务器的用户名和登录口令认证成功");
		}
		
		logger.info("SvnRepoServiceImpl.getConnection() end");
		return conn;
	}
	
	
	public boolean exists(String path, String svnUrl, String svnServerUserName, 
			String svnServerPassword) {
		logger.info("SvnRepoServiceImpl.exists() start");
		try {
			logger.info("SvnRepoServiceImpl.exists() end");
			getAttributes(path, svnUrl, svnServerUserName, svnServerPassword);
			return true;
		} catch (IOException ioErr) {
			logger.info("SvnRepoServiceImpl.exists() end");
			return false;
		}
		
	}
	
	private SFTPv3FileAttributes getAttributes(String fileName, String svnUrl, String svnServerUserName, 
			String svnServerPassword) throws IOException {
		logger.info("SvnRepoServiceImpl.getAttributes() start");
		Connection conn = getConnection(svnUrl, svnServerUserName, svnServerPassword);
		SFTPv3Client client = null;

		try {
			client = new SFTPv3Client(conn);

			logger.info("SvnRepoServiceImpl.getAttributes() end");
			// 检索文件的文件属性，返回 SFTPv3FileAttributes对象，主要记录目录、权限等信息。
			return client.stat(fileName);
		} finally {
			if (client != null) {
				client.close();
			}
			conn.close();
		}
	}
}
