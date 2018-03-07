package com.bmtc.svn.controller;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.svn.domain.SvnCreateBranch;
import com.bmtc.svn.domain.SvnCreateBranchInfo;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.service.SvnCreateBranchService;
import com.bmtc.svn.service.SvnRepoService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/svn/svnCreateBranch")
@Controller
public class SvnCreateBranchController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SvnCreateBranchController.class);
	
	private String prefix = "/svn/svnCreateBranch";
	
	@Autowired
	SvnCreateBranchService svnCreateBranchService;
	
	@Autowired
	private SvnRepoService svnRepoService;
	
	@RequiresPermissions("svn:svnCreateBranch:svnCreateBranch")
	@Log("跳转到SVN新建分支界面")
	@GetMapping("")
	String svnCreateBranch(Model model) {
		return prefix + "/svnCreateBranch";
	}

	@GetMapping("/list")
	@Log("查询列表数据")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("SvnCreateBranchController.list() start");
		Query query = new Query(params);
		List<SvnCreateBranchInfo> svnCreateBranchInfoList = svnCreateBranchService.list(query);
		int total = svnCreateBranchService.count(query);
		PageUtils pageUtil = new PageUtils(svnCreateBranchInfoList, total);
		logger.info("SvnCreateBranchController.list() end");
		return pageUtil;
	}

	@RequiresPermissions("svn:svnCreateBranch:edit")
	@Log("编辑SVN新建分支信息")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		logger.info("SvnCreateBranchController.edit() start");
		SvnCreateBranchInfo svnCreateBranchInfo = svnCreateBranchService.get(id);
		model.addAttribute("svnCreateBranchInfo", svnCreateBranchInfo);
		logger.info("SvnCreateBranchController.edit() end");
		return prefix + "/edit";
	}
	
	@RequiresPermissions("svn:svnCreateBranch:add")
	@Log("保存SVN新建分支信息")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}
	
	@GetMapping("/svnRepoTreeView") 
	@Log("添加SVN用户时访问SVN仓库树形图")
	String svnRepoTreeView() { 
		return prefix + "/svnRepoTree";
	}
	
	@GetMapping("/svnTrunkTreeView/{svnRepoName}") 
	@Log("添加需要新建分支的SVN基线")
	String svnTrunkTreeView(Model model, @PathVariable("svnRepoName") String svnRepoName) { 
		logger.info("SvnCreateBranchController.svnTrunkTreeView() start");
		SvnRepo svnRepoInfo = new SvnRepo();
		// 准备数据
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		svnRepoInfo.setSvnRepoName(svnRepoName);
		svnRepoInfo.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		model.addAttribute("svnRepoInfo", svnRepoInfo);
		logger.info("SvnCreateBranchController.svnTrunkTreeView() end");
		return prefix + "/svnTrunkTree";
	}
	
	
	@RequiresPermissions("svn:svnCreateBranch:add")
	@Log("保存SVN新建分支信息")
	@PostMapping("/save")
	@ResponseBody
	R save(SvnCreateBranch svnCreateBranch) {
		logger.info("SvnCreateBranchController.save() start");	
		
		if (svnCreateBranchService.querySvnCreateBranchInfo(svnCreateBranch) != null) {
			logger.error("已经存在【svn基线为:" + svnCreateBranch.getSvnTrunk() +"，分支为:"
					+ svnCreateBranch.getNewBranch() + "】的svn分支");
			return R.error(-1, "已经存在【svn基线为:" + svnCreateBranch.getSvnTrunk() +"，分支为:"
					+ svnCreateBranch.getNewBranch() + "】的svn分支");
		}
		
		String res = "";
		try {
			res = svnCreateBranchService.save(svnCreateBranch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		
		if (res.equals("-1")) {
			return R.error(-1, "新建SVN分支失败，请先给超级用户开通【" + svnCreateBranch.getSvnRepoName() + "】仓库的根目录读写权限");
		} else if (res.equals("-2")) {
			return R.error(-1, "新建SVN分支成功，信息入库失败");
		} else if (res.equals("-3")) {
			return R.error(-1, "新建SVN分支失败");
		}
		
		logger.info("SvnCreateBranchController.save() end");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("msg", res);
		return R.ok(map);
	}

	@RequiresPermissions("svn:svnCreateBranch:edit")
	@Log("更新SVN新建分支信息")
	@PostMapping("/update")
	@ResponseBody
	R update(SvnCreateBranch svnCreateBranch) {
		logger.info("SvnCreateBranchController.update() start");
		if (svnCreateBranchService.update(svnCreateBranch) > 0) {
			logger.info("SvnCreateBranchController.update() end");
			return R.ok();
		}
		logger.info("SvnCreateBranchController.update() end");
		return R.error();
	}

	@RequiresPermissions("svn:svnCreateBranch:remove")
	@Log("删除SVN新建分支信息")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		logger.info("SvnCreateBranchController.remove() start");
		if (svnCreateBranchService.remove(id) > 0) {
			logger.info("SvnCreateBranchController.remove() end");
			return R.ok();
		}
		logger.info("SvnCreateBranchController.remove() end");
		return R.error();
	}

	@RequiresPermissions("svn:svnCreateBranch:batchRemove")
	@Log("批量删除SVN新建分支信息")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		logger.info("SvnCreateBranchController.batchRemove() start");
		int count = svnCreateBranchService.batchRemove(ids);
		if (count > 0) {
			logger.info("SvnCreateBranchController.batchRemove() end");
			return R.ok();
		}
		logger.info("SvnCreateBranchController.batchRemove() end");
		return R.error();
	}
	
	/**
	 * 新建SVN分支add/edit页面打开选择产品页面
	 * @param 
	 * @return String
	 */
	@GetMapping("/showDepts")
	String showDepts() {
		logger.info("SvnCreateBranchController.showDepts() start");
		logger.info("SvnCreateBranchController.showDepts() end");
		return prefix + "/showDepts";
	}
	
	/**
	 * 新建SVN分支add/edit页面打开选择批次页面
	 * @param 
	 * @return String
	 */
	@GetMapping("/showBatch")
	String showBatch() {
		logger.info("SvnCreateBranchController.showBatch() start");
		logger.info("SvnCreateBranchController.showBatch() end");
		return prefix + "/showBatch";
	}
}
