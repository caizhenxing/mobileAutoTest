package com.bmtc.svn.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;

import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.utils.DigestUtil;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.common.utils.ReadFileToString;
import com.bmtc.svn.dao.SvnConfDiffDao;
import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.domain.SvnConfDiff;
import com.bmtc.svn.domain.SvnConfDiffInfo;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.service.SvnConfDiffService;
import com.bmtc.svn.service.SvnService;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * svn配置文件差异service实现类
 * @author lpf7161
 */
@Transactional
@Service
@Component
public class SvnConfDiffServiceImpl implements SvnConfDiffService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SvnConfDiffServiceImpl.class);
	
	@Autowired
	SvnConfDiffDao svnConfDiffDao;
	
	@Autowired
	private SvnRepoDao svnRepoDao;
	
	/**
	 * SVN服务层
	 */
	@Resource(name = "SvnService")
	private SvnService svnService;
	
	@Autowired
	BMTCConfig bmtcConfig;
	
	/**
	 * 系统配置信息服务层
	 */
	@Autowired
	private ConfigService configService;
	
	/**
	 * 保存svn配置文件差异信息
	 * @return count
	 * @throws Exception 
	 */
	@Override
	@Scheduled(cron="0 0 2,13 * * ?") // 每天凌晨2点和中午13点定时更新
	// @Scheduled(cron="0 0/3 * * * ?") // 每天隔3分钟定时更新
	public int save() throws Exception {
		logger.info("SvnConfDiffServiceImpl.save() start");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		SvnConfDiff svnConfDiff = new SvnConfDiff();
		
		// 从数据库查询所有SVN仓库信息
		List<SvnRepo> list = svnRepoDao.querySvnRepo(params);
		
		// 用于存储更新或者插入数据库数据的返回值
		Integer count[] = new Integer[list.size()];
		int i = 0;
		
		// 遍历所有的SVN仓库
		for (SvnRepo svnRepo : list) {
			
			// 设置svn仓库名称
			svnConfDiff.setSvnRepoName(svnRepo.getSvnRepoName());
			
			// 设置仓库id
			svnConfDiff.setSvnRepoId(svnRepo.getId());
			
			// 根据svn数据库，生成配置文件passwd、authz和svnserver.conf
			String svnDbConfFilesLocation = getDbConfFiles(bmtcConfig.getSvnConfFilesLocation(), svnConfDiff.getSvnRepoName());
			
			// 从svn服务器svnConfDiff.getSvnRepoName()仓库目录下获取配置文件passwd、authz和svnserver.conf
			String svnServerConfFilesLocation = getSvnServerConfFiles(bmtcConfig.getSvnConfFilesLocation(), svnConfDiff.getSvnRepoName());
			
			// 判断存储（passwd、authz和svnserver.conf）配置文件的本地文件夹是否存在
			File svnDbConfFilesLocationFile = new File(svnDbConfFilesLocation);
			File svnServerConfFilesLocationFile = new File(svnServerConfFilesLocation);
			if(!svnDbConfFilesLocationFile.exists() || !svnDbConfFilesLocationFile.isDirectory() 
					|| !svnServerConfFilesLocationFile.exists() || !svnServerConfFilesLocationFile.isDirectory()) {
				logger.error("[" + svnConfDiff.getSvnRepoName() + "]" + "配置文件（passwd、authz和svnserver.conf）生成或下载失败");
				//return -1;
			}
			
			// 将从svn服务器上获取到的配置文件（passwd、authz和svnserver.conf）中的回车换行符号（"\n"、"\r\n"、"\r"）改为与System.getProperty("line.separator")一致
			// passwd文件
			String svnServerPasswdPath = svnServerConfFilesLocation + "passwd";
			// 读取passwd文件的内容到passwdContents
			StringBuffer passwdContents = ReadFileToString.getStrBuf(svnServerPasswdPath);
			File passwdFile = new File(svnServerPasswdPath);
			// 将passwd文件内容写入passwdFile中
			svnService.write(passwdFile, passwdContents.toString());
			
			// authz文件
			String svnServerAuthzPath = svnServerConfFilesLocation + "authz";
			// 读取authz文件的内容到authzContents
			StringBuffer authzContents = ReadFileToString.getStrBuf(svnServerAuthzPath);
			File authzFile = new File(svnServerAuthzPath);
			// 将authz文件内容写入authzFile中
			svnService.write(authzFile, authzContents.toString());
			
			// svnserver.conf文件
			String svnServerSvnservePath = svnServerConfFilesLocation + "svnserve.conf";
			// 读取svnserver.conf文件的内容到svnserverContents
			StringBuffer svnserverContents = ReadFileToString.getStrBuf(svnServerSvnservePath);
			File svnserverFile = new File(svnServerSvnservePath);
			// 将svnserver.conf文件内容写入svnserverFile中
			svnService.write(svnserverFile, svnserverContents.toString());
			
			// 分别对比三对配置文件（passwd、authz和svnserver.conf）内容是否相同（通过分别计算三对文件的SHA1校验值实现），将结果写入svnConfDiff对象中
			// 对比passwd文件
			File svnDbPasswdFile = new File(svnDbConfFilesLocation + "conf/passwd");
			String svnDbPasswdDgst = null;
			svnDbPasswdDgst = DigestUtil.getFileDigest(svnDbPasswdFile, "SHA1");
			
			File svnServerPasswdFile = new File(svnServerConfFilesLocation + "passwd");
			String svnServerPasswdDgst = null;
			svnServerPasswdDgst = DigestUtil.getFileDigest(svnServerPasswdFile, "SHA1");
			
			if(svnDbPasswdDgst.equals(svnServerPasswdDgst)) {
				svnConfDiff.setPasswdStatus("相同");
			} else {
				svnConfDiff.setPasswdStatus("不相同");
			}
			
			// 对比authz文件
			File svnDbAuthzFile = new File(svnDbConfFilesLocation + "conf/authz");
			String svnDbAuthzDgst = null;
			svnDbAuthzDgst = DigestUtil.getFileDigest(svnDbAuthzFile, "SHA1");
			
			File svnServerAuthzFile = new File(svnServerConfFilesLocation + "authz");
			String svnServerAuthzDgst = null;
			svnServerAuthzDgst = DigestUtil.getFileDigest(svnServerAuthzFile, "SHA1");
			
			if(svnDbAuthzDgst.equals(svnServerAuthzDgst)) {
				svnConfDiff.setAuthzStatus("相同");
			} else {
				svnConfDiff.setAuthzStatus("不相同");
			}
			
			// 对比svnserver.conf文件
			File svnDbSvnserveFile = new File(svnDbConfFilesLocation + "conf/svnserve.conf");
			String svnDbSvnserveDgst = null;
			svnDbSvnserveDgst = DigestUtil.getFileDigest(svnDbSvnserveFile, "SHA1");
			
			File svnServerSvnserveFile = new File(svnServerConfFilesLocation + "svnserve.conf");
			String svnServerSvnserveDgst = null;
			svnServerSvnserveDgst = DigestUtil.getFileDigest(svnServerSvnserveFile, "SHA1");
			
			if(svnDbSvnserveDgst.equals(svnServerSvnserveDgst)) {
				svnConfDiff.setSvnserverStatus("相同");
			} else {
				svnConfDiff.setSvnserverStatus("不相同");
			}
	
			// 通过svn仓库名查询svn配置文件差异信息，非空时修改svn配置文件差异信息
			if(svnConfDiffDao.getSvnConfDiff(svnConfDiff.getSvnRepoName()) != null) {
				count[i++] = svnConfDiffDao.update(svnConfDiff);
			} else { // 为null时，插入svn配置文件差异信息
				count[i++] = svnConfDiffDao.save(svnConfDiff);
			}
		}
		
		for (i = 0; i < list.size(); i++) {
			logger.info("ConfigServiceImpl.save() end");
			if (count[i] == 0) {
				return 0;
			}
		}
		logger.info("ConfigServiceImpl.save() end");
		return 1;
	}

	/**
	 * 根据svn数据库，生成配置文件passwd、authz和svnserver.conf，存放到本地路径svnDbConfFilesLocation
	 * @param svnDbConfFilesLocation, svnRepoName
	 * @return svnDbConfFilesLocation
	 * @throws Exception 
	 */
	public String getDbConfFiles(String svnDbConfFilesLocation, String svnRepoName) throws Exception {
		
		logger.info("ConfigServiceImpl.getDbConfFiles() start");
		
		// svnDbConfFilesLocation路径后面添加"/"
		if(!svnDbConfFilesLocation.endsWith("/")) {
			svnDbConfFilesLocation = svnDbConfFilesLocation + "/";
		}
		
		// svnDbConfFilesLocation路径后面增加svn仓库目录，db表示配置文件来自数据库中
		svnDbConfFilesLocation = svnDbConfFilesLocation + svnRepoName + "-db";
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnDbConfFilesLocation)) {
			svnDbConfFilesLocation = StringUtils.replace(svnDbConfFilesLocation, "\\", "/");
		}
		
		// svnDbConfFilesLocation路径后面添加"/"
		if(!svnDbConfFilesLocation.endsWith("/")) {
			svnDbConfFilesLocation = svnDbConfFilesLocation + "/";
		}
		
		// 不存在svnDbConfFilesLocation，则创建
		File svnDbConfFilesLocationFile = new File(svnDbConfFilesLocation);
		if (!svnDbConfFilesLocationFile.exists() || !svnDbConfFilesLocationFile.isDirectory()) {
			svnDbConfFilesLocationFile.mkdir();
		}
		
		// 根据svn数据库，生成配置文件passwd、authz和svnserver.conf，存放到本地路径svnDbConfFilesLocation
		svnService.exportConfig(svnRepoName, svnDbConfFilesLocation);
		
		logger.info("ConfigServiceImpl.getDbConfFiles() end");
		
		return svnDbConfFilesLocation;
	}
	
	/**
	 * 从远程svn服务器获取对应仓库中的passwd、authz和svnserver.conf，存放到本地路径svnServerConfFilesLocation
	 * @param svnServerConfFilesLocation, svnRepoName
	 * @return svnServerConfFilesLocation
	 * @throws Exception 
	 */
	public String getSvnServerConfFiles(String svnServerConfFilesLocation, String svnRepoName) throws Exception {
		
		logger.info("ConfigServiceImpl.getSvnServerConfFiles() start");
		
		// svnServerConfFilesLocation路径后面添加"/"
		if(!svnServerConfFilesLocation.endsWith("/")) {
			svnServerConfFilesLocation = svnServerConfFilesLocation + "/";
		}
		
		// svnServerConfFilesLocation路径后面增加svn仓库目录，db表示配置文件来自数据库中
		svnServerConfFilesLocation = svnServerConfFilesLocation + svnRepoName + "-svnServer";
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnServerConfFilesLocation)) {
			svnServerConfFilesLocation = StringUtils.replace(svnServerConfFilesLocation, "\\", "/");
		}
		
		// svnServerConfFilesLocation路径后面添加"/"
		if(!svnServerConfFilesLocation.endsWith("/")) {
			svnServerConfFilesLocation = svnServerConfFilesLocation + "/";
		}
		
		// 不存在svnServerConfFilesLocation，则创建
		File svnServerConfFilesLocationFile = new File(svnServerConfFilesLocation);
		if (!svnServerConfFilesLocationFile.exists() || !svnServerConfFilesLocationFile.isDirectory()) {
			svnServerConfFilesLocationFile.mkdir();
		}
		
		// 根据svn数据库，生成配置文件passwd、authz和svnserver.conf，存放到本地路径svnServerConfFilesLocation
		
		// 根据仓库名获取仓库信息
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoName(svnRepoName);
		
		// 根据svn url截取svn服务器的IP地址
		String svnServerIp = svnRepo.getSvnRepoUrl().split("//")[1].split("/")[0];

		// 根据svn的IP地址查询svn服务器相关信息
		ConfigInfoDO configInfoDO = configService.getConfigInfo(svnServerIp);

		int port = Integer.parseInt(bmtcConfig.getPort());
		// 建立连接—远程服务器IP和端口号
		Connection con = new Connection(svnServerIp, port); // 端口号为22
		logger.info("scp配置文件(svnserve.conf, authz, passwd)开始...");
		
		// 进行连接操作
		con.connect();
		
		// 配置文件中的bmtcConfig.getSvnServerUserName()
		String svnServerUserName = configInfoDO.getSvnServerUserName(); 
		
		// 配置文件中的bmtcConfig.getSvnServerPassword()
		String svnServerPassword = EncryptUtil.decrypt(configInfoDO.getSvnServerPassword()); 
		
		// 进行连接访问授权验证
		boolean isAuthed = con.authenticateWithPassword(svnServerUserName, 
				svnServerPassword);
		
		if(isAuthed == false) {
			logger.error("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
			throw new RuntimeException("scp认证失败，请检查svn服务器的用户名和登录口令是否正确"); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		} else {
			logger.info("svn服务器的用户名和登录口令认证成功");
		}
		
		// 建立SCP客户端对象
		SCPClient scpClient = new SCPClient(con);
		
		// 建立SFTPv3客户端对象
		SFTPv3Client sFTPv3Client = new SFTPv3Client(con);
			
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnRepo.getSvnRepoPath())) {
			svnRepo.setSvnRepoPath(StringUtils.replace(svnRepo.getSvnRepoPath(), "\\", "/"));
		}
		
		// SvnRepoPath路径后面添加"/"
		if(!svnRepo.getSvnRepoPath().endsWith("/")) {
			svnRepo.setSvnRepoPath(svnRepo.getSvnRepoPath() + "/");
		}
		
		//判断远程仓库是否存在
		try {
			sFTPv3Client.stat(svnRepo.getSvnRepoPath());		
		} catch (IOException ioErr) {
			ioErr.printStackTrace();
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}

		Vector<?> contents = null;
		
		//判断远程仓库是否存在
		try {
			contents = (Vector<?>) sFTPv3Client.ls(svnRepo.getSvnRepoPath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}
		
		//判断远程仓库是否存在
		if(contents.size() == 2) {
			logger.error("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}
		
		try {
			
			// 将远程服务器passwd文件复制到本地
			scpClient.get(svnRepo.getSvnRepoPath() + "conf/passwd", svnServerConfFilesLocation);
			
			// 将远程服务器authz文件复制到本地
			scpClient.get(svnRepo.getSvnRepoPath() + "conf/authz", svnServerConfFilesLocation);

			// 将远程服务器svnserve.conf文件复制到本地
			scpClient.get(svnRepo.getSvnRepoPath() + "conf/svnserve.conf", svnServerConfFilesLocation);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("从svn服务器复制配置文件(svnserve.conf, authz, passwd)到本地时发生异常：" + e.getMessage());
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		} finally {
			if(sFTPv3Client != null) {
				sFTPv3Client.close();
			}
			// 关闭连接
			con.close();
		}
		
		logger.info("scp配置文件(svnserve.conf, authz, passwd)结束");
		logger.info("ConfigServiceImpl.getSvnServerConfFiles() end");
		return svnServerConfFilesLocation;
	}
	
	/**
	 * 更新svn配置文件差异信息
	 * @param svnConfDiff
	 * @return count
	 */
	@Override
	public int update(SvnConfDiff svnConfDiff) {
		logger.info("SvnConfDiffServiceImpl.update() start");
		int count = svnConfDiffDao.update(svnConfDiff);
		logger.info("SvnConfDiffServiceImpl.update() end");
		return count;
	}

	/**
	 * 通过svn仓库名查询svn配置文件差异信息
	 * @param svnRepoName
	 * @return svnConfDiffInfo
	 */
	@Override
	public SvnConfDiffInfo getSvnConfDiff(String svnRepoName) {		
		logger.info("SvnConfDiffServiceImpl.getSvnConfDiff() start");
		SvnConfDiffInfo svnConfDiffInfo = svnConfDiffDao.getSvnConfDiff(svnRepoName);
		logger.info("SvnConfDiffServiceImpl.getSvnConfDiff() end");
		return svnConfDiffInfo;
	}

	/**
	 * 通过id批量删除svn配置文件差异信息
	 * @param ids
	 * @return count
	 */
	@Override
	public int batchRemove(Long[] ids) {
		logger.info("SvnConfDiffServiceImpl.batchremove() start");
		int count = svnConfDiffDao.batchRemove(ids);
		logger.info("SvnConfDiffServiceImpl.batchremove() end");
		return count;
	}

	/**
	 * 通过id获取svn配置文件差异对象
	 * @param id
	 * @return svnConfDiffInfo
	 */
	@Override
	public SvnConfDiffInfo get(Long id) {
		logger.info("SvnConfDiffServiceImpl.get() start");
		SvnConfDiffInfo svnConfDiffInfo = svnConfDiffDao.get(id);
		logger.info("SvnConfDiffServiceImpl.get() end");
		return svnConfDiffInfo;
	}

	/**
	 * 查询svn配置文件差异数据
	 * @param map
	 * @return List<SvnConfDiffInfo>
	 */
	@Override
	public List<SvnConfDiffInfo> list(Map<String, Object> map) {
		logger.info("SvnConfDiffServiceImpl.list() start");
		logger.info("SvnConfDiffServiceImpl.list() end");
		return svnConfDiffDao.list(map);
	}

	/**
	 * 统计svn配置文件差异总数
	 * @param map
	 * @return count
	 */
	@Override
	public int count(Map<String, Object> map) {
		logger.info("SvnConfDiffServiceImpl.count() start");
		logger.info("SvnConfDiffServiceImpl.count() end");
		return svnConfDiffDao.count(map);
	}

	/**
	 * 删除svn配置文件差异信息
	 * @param id
	 * @return count
	 */
	@Override
	public int remove(Long id) {
		logger.info("SvnConfDiffServiceImpl.remove() start");
		logger.info("SvnConfDiffServiceImpl.remove() end");
		return svnConfDiffDao.remove(id);
	}

}
