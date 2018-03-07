/**
 * 
 */
package com.bmtc.svn.service.impl;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.dao.SvnUserDao;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.service.RepositoryService;

/**
 * 仓库服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
@Service("RepositoryService")
public class RepositoryServiceImpl implements RepositoryService {
	
	/**
	 * 日志
	 */
	private static Logger LOG = Logger.getLogger(RepositoryServiceImpl.class);
	
	/**
	 * 项目DAO
	 */
	@Autowired
	private SvnRepoDao svnRepoDao;
	
	/**
	 * 项目用户DAO
	 */
	@Autowired
	private SvnUserDao svnUserDao;
	
	/**
	 * 获取svn仓库
	 * @param svnRepoName 项目名称, svnUserName svn用户名, svnPassword svn密码
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	@Override
	public SVNRepository getRepository(String svnRepoName, String svnUserName, 
			String svnPassword) throws SVNException {
		SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoName(svnRepoName);
		if(svnRepo == null){
			LOG.warn("Not found project: " + svnRepoName);
			return null;
		}
		return getRepository(svnRepo, svnUserName, svnPassword);
	}
	
	/**
	 * 从项目的url中获取svn的url
	 * @param url 项目url
	 * @return svn url
	 */
	@Override
	public String parseURL(String url) {
		if(StringUtils.isBlank(url)) {
			return null;
		}
		String result = url.trim();//去空格
		result = StringUtils.replace(result, "\t", " ");
		result = StringUtils.replace(result, "\r", " ");
		result = StringUtils.replace(result, "\n", " ");
		result = StringUtils.replace(result, "\b", " ");
		result = StringUtils.replace(result, "<", " ");//eg. <br/>
		result = StringUtils.replace(result, "(", " ");//eg. ()
		
		result = result.trim();
		int blank = result.indexOf(" ");
		if(blank != -1){
			result = result.substring(0, blank);
		}
		
		return result;
	}
	
	/**
	 * 获取svn仓库
	 * @param svnRepo 项目, svnUserName svn用户名, svnPassword svn密码
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	@Override
	public SVNRepository getRepository(SvnRepo svnRepo, String svnUserName, 
			String svnPassword) throws SVNException {
		
		String svnUrl = parseURL(svnRepo.getSvnRepoUrl());
		if(StringUtils.isBlank(svnUrl)){
			throw new RuntimeException("URL不可以为空");
		}
		
    	@SuppressWarnings("deprecation")
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(svnUrl));
	    ISVNAuthenticationManager authManager = 
	                 SVNWCUtil.createDefaultAuthenticationManager(svnUserName, svnPassword);
	    repository.setAuthenticationManager(authManager);
	     
	    return repository;
	}
	
	/**
	 * 返回项目仓库的根
	 * @param svnRepo 项目
	 * @param svnUserName SVN用户名
	 * @param svnPassword SVN用户密码
	 * @return 仓库根
	 */
	@Override
	public String getRepositoryRoot(SvnRepo svnRepo, String svnUserName, 
			String svnPassword) {
		SVNRepository repository = null;
		try {
			repository = getRepository(svnRepo,svnUserName, svnPassword);
			return repository.getRepositoryRoot(true).toString();
		} catch(SVNAuthenticationException e) {
    		LOG.error(e.getMessage());
    		return null;
    	} catch (SVNException e) {
    		LOG.error(e.getMessage());
    		e.printStackTrace();
			return null;
		} finally {
			if(repository != null) {
				repository.closeSession();
			}
		}
	}
	
	
	/**
	 * 获取项目指定路径的svn仓库文件系统
	 * @param svnRepoName 项目
	 * @param path 相对仓库根目录的路径
	 * @param svnUserName SVN用户名
	 * @param svnPassword SVN用户密码
	 * @return 目录或文件系统
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<SVNDirEntry> getDir(String svnRepoName, String path, String svnUserName, 
			String svnPassword, SVNRepository repository) throws SVNException {
		if(StringUtils.isBlank(path)) {
			path = "/";//root
		}
		if(!path.startsWith("/")){
			path = "/" + path;
		}
		//SVNRepository repository = null;
		try {
			//repository = getRepository(svnRepoName, svnUserName, svnPassword);
			SVNProperties properties = new SVNProperties();
	    	return repository.getDir(path, SVNRevision.HEAD.getNumber(), properties, (Collection) null);
    	} catch(SVNAuthenticationException e) {
    		e.printStackTrace();
			throw new RuntimeException("认证失败");
    	} catch (SVNException e) {
    		e.printStackTrace();
    		throw new RuntimeException(e.getMessage());
		} finally {
			if(repository!=null) {
				repository.closeSession();
			}
		}
	}

	/**
     * Creates a local blank FSFS-type repository.
     * A call to this routine is equivalent to 
     * <code>createLocalRepository(path, null, enableRevisionProperties, force)</code>.
     * 
     * @param  respository                          a repository root location
     * @return                               a local URL (file:///) of a newly
     *                                       created repository
     */
	@Override
	public SVNURL createLocalRepository(File respository) {
		try {
			return SVNRepositoryFactory.createLocalRepository(respository, true,
					false);
		} catch (SVNException e) {
/*			throw new RuntimeException(new Object[]{respository.getAbsolutePath()}
			+ ": 创建svn仓库失败("+ e.getMessage() + ")");*/
			throw new RuntimeException("创建svn仓库失败("+ e.getMessage() + ")");
		}
	}
	
	@Override
	public boolean testRepositoryConnected(SVNRepository repository, String svnUrl) throws SVNException {
 		boolean isSucceed = false;
		try {
			String[] codepath = getCodePaths(repository, svnUrl);
			long latestRevision = repository.getLatestRevision(); // -1代表最新版本号，初始版本为0
			SVNDirEntry dir = getTargetInfo(repository, codepath[0], latestRevision); //SVNRevision.HEAD.getNumber()
			if (dir != null) {
				isSucceed = true;
				LOG.info(svnUrl + " testlogin success ");
			}
		} catch (SVNException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return isSucceed;
	}

	@Override
	public SVNDirEntry getTargetInfo(SVNRepository repository, String path, long revision) throws SVNException {
		SVNDirEntry collect = null;
		try {
			collect = repository.info(path, revision);
		} catch (SVNException e) {
			collect = null;
			e.printStackTrace();		
			LOG.error(e.getMessage());
		}
		return collect;
	}
	
	@Override
	public String[] getCodePaths(SVNRepository repository, String url) throws SVNException {
		String codepath = getSvnRoot(repository);
		int homeIndex = url.indexOf(codepath);
		return new String[] { url.substring(homeIndex + codepath.length()) };
	}
	
	@Override
	public String getSvnRoot(SVNRepository repository) throws SVNException {
		SVNURL url = null;
		try {
			url = repository.getRepositoryRoot(false);
			return url.getPath();
		} catch (SVNException e) {
			e.printStackTrace();
			LOG.error("You have no right get this repository root url!!!");
		}
		return null;
	}
	
	static {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }

}
