package com.bmtc.svn.service;

import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;

import com.bmtc.svn.domain.SvnInfo;
import com.bmtc.svn.domain.SvnUserRepoInfo;

/**
 * SvnAdminService业务逻辑接口
 * @author lpf7161
 *
 */
public interface SvnAdminService {

	/**
	 * 测试svn权限联通性
	 * @param SvnInfo, svnUserRepoInfo
	 */
	int svnRightPassTest(SvnInfo svnInfo, SvnUserRepoInfo svnUserRepoInfo) throws SVNAuthenticationException, SVNException, Exception;
	
}
