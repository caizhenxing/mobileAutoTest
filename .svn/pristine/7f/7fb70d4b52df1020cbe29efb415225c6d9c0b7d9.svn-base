package com.bmtc.svn.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNException;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.utils.R;
import com.bmtc.svn.common.web.BaseController;
import com.bmtc.svn.domain.SvnInfo;
import com.bmtc.svn.domain.SvnUserRepoInfo;
import com.bmtc.svn.service.SvnAdminService;
import com.bmtc.svn.service.SvnUserService;

/**
 * svn管理前端接口处理
 * @author lpf7161
 *
 */
@RequestMapping("/svn/admin")
@Controller
public class SvnAdminController extends BaseController {
	private static Logger logger = Logger.getLogger(SvnAdminController.class);
	
	@Autowired 
	private SvnUserService svnUserService;
	
	@Autowired 
	private SvnAdminService svnAdminService;
	
	@RequiresPermissions("svn:svnUserRight:passTest")
	@PostMapping("/svnRightPassTest")
	@Log("测试svn联通性")
	@ResponseBody
	public R svnRightPassTest(SvnInfo svnInfo) {
		logger.info("SvnAdminController.svnRightPassTest() start");
		String svnUserName = svnInfo.getSvnUserName();
		String svnRepoName = svnInfo.getSvnRepoName();
		SvnUserRepoInfo svnUserRepoInfo = svnUserService.querySvnUser(svnUserName, svnRepoName);
		int res = 0;
		try {
			res = svnAdminService.svnRightPassTest(svnInfo, svnUserRepoInfo);
		} catch(SVNAuthenticationException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, "svn.auth.error:认证失败(" + e.getMessage() + ")");
		} catch (SVNException e) {
			e.printStackTrace();
			logger.error(e.getMessage());		
			return R.error(-1, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());	
			return R.error(-1, e.getMessage());
		}
		logger.info("SvnAdminController.svnRightPassTest() end");
		if(res == 1) {
			return R.ok("SVN认证成功");
		}
		return R.error(-1, "SVN认证失败");
	}
}
