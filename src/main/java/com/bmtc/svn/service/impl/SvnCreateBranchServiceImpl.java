package com.bmtc.svn.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3FileAttributes;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.bmtc.common.config.BMTCConfig;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.dao.SvnCreateBranchDao;
import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.dao.SvnUserRightDao;
import com.bmtc.svn.domain.SvnCreateBranch;
import com.bmtc.svn.domain.SvnCreateBranchInfo;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnUserRepoInfo;
import com.bmtc.svn.service.SvnCreateBranchService;
import com.bmtc.svn.service.SvnUserService;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SVN新建分支信息服务层service实现类
 * @author lpf7161
 */
@Transactional
@Service
public class SvnCreateBranchServiceImpl implements SvnCreateBranchService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SvnCreateBranchServiceImpl.class);
	
	@Autowired
	SvnCreateBranchDao svnCreateBranchDao;
	
	@Autowired
	private SvnRepoDao svnRepoDao;
	
	@Autowired
	private SvnUserRightDao svnUserRightDao;

	/**
	 * 系统配置信息服务层
	 */
	@Autowired
	private ConfigService configService;
	
	/**
	 * SVN用户服务层
	 */
	@Autowired
	private SvnUserService svnUserService;
	
	/**
	 * 配置文件信息
	 */
	@Autowired
	BMTCConfig bmtcConfig;
	
	/**
	 * 分隔符
	 */
	private static final String SEP = System.getProperty("line.separator");
	
	/**
	 * 保存svn新建分支信息
	 * @param svnCreateBranch
	 * @return count
	 * @throws Exception 
	 */
	@Override
	public String save(SvnCreateBranch svnCreateBranch) throws Exception {
		logger.info("SvnCreateBranchServiceImpl.save() start");
		// 根据仓库名获取仓库信息
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoName(svnCreateBranch.getSvnRepoName());
		svnCreateBranch.setSvnRepoId(svnRepo.getId());
		svnCreateBranch.setSvnRepoPath(svnRepo.getSvnRepoPath());
		svnCreateBranch.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		svnCreateBranch.setSvnRepoDes(svnRepo.getSvnRepoDes());
		
		// svnTrunk 把\替换为/
		if (StringUtils.isNotBlank(svnCreateBranch.getSvnTrunk())) {
			svnCreateBranch.setSvnTrunk(StringUtils.replace(svnCreateBranch.getSvnTrunk(), "\\", "/"));
		}
		
		// newBranch 把\替换为/
		if (StringUtils.isNotBlank(svnCreateBranch.getNewBranch())) {
			svnCreateBranch.setNewBranch(StringUtils.replace(svnCreateBranch.getNewBranch(), "\\", "/"));
		}
	
		// 根据svn url截取svn服务器的IP地址
		String svnServerIp = svnCreateBranch.getSvnRepoUrl().split("//")[1].split("/")[0];

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
		boolean iSExistSvnRepoPath = this.exists(svnCreateBranch.getSvnRepoPath(), svnServerIp, 
				svnServerUserName, svnServerPassword);
		
		boolean createNewBranchSucceed = false;
		
		// 根据svn的IP地址查询超级用户的svn用户名
		String svnRootUserName = configInfoDO.getSvnRootUserName(); 
		
		// 根据SVN用户名和库名查询SVN用户信息，获取超级用户在仓库svnCreateBranch.getSvnRepoName()()的svn密码
		SvnUserRepoInfo svnUserRepoInfo = svnUserService.querySvnUser(svnRootUserName, //默认为admin
				svnCreateBranch.getSvnRepoName());

		// 判断超级用户是否拥有相应仓库的根目录的读写权限
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("svnUserName", svnRootUserName);
		params.put("svnRepoName", svnCreateBranch.getSvnRepoName());
		params.put("svnPath", "[" + svnCreateBranch.getSvnRepoName() + ":/]");
		params.put("svnUserAuthz", "rw");
		// 根据SVN用户名、仓库名、svn路径、svn权限查询用户SVN权限总记录数
		int total = svnUserRightDao.countSvnRight(params);
		
		// 若超级用户没有相应svn仓库的根权限，则直接返回
		if(svnUserRepoInfo == null || svnUserRepoInfo.getSvnPassword() == null || total == 0) {	
			return "-1";
		}
		
		// 对svn用户密码进行解密
		svnUserRepoInfo.setSvnPassword(EncryptUtil.decrypt(svnUserRepoInfo.getSvnPassword()));
		String cmdInfo = "";
		
		try {
			
			Vector<?> contents = null;
			
			if(iSExistSvnRepoPath == true) {
				// 判断远程仓库是否存在，判断svnRepoPath路径下是否存在文件，默认只有.和..两项
				contents = (Vector<?>) sFTPv3Client.ls(svnCreateBranch.getSvnRepoPath());
			}
			
			// 存在仓库，则新建svn分支
			if(iSExistSvnRepoPath == true || contents.size() >= 2) {	
				// 调用svn命令，新建svn分支
				sess.execCommand("svn copy " + svnCreateBranch.getSvnTrunk() + " " + svnCreateBranch.getNewBranch()
						+ " -m" + " \"" + svnCreateBranch.getCreateBranchComment() + "\""
						+ " --username=" + svnRootUserName + " --password=" + svnUserRepoInfo.getSvnPassword());

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
					cmdInfo += line + SEP;
				}
				if(sess.getExitStatus() == 0) {
					createNewBranchSucceed = true;
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
		
		int count = 0;
		if(createNewBranchSucceed == true) {
			count = svnCreateBranchDao.save(svnCreateBranch);
			if(count > 0) {
				return cmdInfo;
			} else {
				return "-2";
			}
		}

		logger.info("SvnCreateBranchServiceImpl.save() end");
		return "-3";
	}

	/**
	 * 更新svn新建分支信息
	 * @param svnCreateBranch
	 * @return count
	 */
	@Override
	public int update(SvnCreateBranch svnCreateBranch) {
		logger.info("SvnCreateBranchServiceImpl.update() start");
		int count = svnCreateBranchDao.update(svnCreateBranch);
		logger.info("SvnCreateBranchServiceImpl.update() end");
		return count;
	}

	/**
	 * 通过svn仓库名查询svn新建分支信息
	 * @param svnRepoName
	 * @return svnCreateBranchInfo
	 */
	@Override
	public SvnCreateBranchInfo getSvnCreateBranchInfo(String svnRepoName) {		
		logger.info("SvnCreateBranchServiceImpl.getSvnCreateBranchInfo() start");
		SvnCreateBranchInfo svnCreateBranchInfo = svnCreateBranchDao.getSvnCreateBranchInfo(svnRepoName);
		logger.info("SvnCreateBranchServiceImpl.getSvnCreateBranchInfo() end");
		return svnCreateBranchInfo;
	}


	/**
	 * 通过svn仓库id、svn新建分支基线、svn需要新建的分支查询svn新建分支信息
	 * @param svnCreateBranch
	 * @return svnCreateBranchInfo
	 */
	@Override
	public SvnCreateBranchInfo querySvnCreateBranchInfo(SvnCreateBranch svnCreateBranch) {		
		logger.info("SvnCreateBranchServiceImpl.querySvnCreateBranchInfo() start");
		SvnCreateBranchInfo svnCreateBranchInfo = svnCreateBranchDao.querySvnCreateBranchInfo(svnCreateBranch);
		logger.info("SvnCreateBranchServiceImpl.querySvnCreateBranchInfo() end");
		return svnCreateBranchInfo;
	}
	
	/**
	 * 批量删除svn新建分支信息
	 * @param ids
	 * @return count
	 */
	@Override
	public int batchRemove(Long[] ids) {
		logger.info("SvnCreateBranchServiceImpl.batchremove() start");
		int count = svnCreateBranchDao.batchRemove(ids);
		logger.info("SvnCreateBranchServiceImpl.batchremove() end");
		return count;
	}

	/**
	 * 通过id获取svn新建分支信息表对象
	 * @param id
	 * @return svnCreateBranchInfo
	 */
	@Override
	public SvnCreateBranchInfo get(Long id) {
		logger.info("SvnCreateBranchServiceImpl.get() start");
		SvnCreateBranchInfo svnCreateBranchInfo = svnCreateBranchDao.get(id);
		// 根据仓库id获取仓库信息
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoId(svnCreateBranchInfo.getSvnRepoId());
		svnCreateBranchInfo.setSvnRepoName(svnRepo.getSvnRepoName());
		svnCreateBranchInfo.setSvnRepoPath(svnRepo.getSvnRepoPath());
		svnCreateBranchInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		svnCreateBranchInfo.setSvnRepoDes(svnRepo.getSvnRepoDes());
		logger.info("SvnCreateBranchServiceImpl.get() end");
		return svnCreateBranchInfo;
	}

	/**
	 * 查询svn新建分支信息
	 * @param map
	 * @return List<SvnCreateBranchInfo>
	 */
	@Override
	public List<SvnCreateBranchInfo> list(Map<String, Object> map) {
		logger.info("SvnCreateBranchServiceImpl.list() start");
		logger.info("SvnCreateBranchServiceImpl.list() end");
		return svnCreateBranchDao.list(map);
	}

	/**
	 * 统计svn新建分支信息总数
	 * @param map
	 * @return count
	 */
	@Override
	public int count(Map<String, Object> map) {
		logger.info("SvnCreateBranchServiceImpl.count() start");
		logger.info("SvnCreateBranchServiceImpl.count() end");
		return svnCreateBranchDao.count(map);
	}

	/**
	 * 删除svn新建分支信息
	 * @param id
	 * @return count
	 */
	@Override
	public int remove(Long id) {
		logger.info("SvnCreateBranchServiceImpl.remove() start");
		logger.info("SvnCreateBranchServiceImpl.remove() end");
		return svnCreateBranchDao.remove(id);
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
			logger.info(ioErr.getMessage());
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
