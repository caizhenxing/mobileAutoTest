/**
 * 
 */
package com.bmtc.svn.service;

import java.io.File;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.bmtc.svn.domain.SvnRepo;

/**
 * 仓库服务层
 * @author lpf7161
 * 
 */
public interface RepositoryService {
	
	/**
	 * 获取svn仓库
	 * @param svnRepoName 项目名称, svnUserName svn用户名, svnPassword svn密码
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	SVNRepository getRepository(String svnRepoName, String svnUserName, 
			String svnPassword) throws SVNException;
	
	/**
	 * 从项目的url中获取svn的url
	 * @param url 项目url
	 * @return svn url
	 */
	String parseURL(String url);
	
	/**
	 * 获取svn仓库
	 * @param svnRepo 项目, svnUserName svn用户名, svnPassword svn密码
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	SVNRepository getRepository(SvnRepo svnRepo, String svnUserName, 
			String svnPassword) throws SVNException;
		
	/**
	 * 返回项目仓库的根
	 * @param svnRepo 项目
	 * @param svnUserName SVN用户名
	 * @param svnPassword SVN用户密码
	 * @return 仓库根
	 */
	String getRepositoryRoot(SvnRepo svnRepo, String svnUserName, 
			String svnPassword);
	
	
	/**
	 * 获取项目指定路径的svn仓库文件系统
	 * @param svnRepoName 项目
	 * @param path 相对仓库根目录的路径
	 * @param svnUserName SVN用户名
	 * @param svnPassword SVN用户密码
	 * @return 目录或文件系统
	 */
	Collection<SVNDirEntry> getDir(String svnRepoName, String path, String svnUserName,
			String svnPassword, SVNRepository repository) throws SVNException;

	/**
     * Creates a local blank FSFS-type repository.
     * A call to this routine is equivalent to 
     * <code>createLocalRepository(path, null, enableRevisionProperties, force)</code>.
     * 
     * @param  respository                          a repository root location
     * @return                               a local URL (file:///) of a newly
     *                                       created repository
     */
	SVNURL createLocalRepository(File respository);

	//测试仓库联通性
	boolean testRepositoryConnected(SVNRepository repository, String svnUrl) throws SVNException;
	
	//获取svn对应路径的目录信息
	SVNDirEntry getTargetInfo(SVNRepository repository, String path, long revision) throws SVNException;
	
	String[] getCodePaths(SVNRepository repository, String url) throws SVNException;
	
	String getSvnRoot(SVNRepository repository) throws SVNException;
}
