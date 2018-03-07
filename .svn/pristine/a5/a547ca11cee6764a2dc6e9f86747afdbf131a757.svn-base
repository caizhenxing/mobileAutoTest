package com.bmtc.svn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.svn.domain.SvnInfo;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnRootUser;
import com.bmtc.svn.domain.SvnUserAuthz;
import com.bmtc.svn.domain.SvnUserAuthzInfo;
import com.bmtc.svn.service.RepTreeNodeService;
import com.bmtc.svn.service.RepositoryService;
import com.bmtc.svn.service.SvnRepoService;
import com.bmtc.svn.service.SvnService;
import com.bmtc.svn.service.SvnUserRightService;
import com.bmtc.svn.service.SvnUserService;
import com.bmtc.svn.common.entity.PushMsg;
import com.bmtc.svn.common.web.BaseController;
import com.bmtc.svn.common.web.ExtractPathFromPattern;
import com.bmtc.system.service.ConfigService;

/**
 * svn用户权限管理前端接口处理
 * @author lpf7161
 *
 */
@RequestMapping("/svn/svnUserRight")
@Controller
public class SvnUserRightController extends BaseController {
	private static Logger logger = Logger.getLogger(SvnUserRightController.class);
	
	@Autowired
	private SvnUserRightService svnUserRightService;
	
	@Autowired
	private SvnRepoService svnRepoService;
	
	@Autowired
	private SvnUserService svnUserService;
	
	@Autowired
	private SvnService svnService;
	
	/**
	 * 仓库目录结构树节点服务层
	 */
	@Autowired
	private RepTreeNodeService repTreeNodeService;
	
	/**
	 * SVN仓库服务层
	 */
	@Autowired
	private RepositoryService repositoryService;
	
	/**
	 * 系统配置信息服务层
	 */
	@Autowired
	private ConfigService configService;
	
	@Autowired
	BMTCConfig bmtcConfig;
	
	String prefix = "svn/svnUserRight";
	
	@RequiresPermissions("svn:svnUserRight:svnUserRight")
	@Log("跳转到svn用户权限界面")
	@GetMapping("")
	String svnUserRight() {
		return prefix + "/svnUserRight";
	}
	
	/**
	 * 参数检查
	 * @param svnUserAuthz
	 * @return PushMsg
	 */
	private PushMsg checkSvnUserAuthzParam(SvnUserAuthz svnUserAuthz) {
		logger.info("SvnUserRightController.checkSvnUserAuthzParam() start");
		//校验参数
		if (svnUserAuthz == null) {
			logger.error("svnUserAuthz is null");
			return pushMsg("请求输入参数", false);
		}
		if (StringUtils.isEmpty(svnUserAuthz.getSvnUserAuthz())) {
			logger.error("svnUserAuthz is null");
			return pushMsg("请求输入svnUserAuthz参数", false);
		}
		if (StringUtils.isEmpty(svnUserAuthz.getSvnPath())) {
			logger.error("svnPath is null");
			return pushMsg("请求输入svnPath参数", false);
		}
		logger.info("SvnUserRightController.checkSvnUserAuthzParam() end");
		return pushMsg("参数校验成功", true);
	}
	
	
	@GetMapping("/treeView/{svnRepoName}") 
	@Log("添加需要开通权限的SVN路径")
	String svnPathTreeView(Model model, @PathVariable("svnRepoName") String svnRepoName) { 
		logger.info("SvnUserRightController.svnPathTreeView() start");
		model.addAttribute("svnRepoName", svnRepoName);
		logger.info("SvnUserRightController.svnPathTreeView() end");
		return prefix + "/svnPathTree";
	}
	

/*	@PostMapping("/tree")
	@Log("获取SVN路径树形图数据")
	@ResponseBody
	public Object tree(SvnRootUser svnRootUser) throws SVNException {
		logger.info("SvnUserRightController.tree() start");
		List<Tree<String>> trees = new ArrayList<Tree<String>>();
		try {
			trees = svnUserRightService.getTreeData(svnRootUser);
		} catch (SVNException e) {
			e.printStackTrace();
			return R.error(-1, e.getMessage());
		}
		logger.info("SvnUserRightController.tree() end");
		return trees;
	}*/
	
	
	@PostMapping("/tree")
	@Log("获取SVN路径树形图数据")
	@ResponseBody
	public Object tree(SvnRootUser svnRootUser) {
		logger.info("SvnUserRightController.tree() start");
		
		Tree<String> tree = new Tree<String>();
		try {
			tree = svnUserRightService.getTree(svnRootUser);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1, e.getMessage());
		}
		
		logger.info("SvnUserRightController.tree() end");
		return tree;
	}
	
	
/*	//@RequiresPermissions("svn:svnUserRight:editSvnUserRight")
	@Log("修改svn用户权限")
	@GetMapping("/edit/{svnUserName}/{svnRepoName}")
	String edit(Model model, @PathVariable("svnUserName") String svnUserName, 
			@PathVariable("svnRepoName") String svnRepoName) {
		logger.info("SvnUserRightController.edit() start");
		SvnInfo svnInfo = new SvnInfo();
		//准备数据
		svnInfo.setSvnUserName(svnUserName);
		svnInfo.setSvnRepoName(svnRepoName);
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		svnInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		model.addAttribute("svnInfo", svnInfo);
		logger.info("SvnUserRightController.edit() end");
		return prefix + "/edit";
	}*/
	
	@RequiresPermissions("svn:svnUserRight:addSvnUserRight")
	@Log("添加svn用户权限")
	@GetMapping("/add/{svnUserName}/{svnRepoName}")
	String add(Model model, @PathVariable("svnUserName") String svnUserName, 
			@PathVariable("svnRepoName") String svnRepoName) {
		logger.info("SvnUserRightController.add() start");
		SvnInfo svnInfo = new SvnInfo();
		//准备数据
		svnInfo.setSvnUserName(svnUserName);
		svnInfo.setSvnRepoName(svnRepoName);
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		svnInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		model.addAttribute("svnInfo", svnInfo);
		logger.info("SvnUserRightController.add() end");
		return prefix + "/add";
	}
	
	@RequiresPermissions("svn:svnUserRight:passTest")
	@Log("测试svn联通性")
	@GetMapping("/passTest/{svnUserName}/{svnRepoName}/{svnUserAuthz}/**")
	String passTest(Model model, HttpServletRequest request, @PathVariable("svnUserName") String svnUserName, 
			@PathVariable("svnRepoName") String svnRepoName, @PathVariable("svnUserAuthz") String svnUserAuthz) {
		logger.info("SvnUserRightController.passTest() start");
		SvnInfo svnInfo = new SvnInfo();
		//获取svn权限路径
		String svnPath = ExtractPathFromPattern.extractPathFromPattern(request);
		//准备数据
		svnInfo.setSvnUserName(svnUserName);
		svnInfo.setSvnRepoName(svnRepoName);
		svnInfo.setSvnPath(svnPath);
		svnInfo.setSvnUserAuthz(svnUserAuthz);
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		svnInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		model.addAttribute("svnInfo", svnInfo);
		logger.info("SvnUserRightController.passTest() end");
		return prefix + "/passTest";
	}
	
	@RequiresPermissions("svn:svnUserRight:removeSvnUserRight")
	@Log("删除svn用户权限")
	@GetMapping("/remove/{svnUserName}/{svnRepoName}")
	String remove(Model model, @PathVariable("svnUserName") String svnUserName, 
			@PathVariable("svnRepoName") String svnRepoName) {
		logger.info("SvnUserRightController.remove() start");
		SvnInfo svnInfo = new SvnInfo();
		//准备数据
		svnInfo.setSvnUserName(svnUserName);
		svnInfo.setSvnRepoName(svnRepoName);
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		svnInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		model.addAttribute("svnInfo", svnInfo);
		logger.info("SvnUserRightController.remove() end");
		return prefix + "/remove";
	}
	
	/**
	 * 增加用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录为0时将svnUserAuthz写入svn_user_authz表；
	 * 记录非0时，说明表中已经有该用户在该SVN仓库下svnPath的权限，则不用插入记录
	 * @param svnUserAuthzInfo
	 * @return R
	 */
	@RequiresPermissions("svn:svnUserRight:addSvnUserRight")
	@Log("新增svn用户权限")
	@PostMapping("/addSvnUserRight")
	@ResponseBody
	public R addSvnUserRight(SvnUserAuthzInfo svnUserAuthzInfo) {	
		logger.info("SvnUserRightController.addSvnUserRight() start");	
		
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserName())) {
			logger.error("svnUserName is null");
			return R.error(-1, "请求输入svnUserName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnPath())) {
			logger.error("svnPath is null");
			return R.error(-1, "请求输入svnPath参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserAuthz())) {
			logger.error("svnUserAuthz is null");
			return R.error(-1, "请求输入svnUserAuthz参数");
		}
		
		//根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId
		String svnRepoIdStr = svnRepoService.querySvnRepoIdBySvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		//根据SVN用户名和仓库名执行querySvnUser()得到svnUserId
		String svnUserIdStr = svnUserService.querySvnUserId(svnUserAuthzInfo.getSvnUserName(), 
				svnUserAuthzInfo.getSvnRepoName());
		
		long svnUserId = 0;
		if("".equals(svnUserIdStr) || svnUserIdStr == null) {
			
			logger.error("svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnUserId = Long.parseLong(svnUserIdStr);
		}
		
		SvnUserAuthz svnUserAuthz = new SvnUserAuthz();
		//对svnUserAuthz相关属性赋值
		svnUserAuthz.setSvnPath(svnUserAuthzInfo.getSvnPath());
		svnUserAuthz.setSvnUserAuthz(svnUserAuthzInfo.getSvnUserAuthz());
		svnUserAuthz.setSvnUserId(svnUserId);
		svnUserAuthz.setSvnRepoId(svnRepoId);
		svnUserAuthz.setStatus(svnUserAuthzInfo.getAuthzStatus());
		svnUserAuthz.setSvnRepoName(svnUserAuthzInfo.getSvnRepoName() );
		
		//校验svnUserAuthz参数
		PushMsg pmg = checkSvnUserAuthzParam(svnUserAuthz);
		if (!pmg.getStatus()) {
			logger.error("svnUserAuthz is null");
			return R.error(-1, "svnUserAuthz is null");
		}
		
		int res = 0;
		try {
			//将svnUserAuthz写入svn_user_authz表
			res = svnUserRightService.addSvnUserRight(svnUserAuthz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		
		if(res == -1) {
			logger.error("svn_user_authz表中已存在svnUserId=" + svnUserAuthz.getSvnUserId() + ", svnPath=" 
					+ svnUserAuthz.getSvnPath() + "的权限记录");
			return R.error(-1, "svn_user_authz表中已存在svnUserId=" + svnUserAuthz.getSvnUserId() + 
					", svnPath=" + svnUserAuthz.getSvnPath() + "的权限记录");
		} else if(res > 0) {
			logger.info("添加" + res + "条SVN用户权限完成");
		}
		
		logger.info("SvnUserRightController.addSvnUserRight() end");
		return R.ok();
	}
	
	

	/**
	 * 删除用户SVN权限
	 * 首先根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录非0时删除用户SVN权限
	 * @param svnUserAuthzInfo
	 * @return R
	 */
	@RequiresPermissions("svn:svnUserRight:removeSvnUserRight")
	@Log("删除svn用户权限")
	@PostMapping("/deleteSvnUserRight")
	@ResponseBody
	public Object deleteSvnUserRight(SvnUserAuthzInfo svnUserAuthzInfo) {
		logger.info("SvnUserRightController.deleteSvnUserRight() start");
		
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserName())) {
			logger.error("svnUserName is null");
			return R.error(-1, "请求输入svnUserName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnPath())) {
			logger.error("svnPath is null");
			return R.error(-1, "请求输入svnPath参数");
		}
		
		// 根据SVN用户名和仓库名执行querySvnUser()得到svnUserId
		String svnUserIdStr = svnUserService.querySvnUserId(svnUserAuthzInfo.getSvnUserName(), 
				svnUserAuthzInfo.getSvnRepoName());
		
		long svnUserId = 0;
		if("".equals(svnUserIdStr) || svnUserIdStr == null) {		
			logger.error("svn_user数据表中不存在svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_user数据表中不存在svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnUserId = Long.parseLong(svnUserIdStr);
		}
		
		svnUserAuthzInfo.setSvnUserId(svnUserId);
		
		//根据svnUserId和svnPath删除SVN用户权限
		int res = 0;
		try {
			res = svnUserRightService.deleteSvnUserRight(svnUserAuthzInfo.getSvnUserName(), svnUserAuthzInfo.getSvnUserId(), 
					svnUserAuthzInfo.getSvnRepoName(), svnUserAuthzInfo.getSvnPath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		
		if(res == -1) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else if (res == -2){
			logger.info("svn_user_authz数据表中不存在svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "',且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "',且svn路径为'" + svnUserAuthzInfo.getSvnPath() +"'的记录" +"的记录");
			
			return R.error(-1, "svn_user_authz数据表中不存在svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "',且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "',且svn路径为'" + svnUserAuthzInfo.getSvnPath() +"'的记录");
		} else if(res > 0) {
			logger.info("根据svnUserId和svnPath删除" + res + "条SVN用户权限完成");
		}
		
		logger.info("SvnUserRightController.deleteSvnUserRight() end");
		return R.ok();
	}
	
	
	/**
	 * 删除用户SVN权限，首先根据svnUserName执行querySvnUserIdBySvnUserName()得到svnUserId集，
	 * 然后根据svnUserId查询记录个数，记录非0时删除用户SVN权限，
	 * 根据svnUserId集逐个删除用户SVN权限
	 * 即删除所有与用户名为svnUserName相关的权限信息
	 * @param svnUserName
	 * @return PushMsg
	 */
	@RequestMapping(value = "/deleteSvnUserRightBySvnUserId")
	@ResponseBody
	public Object deleteSvnUserRightBySvnUserId(@RequestParam String svnUserName) {
		logger.info("SvnUserRightController.deleteSvnUserRightBySvnUserId() start");
		PushMsg pmg = new PushMsg();
		
		//校验参数
		if (StringUtils.isEmpty(svnUserName)) {
			logger.info("svnUserName is null");
			pmg = pushMsg("请求输入svnUserName参数", false);
			return pmg;
		}
		
		pmg = pushMsg("参数校验成功", true);
		
		//根据SVN用户名查询svnUserId集
		List<String> svnUserIdList = svnUserRightService.querySvnUserIdBySvnUserName(svnUserName);
		
		if(svnUserIdList.size() == 0) {
			
			logger.error("svn_user表中不存在svn用户名为'" + svnUserName + "'的用户记录");
			pmg = pushMsg("svn_user表中不存在svn用户名为'" + svnUserName + "'的用户记录", false);
			
			return pmg;
		}
		int deleteCnt = 0;
		for(String svnUserIdStr : svnUserIdList) {
			int cnt = 0;
			long svnUserId = 0;
			int res = 0;
			//判断svnUserIdStr是否为空
			if("".equals(svnUserIdStr) || svnUserIdStr == null) {
				
				logger.error("svn_user数据表中不存在svn用户名为'" + svnUserName + "'的记录");
				pmg = pushMsg("svn_user数据表中不存在svn用户名为'" + svnUserName + "'的记录", false);
				
				return pmg;
			} else {
				svnUserId = Long.parseLong(svnUserIdStr);
			}
			
			cnt = svnUserRightService.countBySvnUserId(svnUserId);
			//cnt不为0时，删除用户记录
			if(cnt != 0) {
				res = svnUserRightService.deleteSvnUserRightBySvnUserId(svnUserId);
				if(res == 0) {
					logger.info("根据svnUserName(svnUserId=" + svnUserId + ")删除svn权限操作异常");
					return pmg;
				} else {
					pmg.setArg1(res);
					deleteCnt++;
					logger.info("根据svnUserName(svnUserId=" + svnUserId + ")删除" + res + "条SVN用户完成");
				}
			} else {
				logger.error("svn_user_authz表中不存在svn用户id为'" + svnUserId + "'的用户权限记录");			
			}
		}
		pmg.setArg1(deleteCnt);
		pmg.setResult(svnUserIdList.size());
		logger.info("SvnUserRightController.deleteSvnUserRightBySvnUserId() end");
		return pmg;
	}
	
	
	/**
	 * 根据SVN用户名、库名和SVN路径修改用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 然后根据svnUserId和svnPath查询记录个数，记录非0时修改svnUserAuthz信息.
	 * @param svnUserAuthzInfo
	 * @return R
	 */
	@RequestMapping(value = "/updateSvnUserRight")
	@ResponseBody
	public Object updateSvnUserRight(@RequestBody SvnUserAuthzInfo svnUserAuthzInfo) {
		logger.info("SvnUserRightController.updateSvnUserRight() start");
		
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserName())) {
			logger.error("svnUserName is null");
			return R.error(-1, "请求输入svnUserName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnPath())) {
			logger.error("svnPath is null");
			return R.error(-1, "请求输入svnPath参数");
		}
		
		//根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId
		String svnRepoIdStr = svnRepoService.querySvnRepoIdBySvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		//根据SVN用户名和仓库名执行querySvnUser()得到svnUserId
		String svnUserIdStr = svnUserService.querySvnUserId(svnUserAuthzInfo.getSvnUserName(), 
				svnUserAuthzInfo.getSvnRepoName());
		
		long svnUserId = 0;
		if("".equals(svnUserIdStr) || svnUserIdStr == null) {
			logger.error("svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnUserId = Long.parseLong(svnUserIdStr);
		}
		
		SvnUserAuthz svnUserAuthz = new SvnUserAuthz();
		//对svnUserAuthz相关属性赋值
		svnUserAuthz.setSvnPath(svnUserAuthzInfo.getSvnPath());
		svnUserAuthz.setSvnUserAuthz(svnUserAuthzInfo.getSvnUserAuthz());
		svnUserAuthz.setSvnUserId(svnUserId);
		svnUserAuthz.setSvnRepoId(svnRepoId);
		svnUserAuthz.setStatus(svnUserAuthzInfo.getAuthzStatus());
		svnUserAuthz.setSvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		//校验svnUserAuthz参数
		PushMsg pmg = checkSvnUserAuthzParam(svnUserAuthz);
		if (!pmg.getStatus())
		{
			logger.error("svnUserAuthz is null");
			return R.error(-1, "svnUserAuthz is null");
		}
		
		int res = 0;
		//将svnUserAuthz写入svn_user_authz表
		try {
			res = svnUserRightService.updateSvnUserRight(svnUserAuthz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		if(res == 0) {
			logger.info("svn_user_authz数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "',且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "',且svn路径为'" + svnUserAuthzInfo.getSvnPath() +"'的记录" +"的记录");
			return R.error(-1, "svn_user_authz数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "',且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "',且svn路径为'" + svnUserAuthzInfo.getSvnPath() +"'的记录");
		} else {
			logger.info("修改" + res + "条SVN用户权限完成");
		}
		logger.info("SvnUserRightController.updateSvnUserRight() end");
		return R.ok();
	}
	

	/**
	 * 增加用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录为0时将svnUserAuthz写入svn_user_authz表；
	 * 记录非0时，修改原记录信息
	 * @param svnUserAuthzInfo
	 * @return R
	 */
	@RequestMapping(value = "/saveSvnUserRight")
	@ResponseBody
	public Object saveSvnUserRight(@RequestBody SvnUserAuthzInfo svnUserAuthzInfo) {	
		logger.info("SvnUserRightController.saveSvnUserRight() start");	
		
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserName())) {
			logger.error("svnUserName is null");
			return R.error(-1, "请求输入svnUserName参数");
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnPath())) {
			logger.error("svnPath is null");
			return R.error(-1, "请求输入svnPath参数");
		}
		
		//根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId
		String svnRepoIdStr = svnRepoService.querySvnRepoIdBySvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		//根据SVN用户名和仓库名执行querySvnUser()得到svnUserId
		String svnUserIdStr = svnUserService.querySvnUserId(svnUserAuthzInfo.getSvnUserName(), 
				svnUserAuthzInfo.getSvnRepoName());
		
		long svnUserId = 0;
		if("".equals(svnUserIdStr) || svnUserIdStr == null) {
			logger.error("svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
		} else {
			svnUserId = Long.parseLong(svnUserIdStr);
		}
		
		SvnUserAuthz svnUserAuthz = new SvnUserAuthz();
		//对svnUserAuthz相关属性赋值
		svnUserAuthz.setSvnPath(svnUserAuthzInfo.getSvnPath());
		svnUserAuthz.setSvnUserAuthz(svnUserAuthzInfo.getSvnUserAuthz());
		svnUserAuthz.setSvnUserId(svnUserId);
		svnUserAuthz.setSvnRepoId(svnRepoId);
		svnUserAuthz.setStatus(svnUserAuthzInfo.getAuthzStatus());
		svnUserAuthz.setSvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		//校验svnUserAuthz参数
		PushMsg pmg = checkSvnUserAuthzParam(svnUserAuthz);
		if (!pmg.getStatus()) {
			logger.error("svnUserAuthz is null");
			return R.error(-1, "svnUserAuthz is null");
		}
		
		int res = 0;
		//将svnUserAuthz写入svn_user_authz表
		try {
			res = svnUserRightService.saveSvnUserRight(svnUserAuthz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		if(res != 0) {
			logger.info("添加或者修改" + res + "条SVN用户权限完成");
		} else {
			logger.error("添加或者修改SVN用户权限异常");
			return R.error(-1, "添加或者修改SVN用户权限异常");
		}
		logger.info("SvnUserRightController.saveSvnUserRight() end");
		return R.ok();
	}
	
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和svnRepoId执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询该用户在该仓库下的svn权限信息集
	 * @param svnUserAuthzInfo
	 * @return List<SvnUserAuthzInfo>
	 */
	@GetMapping("/querySvnUserRightBySvnUserId")
	@ResponseBody
	public Object querySvnUserRightBySvnUserId(@RequestParam Map<String, Object> params) {
		logger.info("SvnUserRightController.querySvnUserRightBySvnUserId() start");
		
		SvnUserAuthzInfo svnUserAuthzInfo = new SvnUserAuthzInfo();
		
		svnUserAuthzInfo.setSvnUserName(params.get("svnUserName").toString());
		svnUserAuthzInfo.setSvnRepoName(params.get("svnRepoName").toString());
		
		PushMsg pmsg = new PushMsg();
		
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnUserName())) {
			logger.error("svnUserName is null");
			pmsg = pushMsg("请求输入svnUserName参数", false);
			return pmsg;
		}
		if (StringUtils.isEmpty(svnUserAuthzInfo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			pmsg = pushMsg("请求输入svnRepoName参数", false);
			return pmsg;
		}
		
		pmsg = pushMsg("参数校验成功", true);
		
		//根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId
		String svnRepoIdStr = svnRepoService.querySvnRepoIdBySvnRepoName(svnUserAuthzInfo.getSvnRepoName());
		
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			pmsg = pushMsg("svn_repo数据表中不存在svn仓库名为'" + svnUserAuthzInfo.getSvnRepoName() + "'的记录", false);
			
			return pmsg;
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		//根据SVN用户名和仓库名执行querySvnUser()得到svnUserId
		String svnUserIdStr = svnUserService.querySvnUserId(svnUserAuthzInfo.getSvnUserName(), 
				svnUserAuthzInfo.getSvnRepoName());
		
		long svnUserId = 0;
		if("".equals(svnUserIdStr) || svnUserIdStr == null) {
			
			logger.error("svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录");
			pmsg = pushMsg("svn_user数据表中没有svn用户名为'" + svnUserAuthzInfo.getSvnUserName() + "'，且svn仓库名为'" 
					+ svnUserAuthzInfo.getSvnRepoName() + "'的记录", false);
			
			return pmsg;
		} else {
			svnUserId = Long.parseLong(svnUserIdStr);
		}
		
		svnUserAuthzInfo.setSvnUserId(svnUserId);
		svnUserAuthzInfo.setSvnRepoId(svnRepoId);
		params.put("svnUserId", svnUserAuthzInfo.getSvnUserId());
		params.put("svnRepoId", svnUserAuthzInfo.getSvnRepoId());
		
		//根据svnUserId查询SVN用户权限信息
		List<SvnUserAuthzInfo> list = svnUserRightService.querySvnUserRight(params);
		logger.info("根据svnUserId查询SVN用户权限信息完成");
		logger.info("SvnUserRightController.querySvnUserRightBySvnUserId() end");
		return list;
	}
	
	
	@RequiresPermissions("svn:svnUserRight:querySvnUserRight")
	@Log("查询svn用户权限信息")
	@GetMapping("/querySVNRight")
	String querySVNRight() {
		return prefix + "/query";
	}
	
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限
	 * @param svnUserAuthzInfo
	 * @return List<SvnUserAuthzInfo>
	 */
	@RequiresPermissions("svn:svnUserRight:querySvnUserRight")
	@GetMapping("/querySvnUserRight")
	@Log("查询svn权限列表数据")
	@ResponseBody
	public Object querySvnUserRight(@RequestParam Map<String, Object> params) {
		logger.info("SvnUserRightController.querySvnUserRight() start");
		// 分页查询参数数据
		Query query = new Query(params);
		// 根据SVN用户名(可为空)、仓库名(可为空)、SVN路径(可为空)、SVN权限(可为空)查询用户SVN权限
		List<SvnUserAuthzInfo> list = svnUserRightService.querySvnRight(query);
		// 查询总记录数
		int total = svnUserRightService.countSvnRight(query);
		// 封装到返回对象
		PageUtils pageUtil = new PageUtils(list, total);
		logger.info("SvnUserRightController.querySvnUserRight() end");
		// 返回数据
		return pageUtil;
	}
	
	
	/**
	 * 根据SVN用户名查询用户SVN权限，首先根据SVN用户名执行querySvnUserIdBySvnUserName()得到svnUserId集，
	 * 然后根据svnUserId集查询该用户在每一个仓库中的svn权限信息集
	 * @param svnUserName
	 * @return List<SvnUserAuthzInfo>
	 */
	@RequestMapping(value = "/querySvnUserRightBySvnUserName")
	@ResponseBody
	public Object querySvnUserRightBySvnUserName(@RequestParam Map<String, Object> params) {
		logger.info("SvnUserRightController.querySvnUserRightBySvnUserName() start");
		PushMsg pmsg = new PushMsg();
		
		String svnUserName = params.get("svnUserName").toString();
		
		if (StringUtils.isEmpty(svnUserName)) {
			logger.error("svnUserName is null");
			pmsg = pushMsg("请求输入svnUserName参数", false);
			return pmsg;
		}
		
		pmsg = pushMsg("参数校验成功", true);
		
		List<String> svnUserIdList = svnUserRightService.querySvnUserIdBySvnUserName(svnUserName);
		
		if(svnUserIdList.size() == 0) {
			
			logger.error("svn_user表中不存在svn用户名为'" + svnUserName + "'的用户记录");
			pmsg = pushMsg("svn_user表中不存在svn用户名为'" + svnUserName + "'的用户记录", false);
			
			return pmsg;
		}
		
		// Map<svnUserName, List<SvnUserAuthzInfo>>
		Map<String, List<SvnUserAuthzInfo>> svnUserAuthzInfos = new HashMap<String, List<SvnUserAuthzInfo>>();
		
		for(String svnUserIdStr : svnUserIdList) {
			
			long svnUserId = 0;
			
			//判断svnUserIdStr是否为空
			if("".equals(svnUserIdStr) || svnUserIdStr == null) {
				
				logger.error("svn_user数据表中不存在svn用户名为'" + svnUserName + "'的记录");
				pmsg = pushMsg("svn_user数据表中不存在svn用户名为'" + svnUserName + "'的记录", false);
				
				return pmsg;
			} else {
				svnUserId = Long.parseLong(svnUserIdStr);
			}
			params.put("svnUserId", svnUserId);
			//根据svnUserId查询SVN用户权限信息
			List<SvnUserAuthzInfo> svnUserAuthzInfoList = svnUserRightService.querySvnUserRightBySvnUserId(params);	
			logger.info("根据svnUserName查询SVN用户权限信息完成");
			
			// 格式化返回数据
			for (SvnUserAuthzInfo svnUserAuthzInfo : svnUserAuthzInfoList) {
				List<SvnUserAuthzInfo> authList = svnUserAuthzInfos.get(svnUserAuthzInfo.getSvnUserName());
				if (authList == null) {
					authList = new ArrayList<SvnUserAuthzInfo>();
					svnUserAuthzInfos.put(svnUserAuthzInfo.getSvnUserName(), authList);
				}
				authList.add(svnUserAuthzInfo);
			}
			
		}
		
		logger.info("SvnUserRightController.querySvnUserRightBySvnUserName() end");
		//返回所有的list
		return svnUserAuthzInfos;
	}
	
}
