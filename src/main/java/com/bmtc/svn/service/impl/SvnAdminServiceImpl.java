package com.bmtc.svn.service.impl;

import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.common.utils.SVNUtil;
import com.bmtc.svn.domain.SvnInfo;
import com.bmtc.svn.domain.SvnUserRepoInfo;
import com.bmtc.svn.service.SvnAdminService;


/**
 * SvnAdminService业务逻辑实现
 * @author lpf7161
 *
 */
@Service("SvnAdminService")
public class SvnAdminServiceImpl implements SvnAdminService {
	private static Logger logger = Logger.getLogger(SvnAdminServiceImpl.class);
	@Override
	public int svnRightPassTest(SvnInfo svnInfo, SvnUserRepoInfo svnUserRepoInfo) 
			throws SVNAuthenticationException, SVNException, Exception {
		logger.info("SvnAdminServiceImpl.svnRightPassTest() start");
		String svnRepoUrl = svnInfo.getSvnRepoUrl();
		String svnUrl = svnInfo.getSvnRepoUrl();
		String svnPath = svnInfo.getSvnPath();
		String svnUserName = svnInfo.getSvnUserName();
		String svnPassword = svnUserRepoInfo.getSvnPassword();
		//对SVN用户密码进行解密
		svnPassword = EncryptUtil.decrypt(svnPassword);
		//截取svn路径，如[test:/svnadmin_init/test]，获取/svnadmin_init/test
		svnPath = svnPath.split(":")[1].substring(0, svnPath.split(":")[1].length() - 1);
		if(StringUtils.isBlank(svnPath)){
			svnPath = "/";//root路径
		}
		if(!svnPath.startsWith("/")){
			svnPath = "/" + svnPath;
		}
		svnUrl = svnUrl + svnPath;
		SVNRepository repository = null;
		// boolean isSucceed = false;
		try {
			// 测试svn权限开通成功与否
			// 获取svn仓库信息
			repository = SVNUtil.getRepository(svnRepoUrl, svnUserName, svnPassword);		
			// 若用户名和密码错误，此处便会抛出SVNAuthenticationException，前端显示认证失败的异常
			repository.testConnection();
			long latestRevision = repository.getLatestRevision(); // 修订-1代表最新版本号，初始版本为0
			// 若此用户在svnUrl下没有权限，则抛出SVNAuthenticationException，前端显示认证失败的异常
			repository.checkPath(svnPath, latestRevision);
			// isSucceed = repositoryService.testRepositoryConnected(repository, svnUrl);
		} catch(SVNAuthenticationException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.info("SvnAdminServiceImpl.svnRightPassTest() end");
			throw new RuntimeException("svn.auth.error:认证失败(" + e.getMessage() + ")");
    	} catch (SVNException e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		logger.info("SvnAdminServiceImpl.svnRightPassTest() end");		
    		throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage());
    		logger.info("SvnAdminServiceImpl.svnRightPassTest() end");		
    		throw new RuntimeException(e.getMessage());
		} finally {
			if(repository != null) {
				repository.closeSession();
			}
		}
		logger.info("SvnAdminServiceImpl.svnRightPassTest() end");	
/*		if(isSucceed == true) return R.ok("SVN认证成功");
		return R.error(-1, "SVN认证失败");*/
		return 1;
	}
}
