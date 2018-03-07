package com.bmtc.svn.service.impl;

import java.io.File;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.bmtc.svn.common.utils.SVNUtil;
import com.bmtc.svn.service.UpdateLocalCodeBySvnRepoService;


/**
 * SVN用户信息管理业务逻辑实现
 * @author lpf7161
 *
 */
@Service("UpdateLocalCodeBySvnRepoService")
public class UpdateLocalCodeBySvnRepoServiceImpl implements UpdateLocalCodeBySvnRepoService {
	private static Logger logger = Logger.getLogger(UpdateLocalCodeBySvnRepoServiceImpl.class);

	/**
	 * 从SVN仓库检出代码或者更新代码到本地
	 * @Updates a working copy (brings changes from the repository into the working copy).
     * @param clientManager
     * @param wcPath working copy path
     * @param updateToRevision revision to update to
     * @param depth update的深度：目录、子目录、文件
     * @return 当前版本号
     * @throws SVNException
	 */
	@Override
	public long updateLocalCodeBySvnRepo(String url, String userName, String password, 
			File wcPath, SVNDepth depth) throws SVNException {
		
		logger.info("SvnRepoServiceImpl.updateLocalCodeBySvnRepo() start");
		
		SVNClientManager clientManager = null;
		
/*		try { 
			clientManager = this.connectSvnRepos(url, userName, password);
		} catch (SVNException e) {
			logger.error(e.getMessage());
		}*/
		
		try { 
			clientManager = SVNUtil.authSvn(url, userName, password);
		} catch (SVNException e) {
			logger.error(e.getMessage());
		}
		
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		
		updateClient.setIgnoreExternals(false);
		
		if (depth == null) {
			depth = SVNDepth.INFINITY;
		}
		try {
			//如果第一次就是检出
			if (!SVNWCUtil.isVersionedDirectory(wcPath)) {
				logger.info("SvnRepoServiceImpl.updateLocalCodeBySvnRepo() end");
				return this.checkout(clientManager, SVNURL.parseURIEncoded(url), SVNRevision.HEAD, wcPath, depth);
			}
			else {
				logger.info("SvnRepoServiceImpl.updateLocalCodeBySvnRepo() end");
				//从第二次开始就是更新
				return updateClient.doUpdate(wcPath, SVNRevision.HEAD, depth, false, false);
			}
		}
		catch (SVNException e) {
			logger.error("更新代码到指定版本:"+e.getMessage());
			throw new SVNException(e.getErrorMessage());
		}
	}
	
   /**
     * @category 更新代码到指定版本
     * @Updates a working copy (brings changes from the repository into the working copy).
     * @param clientManager
     * @param wcPath working copy path
     * @param updateToRevision revision to update to
     * @param depth update的深度：目录、子目录、文件
     * @return
     * @throws SVNException
     */
	/*@Override
	public long updateByTime(String url, String userName, String password, File wcPath, Date date, SVNDepth depth) throws SVNException {
    	SVNClientManager clientManager = this.connectSvnRepos(url, userName, password);
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		
		SVNRevision updateToRevision = SVNRevision.create(date);
		updateClient.setIgnoreExternals(false);
		//SVNWCUtil.isVersionedDirectory(dir)
		if (depth == null) {
			depth = SVNDepth.INFINITY;
		}
		try {
			//如果第一次就是检出
			if (!SVNWCUtil.isVersionedDirectory(wcPath)) {
				return this.checkout(clientManager, SVNURL.parseURIEncoded(url), SVNRevision.HEAD, wcPath, depth);
			}
			else {
				//从第二次开始就是更新
				return updateClient.doUpdate(wcPath, SVNRevision.HEAD, depth, false, false);
			}		
		}
		catch (SVNException e) {
			logger.error("更新代码到指定版本:"+e.getMessage());
			throw new SVNException(e.getErrorMessage());
		}
	}*/
    
    
	/**
	 * @category 用户认证
	 * @param userName
	 * @param password
	 * @throws SVNException 
	 */
	private ISVNAuthenticationManager authenticationSvn(String userName, String password) throws SVNException {
		logger.info("updateLocalCodeBySvnRepoServiceImpl.authenticationSvn() start");
		/*
         * 对版本库设置认证信息。
         */
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password);
		
        logger.info("updateLocalCodeBySvnRepoServiceImpl.authenticationSvn() end");
        return authManager;
	}
    
    
	/** 
	 * @category 连接Svn服务器
	 * @param url 需要连接的库
	 * @param username 用户名
	 * @param password 密码
	 * @return 连接svn库
	 */
	@Override
	public SVNClientManager connectSvnRepos(String url, String userName, String password) throws SVNException {
		logger.info("updateLocalCodeBySvnRepoServiceImpl.connectSvnRepos() start");
		try {
			logger.info("开始认证");
			ISVNAuthenticationManager authManager = authenticationSvn(userName, password);
	       
			DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
	        options.setDiffCommand("-x -w"); 
	        
			SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
			logger.info("认证完成");
			logger.info("updateLocalCodeBySvnRepoServiceImpl.connectSvnRepos() end");
			return clientManager;
        } 
		catch (SVNException e) {
			logger.error("连接svn库：" + e);
			throw e;
        }
	}
    
    
	/**
     * @category 更新代码到指定版本
     * @Updates a working copy (brings changes from the repository into the working copy).
     * @param clientManager
     * @param wcPath working copy path
     * @param updateToRevision revision to update to
     * @param depth update的深度：目录、子目录、文件
     * @return
     * @throws SVNException
     */
    @Override
	public long update(SVNClientManager clientManager, File wcPath, SVNRevision updateToRevision, SVNDepth depth) throws SVNException {
    	logger.info("updateLocalCodeBySvnRepoServiceImpl.update() start");
    	
		SVNUpdateClient updateClient = clientManager.getUpdateClient();

		/*
		 * sets externals not to be ignored during the update
		 */
		updateClient.setIgnoreExternals(false);

		/*
		 * returns the number of the revision wcPath was updated to
		 */
		try {
			logger.info("updateLocalCodeBySvnRepoServiceImpl.update() end");
			return updateClient.doUpdate(wcPath, updateToRevision,depth, false, false);
		}
		catch (SVNException e) {
			logger.error("更新代码到指定版本:"+e.getMessage());
			throw new SVNException(e.getErrorMessage());
		}
	}

	/**
	 * @category 根据版本号，路径，检出到指定本地位置
	 * @param clientManager
	 * @param url a repository location from where a Working Copy will be checked out
	 * @param revision the desired revision of the Working Copy to be checked out
	 * @param destPath the local path where the Working Copy will be placed
	 * @param depth checkout的深度，目录、子目录、文件
	 * @return
	 * @throws SVNException
	 */
    @Override
	public long checkout(SVNClientManager clientManager, SVNURL url, SVNRevision revision, File destPath, SVNDepth depth) throws SVNException {

    	logger.info("updateLocalCodeBySvnRepoServiceImpl.checkout() start");
    	
		SVNUpdateClient updateClient = clientManager.getUpdateClient();
		/*
		 * sets externals not to be ignored during the checkout
		 */
		updateClient.setIgnoreExternals(false);
		/*
		 * returns the number of the revision at which the working copy is
		 */
		try {
			logger.info("updateLocalCodeBySvnRepoServiceImpl.checkout() end");
			return updateClient.doCheckout(url, destPath, revision, revision, depth, false);
		} 
		catch (SVNException e) {
			logger.error("checkout:"+e.getMessage());
			throw new SVNException(e.getErrorMessage());
		}
	}
    

}
