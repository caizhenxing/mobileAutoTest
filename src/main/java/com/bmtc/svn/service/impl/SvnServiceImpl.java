package com.bmtc.svn.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmtc.common.config.BMTCConfig;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.common.utils.TimeUtils;
import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.dao.SvnUserDao;
import com.bmtc.svn.dao.SvnUserRightDao;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnUser;
import com.bmtc.svn.domain.SvnUserAuthzInfo;
import com.bmtc.svn.service.SvnService;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;


/**
 * SVN用户信息管理业务逻辑实现
 * 导出svn配置信息服务层
 * @author lpf7161
 *
 */
@Service("SvnService")
public class SvnServiceImpl implements SvnService {
	private static Logger logger = Logger.getLogger(SvnServiceImpl.class);
	
	@Autowired
	private SvnUserDao svnUserDao;
	
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
	 * 配置文件信息
	 */
	@Autowired
	BMTCConfig bmtcConfig;
	
	/**
	 * 分隔符
	 */
	private static final String SEP = System.getProperty("line.separator");

	/**
	 * 导出到配置文件
	 * 
	 * @param svnRepoName
	 *            项目名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 * @throws Exception 
	 */
	@Override
	public void exportConfig(String svnRepoName, String svnConfFilesLocation) throws Exception {
		logger.info("SvnServiceImpl.exportConfig() start");
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoName(svnRepoName);
		try {
			this.exportConfig(svnRepo, svnConfFilesLocation);
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
			//throw new Exception(e.getMessage());
		}

		logger.info("SvnServiceImpl.exportConfig() end");
	}

	/**
	 * 导出到配置文件
	 * 
	 * @param svnRepo
	 *            项目
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置     
	 * @throws Exception 
	 */
	@Override
	public void exportConfig(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception {
		logger.info("SvnServiceImpl.exportConfig() start");
		if (svnRepo == null) {
			return;
		}
/*		
		File parent = new File(svnRepo.getSvnRepoPath());
		if (!parent.exists() || !parent.isDirectory()) {
			logger.error("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			throw new Exception("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());		
		}*/
		
		// SVN
		try {
			this.exportSVN(svnRepo, svnConfFilesLocation);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
			//throw new Exception(e.getMessage());
		}

		logger.info("SvnServiceImpl.exportConfig() end");
	}

	/**
	 * 导出svn协议的配置信息
	 * 
	 * @param svnRepo
	 *            项目
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置     
	 * @throws Exception 
	 */
	@Override
	public void exportSVN(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception {
		logger.info("SvnServiceImpl.exportSVN() start");
		
		//根据svn仓库名查询svn仓库id
		String svnRepoIdStr = svnRepoDao.querySvnRepoIdBySvnRepoName(svnRepo.getSvnRepoName());
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			throw new RuntimeException("svn_repo数据表中不存在svn仓库名为'" + svnRepo.getSvnRepoName() + "'的记录");
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);
		}

		//根据SVN库id查询SVN用户信息
		
		//项目的用户
		List<SvnUser> usrList = svnUserDao.getListBySvnRepoId(svnRepoId);
		
		// 项目的权限
		Map<String, List<SvnUserAuthzInfo>> svnAuthMap = this.getSvnUserAuthzInfos(svnRepo.getSvnRepoName());

		this.exportSvnConf(svnRepo, svnConfFilesLocation);
		this.exportPasswdSVN(svnRepo, usrList, svnConfFilesLocation);
		try {
			this.exportAuthz(svnRepo, svnAuthMap, svnConfFilesLocation);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
			//throw new Exception(e.getMessage());
		}

		logger.info("SvnServiceImpl.exportSVN() end");
	}

	/**
	 * 获取有相同svn root的项目的权限列表
	 * 
	 * @param rootPath
	 *            svn root
	 * @return 有相同svn root的项目的权限列表
	 */
	@Override
	public Map<String, List<SvnUserAuthzInfo>> getSvnAuthsByRootPath(String rootPath) {
		logger.info("SvnServiceImpl.getSvnAuthsByRootPath() start");
		
		// <svn_path,List<SvnUserAuthzInfo>>
		Map<String, List<SvnUserAuthzInfo>> results = new LinkedHashMap<String, List<SvnUserAuthzInfo>>();
		
		List<SvnUserAuthzInfo> svnUserAuthzInfoList = this.svnUserRightDao.getListByRootPath(rootPath);
		
		// 格式化返回数据
		for (SvnUserAuthzInfo svnUserAuthzInfo : svnUserAuthzInfoList) {
			List<SvnUserAuthzInfo> authList = results.get(svnUserAuthzInfo.getSvnPath());
			if (authList == null) {
				authList = new ArrayList<SvnUserAuthzInfo>();
				results.put(svnUserAuthzInfo.getSvnPath(), authList);
			}
			authList.add(svnUserAuthzInfo);

		}
		logger.info("SvnServiceImpl.getSvnAuthsByRootPath() end");
		return results;
	}

	/**
	 * 获取项目的权限列表
	 * 
	 * @param svnRepoName
	 *            项目
	 * @return 项目的权限列表
	 * @throws Exception 
	 */
	@Override
	public Map<String, List<SvnUserAuthzInfo>> getSvnUserAuthzInfos(String svnRepoName) throws Exception {
		logger.info("SvnServiceImpl.getSvnUserAuthzInfos() start");
		
		// <svn_path,List<SvnUserAuthzInfo>>
		Map<String, List<SvnUserAuthzInfo>> results = new LinkedHashMap<String, List<SvnUserAuthzInfo>>();
		
		//根据svn仓库名查询svn仓库id
		String svnRepoIdStr = svnRepoDao.querySvnRepoIdBySvnRepoName(svnRepoName);
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnRepoName + "'的记录");
			//throw new Exception("svn_repo数据表中不存在svn仓库名为'" + svnRepoName + "'的记录");
			throw new RuntimeException("svn_repo数据表中不存在svn仓库名为'" + svnRepoName + "'的记录"); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		List<SvnUserAuthzInfo> svnUserAuthzInfoList = svnUserRightDao.getSvnUserRightList(svnRepoId);
		
		// 格式化返回数据
		for (SvnUserAuthzInfo svnUserAuthzInfo : svnUserAuthzInfoList) {
			List<SvnUserAuthzInfo> authList = results.get(svnUserAuthzInfo.getSvnPath());
			if (authList == null) {
				authList = new ArrayList<SvnUserAuthzInfo>();
				results.put(svnUserAuthzInfo.getSvnPath(), authList);
			}
			authList.add(svnUserAuthzInfo);

		}
		
		logger.info("SvnServiceImpl.getSvnUserAuthzInfos() end");
		return results;
	}

	/**
	 * 输出svn方式的密码文件
	 * 
	 * @param svnRepo
	 *            项目
	 * @param usrList
	 *            项目用户列表
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	@Override
	public void exportPasswdSVN(SvnRepo svnRepo, List<SvnUser> usrList, String svnConfFilesLocation) {
		
		logger.info("SvnServiceImpl.exportPasswdSVN() start");
		
/*		//备份passwd文件
		//passwd文件路径
		String prePasswdPath = svnRepo.getSvnRepoPath() + "/conf/passwd";
		//读取passwd文件的内容到prePasswdcontents
		StringBuffer prePasswdcontents = ReadFileToString.getStrBuf(prePasswdPath);
		
		//备份passwd文件到svnRepo.getSvnRepoPath()/confBackup/目录下。
		String passwdPath = svnRepo.getSvnRepoPath() + "/confBackup/" + svnRepo.getSvnRepoName() 
				+ "-passwd-" + TimeUtils.getNowTime();
		//需要备份passwd到文件
		File prePasswd = new File(passwdPath);
		//将passwd文件内容写入prePasswd中
		this.write(prePasswd, prePasswdcontents.toString());*/
		
		
		//刷新passwd的内容
		//File outFile = new File(svnRepo.getSvnRepoPath(), "/conf/passwd");
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnConfFilesLocation)) {
			svnConfFilesLocation = StringUtils.replace(svnConfFilesLocation, "\\", "/");
		}
		
		// svnConfFilesLocation路径后面添加"/"
		if(!svnConfFilesLocation.endsWith("/")) {
			svnConfFilesLocation = svnConfFilesLocation + "/";
		}
		
		//svn配置文件(passwd)在本地的存放位置
		File outFile = new File(svnConfFilesLocation, "conf/passwd");
		StringBuffer contents = new StringBuffer();
		contents.append("[users]").append(SEP);

		for (SvnUser usr : usrList) {
			contents.append(usr.getSvnUserName()).append(" = ")
					.append(EncryptUtil.decrypt(usr.getSvnPassword())).append(SEP);// 解密
		}
		
/*		for (SvnUser usr : usrList) {
			contents.append(usr.getSvnUserName()).append(" = ")
					.append(usr.getSvnPassword()).append(SEP);
		}*/
		
		this.write(outFile, contents.toString());
		logger.info("SvnServiceImpl.exportPasswdSVN() end");
	}

	/**
	 * 输出权限配置文件
	 * 
	 * @param svnRepo
	 *            项目
	 * @param resMap
	 *            项目的权限列表
	 * @throws Exception 
	 */
	@Override
	public void exportAuthz(SvnRepo svnRepo, Map<String, List<SvnUserAuthzInfo>> resMap, 
			String svnConfFilesLocation) throws Exception {
		
		logger.info("SvnServiceImpl.exportAuthz() start");
		
		if (svnRepo == null || StringUtils.isBlank(svnRepo.getSvnRepoName())) {
			logger.error("svnRepo为空或者不存在svn仓库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			//throw new Exception("svnRepo为空或者不存在svn仓库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			throw new RuntimeException("svnRepo为空或者不存在svn仓库名为'" + svnRepo.getSvnRepoName() + "'的记录"); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}
		
/*		//备份authz文件
		//authz文件路径
		String preAuthzPath = svnRepo.getSvnRepoPath() + "/conf/authz";
		//读取authz文件的内容到prePasswdcontents
		StringBuffer preAuthzContents = ReadFileToString.getStrBuf(preAuthzPath);
		
		//备份authz文件到svnRepo.getSvnRepoPath()/confBackup/目录下。
		String authzPath = svnRepo.getSvnRepoPath() + "/confBackup/" + svnRepo.getSvnRepoName() 
				+ "-authz-" + TimeUtils.getNowTime();
		//需要备份authz文件 
		File preAuthz = new File(authzPath);
		//将authz文件内容写入preAuthz中
		this.write(preAuthz, preAuthzContents.toString());*/
		
		
		// 刷新authz内容
		// File outFile = new File(svnRepo.getSvnRepoPath(), "/conf/authz");
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnConfFilesLocation)) {
			svnConfFilesLocation = StringUtils.replace(svnConfFilesLocation, "\\", "/");
		}
		
		// svnConfFilesLocation路径后面添加"/"
		if(!svnConfFilesLocation.endsWith("/")) {
			svnConfFilesLocation = svnConfFilesLocation + "/";
		}
		
		// svn配置文件(authz)在本地的存放位置
		File outFile = new File(svnConfFilesLocation, "conf/authz");
		StringBuffer contents = new StringBuffer();
		contents.append("[aliases]").append(SEP);
		contents.append("[groups]").append(SEP);

		contents.append(SEP);

		for (Iterator<String> resIterator = resMap.keySet().iterator(); resIterator
				.hasNext();) {
			String res = resIterator.next();
			contents.append(res).append(SEP);
			for (SvnUserAuthzInfo svnUserAuthzInfo : resMap.get(res)) {
				if (StringUtils.isNotBlank(svnUserAuthzInfo.getSvnUserName())) {
					contents.append(svnUserAuthzInfo.getSvnUserName()).append(" = ")
							.append(svnUserAuthzInfo.getSvnUserAuthz()).append(SEP);
				}
			}
			contents.append(SEP);
		}

		this.write(outFile, contents.toString());
		logger.info("SvnServiceImpl.exportAuthz() end");
	}

	
	@Override
	public void exportSvnConf(SvnRepo svnRepo, String svnConfFilesLocation) {
		if (svnRepo == null || StringUtils.isBlank(svnRepo.getSvnRepoName())) {
			return;
		}	
		
		// File outFile = new File(svnRepo.getSvnRepoPath(), "/conf/svnserve.conf");
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnConfFilesLocation)) {
			svnConfFilesLocation = StringUtils.replace(svnConfFilesLocation, "\\", "/");
		}
		
		// svnConfFilesLocation路径后面添加"/"
		if(!svnConfFilesLocation.endsWith("/")) {
			svnConfFilesLocation = svnConfFilesLocation + "/";
		}
		
		// svn配置文件(svnserve.conf)在本地的存放位置
		File outFile = new File(svnConfFilesLocation, "conf/svnserve.conf");
		
		StringBuffer contents = new StringBuffer();
		contents.append("[general]").append(SEP);
		contents.append("anon-access = none").append(SEP);
		contents.append("auth-access = write").append(SEP);
		contents.append("password-db = passwd").append(SEP);
		contents.append("authz-db = authz").append(SEP);
		contents.append("[sasl]").append(SEP);
		this.write(outFile, contents.toString());	
	}

	/**
	 * 写文件流
	 * 
	 * @param outFile
	 *            输出文件
	 * @param contents
	 *            内容
	 */
	@Override
	public void write(File outFile, String contents) {
		
		BufferedWriter writer = null;
		try {
			if (contents == null) {
				contents = "";
			}
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "UTF-8"));// UTF-8 without
																// BOM
			writer.write(contents);
			logger.debug(outFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		} finally {
			if (writer != null) {
				try {
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将svn配置文件(authz, passwd, svnserve.conf)存放到SVN服务器相应位置
	 * 
	 * @param svnRepoName
	 *            仓库名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置          
	 */
	@Override
	public void writeSvnConfFilesToSvnServer(String svnRepoName, String svnConfFilesLocation) 
			throws IOException {

		logger.info("SvnServiceImpl.writeSvnConfFilesToSvnServer() start");
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
			//throw new IOException("scp认证失败，请检查svn服务器的用户名和登录口令是否正确");
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
		
		// 路径 把\替换为/
		if (StringUtils.isNotBlank(svnConfFilesLocation)) {
			svnConfFilesLocation = StringUtils.replace(svnConfFilesLocation, "\\", "/");
		}
		
		// svnConfFilesLocation路径后面添加"/"
		if(!svnConfFilesLocation.endsWith("/")) {
			svnConfFilesLocation = svnConfFilesLocation + "/";
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
			//throw new IOException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}

		Vector<?> contents = null;
		
		//判断远程仓库是否存在
		try {
			contents = (Vector<?>) sFTPv3Client.ls(svnRepo.getSvnRepoPath());
		} catch (IOException e) {
			e.printStackTrace();
			//throw new IOException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}
		
		//判断远程仓库是否存在
		if(contents.size() == 2) {
			logger.error("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			//throw new IOException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath());
			throw new RuntimeException("svn.notFoundResp, 找不到仓库路径:" + svnRepo.getSvnRepoPath()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		}
		
		//当前目录不存在confBackup文件夹，则创建confBackup文件夹，用于备份passwd和authz文件
		int cnt = 0;
		for(Iterator<?> elements = contents.iterator(); elements.hasNext();) {
			SFTPv3DirectoryEntry sFTPv3DirectoryEntry = (SFTPv3DirectoryEntry) elements.next();
			if(!sFTPv3DirectoryEntry.filename.trim().equals("confBackup")) {
				cnt++;
			}
		}

		if(cnt == contents.size()) {
			sFTPv3Client.mkdir(svnRepo.getSvnRepoPath() + "confBackup", 0755);
		}
			
		try {
			// 创建本地confBackup文件夹
			File confBackupFile = new File(svnConfFilesLocation + "confBackup/");
			if (!confBackupFile.exists() || !confBackupFile.isDirectory()) {// 不存在confBackup，则创建
				confBackupFile.mkdir();
			}
			
			// 将远程服务器passwd文件复制到本地，用于备份远程服务器passwd文件
			scpClient.get(svnRepo.getSvnRepoPath() + "conf/passwd", svnConfFilesLocation + "confBackup/");
			
			// 将远程服务器authz文件复制到本地，用于备份远程服务器authz文件
			scpClient.get(svnRepo.getSvnRepoPath() + "conf/authz", svnConfFilesLocation + "confBackup/");
			
			// 备份远程服务器passwd文件
			scpClient.put(svnConfFilesLocation + "confBackup/passwd", 
					svnRepo.getSvnRepoPath() + "confBackup/");
			
			// 备份远程服务器authz文件
			scpClient.put(svnConfFilesLocation + "confBackup/authz", 
					svnRepo.getSvnRepoPath() + "confBackup/");
			
			// 修改远程服务器备份authz文件的名称
			sFTPv3Client.mv(svnRepo.getSvnRepoPath() + "confBackup/authz", svnRepo.getSvnRepoPath() + "confBackup/"
					+ svnRepo.getSvnRepoName() + "-authz-" + TimeUtils.getNowTime());
			
			// 修改远程服务器备份passwd文件的名称
			sFTPv3Client.mv(svnRepo.getSvnRepoPath() + "confBackup/passwd", svnRepo.getSvnRepoPath() + "confBackup/"
					+ svnRepo.getSvnRepoName() + "-passwd-" + TimeUtils.getNowTime());
			
			//移动文件
		/*	sFTPv3Client.mv(svnRepo.getSvnRepoPath() + "conf/authz", svnRepo.getSvnRepoPath() + "confBackup/"
					+ svnRepo.getSvnRepoName() + "-authz-" + TimeUtils.getNowTime());
					
			sFTPv3Client.mv(svnRepo.getSvnRepoPath() + "conf/passwd", svnRepo.getSvnRepoPath() + "confBackup/"
					+ svnRepo.getSvnRepoName() + "-passwd-" + TimeUtils.getNowTime());*/
			
			// 将本地svnserve.conf文件上传到服务器
			scpClient.put(svnConfFilesLocation + "conf/svnserve.conf", 
					svnRepo.getSvnRepoPath() + "conf/");
			
			// 将本地authz文件上传到服务器
			scpClient.put(svnConfFilesLocation + "conf/authz", 
					svnRepo.getSvnRepoPath() + "conf/");
			
			// 将本地passwd文件上传到服务器
			scpClient.put(svnConfFilesLocation + "conf/passwd", 
					svnRepo.getSvnRepoPath() + "conf/");
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("配置文件(svnserve.conf, authz, passwd)scp到svn服务器时发生异常：" + e.getMessage());
			//throw new IOException(e.getMessage());
			throw new RuntimeException(e.getMessage()); //catch中添加throw new RuntimeException()，目的是让aop捕获异常去做事务回滚
		} finally {
			if(sFTPv3Client != null) {
				sFTPv3Client.close();
			}
			// 关闭连接
			con.close();
		}
		
		logger.info("scp配置文件(svnserve.conf, authz, passwd)结束");
		logger.info("SvnServiceImpl.writeSvnConfFilesToSvnServer() end");
	}

	
	/**
	 * 导出配置文件到远程svn服务器
	 * 
	 * @param svnRepoName
	 *            仓库名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	@Override
	public void exportConfigToSvnServer(String svnRepoName,
			String svnConfFilesLocation) throws Exception {
		
		// 不存在svnConfFilesLocation，则创建
		File svnConfFilesLocationFile = new File(svnConfFilesLocation);
		if (!svnConfFilesLocationFile.exists() || !svnConfFilesLocationFile.isDirectory()) {
			svnConfFilesLocationFile.mkdir();
		}
		
		// 操作passwd和authz文件，修改svn用户和权限配置
		this.exportConfig(svnRepoName, svnConfFilesLocation);
		
		// 将svn配置文件(authz, passwd, svnserve.conf)写入远程svn服务器
		this.writeSvnConfFilesToSvnServer(svnRepoName, svnConfFilesLocation);
	}
}
