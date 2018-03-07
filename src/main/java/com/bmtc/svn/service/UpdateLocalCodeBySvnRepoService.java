package com.bmtc.svn.service;

import java.io.File;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 * updateLocalCodeBySvnRepoService业务逻辑接口
 * @author lpf7161
 *
 */
public interface UpdateLocalCodeBySvnRepoService {

	/**
	 * 从SVN仓库检出代码或者更新代码到本地
	 * @param url, userName, password, wcPath, depth
	 */
	long updateLocalCodeBySvnRepo(String url, String userName, String password, 
			File wcPath, SVNDepth depth) throws SVNException;
	
	
	/**
	 * 连接Svn服务器
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 * @throws SVNException
	 */
	SVNClientManager connectSvnRepos(String url, String userName,
			String password) throws SVNException;
	
	/**
	 * 更新代码到指定版本
	 * @param url
	 * @param userName
	 * @param password
	 * @param wcPath
	 * @param date
	 * @param depth
	 * @return
	 * @throws SVNException
	 */
/*	long updateByTime(String url, String userName, String password,
			File wcPath, Date date, SVNDepth depth) throws SVNException;*/
	
	/**
	 * 更新代码到指定版本
	 * @param clientManager
	 * @param wcPath
	 * @param updateToRevision
	 * @param depth
	 * @return
	 * @throws SVNException
	 */
	long update(SVNClientManager clientManager, File wcPath,
			SVNRevision updateToRevision, SVNDepth depth) throws SVNException;

	/**
	 * 根据版本号，路径，检出到指定本地位置
	 * @param clientManager
	 * @param url
	 * @param revision
	 * @param destPath
	 * @param depth
	 * @return
	 * @throws SVNException
	 */
	long checkout(SVNClientManager clientManager, SVNURL url,
			SVNRevision revision, File destPath, SVNDepth depth)
			throws SVNException;
	
	
}
