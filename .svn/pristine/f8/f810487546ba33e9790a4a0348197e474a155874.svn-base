package com.bmtc.svn.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnUser;
import com.bmtc.svn.domain.SvnUserAuthzInfo;



/**
 * SvnService业务逻辑接口
 * 导出svn配置信息服务层
 * @author lpf7161
 *
 */
public interface SvnService {

	/**
	 * 导出到配置文件
	 * 
	 * @param svnRepoName
	 *            仓库名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	void exportConfig(String svnRepoName, String svnConfFilesLocation) throws Exception;

	/**
	 * 导出到配置文件
	 * 
	 * @param svnRepo
	 *            项目
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置     
	 */
	void exportConfig(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception;

	/**
	 * 导出svn协议的配置信息
	 * 
	 * @param svnRepo
	 *            项目
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置       
	 */
	void exportSVN(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception;


	/**
	 * 获取有相同svn root的项目的权限列表
	 * 
	 * @param rootPath
	 *            svn root
	 * @return 有相同svn root的项目的权限列表
	 */
	Map<String, List<SvnUserAuthzInfo>> getSvnAuthsByRootPath(String rootPath);

	/**
	 * 获取项目的权限列表
	 * 
	 * @param svnRepoName
	 *            项目
	 * @return 项目的权限列表
	 */
	Map<String, List<SvnUserAuthzInfo>> getSvnUserAuthzInfos(String svnRepoName) throws Exception;

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
	void exportPasswdSVN(SvnRepo svnRepo, List<SvnUser> usrList, String svnConfFilesLocation);

	/**
	 * 输出权限配置文件
	 * 
	 * @param svnRepo
	 *            项目
	 * @param resMap
	 *            项目的权限列表
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	void exportAuthz(SvnRepo svnRepo, Map<String, List<SvnUserAuthzInfo>> resMap, String svnConfFilesLocation) throws Exception;


	/**
	 * 输出svn方式的svnserve.conf
	 * 
	 * @param svnRepo
	 *            项目
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	void exportSvnConf(SvnRepo svnRepo, String svnConfFilesLocation);

	/**
	 * 写文件流
	 * 
	 * @param outFile
	 *            输出文件
	 * @param contents
	 *            内容
	 */
	void write(File outFile, String contents);
	
	/**
	 * 将svn配置文件(authz, passwd, svnserve.conf)存放到SVN服务器相应位置
	 * 
	 * @param svnRepoName
	 *            仓库名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置          
	 */
	void writeSvnConfFilesToSvnServer(String svnRepoName, String svnConfFilesLocation) throws IOException;
	

	/**
	 * 导出配置文件到远程svn服务器
	 * 
	 * @param svnRepoName
	 *            仓库名
	 * @param svnConfFilesLocation
	 * 			  svn配置文件(authz, passwd, svnserve.conf)在本地的存放位置    
	 */
	void exportConfigToSvnServer(String svnRepoName, String svnConfFilesLocation) throws Exception;
}
