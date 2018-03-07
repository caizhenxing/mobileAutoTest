package com.bmtc.svn.controller;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.svn.domain.SvnConfDiffInfo;
import com.bmtc.svn.service.SvnConfDiffService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/svn/svnConfDiff")
@Controller
public class SvnConfDiffController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SvnConfDiffController.class);
	
	private String prefix = "svn/svnConfDiff";
	
	@Autowired
	SvnConfDiffService svnConfDiffService;
	
	@RequiresPermissions("svn:svnConfDiff:svnConfDiff")
	@Log("跳转到svn配置文件差异信息界面")
	@GetMapping("")
	String svnConfDiff() {
		return prefix + "/svnConfDiff";
	}
 
	@GetMapping("/list")
	@Log("查询列表数据")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("SvnConfDiffController.list() start");
		Query query = new Query(params);
		List<SvnConfDiffInfo> svnConfDiffInfoList = svnConfDiffService.list(query);
		int total = svnConfDiffService.count(query);
		PageUtils pageUtil = new PageUtils(svnConfDiffInfoList, total);
		logger.info("SvnConfDiffController.list() end");
		return pageUtil;
	}

	@RequiresPermissions("svn:svnConfDiff:add")
	@Log("保存svn配置文件差异信息")
	@PostMapping("/save")
	@ResponseBody
	R save() {
		logger.info("ConfigController.save() start");
		int res = 0;
		try {
			res = svnConfDiffService.save();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		if (res > 0) {
			logger.info("SvnConfDiffController.save() end");
			return R.ok();
		}
		logger.info("SvnConfDiffController.save() end");
		return R.error();
	}

	/*	@RequiresPermissions("svn:svnConfDiff:edit")
	@Log("编辑svn配置文件差异信息")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		logger.info("SvnConfDiffController.edit() start");
		SvnConfDiffInfo svnConfDiffInfo = svnConfDiffService.get(id);
		model.addAttribute("config", svnConfDiffInfo);
		logger.info("SvnConfDiffController.edit() end");
		return prefix + "/edit";
	}
	
	@RequiresPermissions("svn:svnConfDiff:edit")
	@Log("更新svn配置文件差异信息")
	@PostMapping("/update")
	@ResponseBody
	R update(SvnConfDiff svnConfDiff) {
		logger.info("SvnConfDiffController.update() start");
		if (svnConfDiffService.update(svnConfDiff) > 0) {
			logger.info("ConfigController.update() end");
			return R.ok();
		}
		logger.info("SvnConfDiffController.update() end");
		return R.error();
	}*/

	@RequiresPermissions("svn:svnConfDiff:remove")
	@Log("删除svn配置文件差异信息")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		logger.info("SvnConfDiffController.remove() start");
		if (svnConfDiffService.remove(id) > 0) {
			logger.info("SvnConfDiffController.remove() end");
			return R.ok();
		}
		logger.info("SvnConfDiffController.remove() end");
		return R.error();
	}

	@RequiresPermissions("svn:svnConfDiff:batchRemove")
	@Log("批量删除svn配置文件差异信息")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		logger.info("SvnConfDiffController.batchRemove() start");
		int count = svnConfDiffService.batchRemove(ids);
		if (count > 0) {
			logger.info("SvnConfDiffController.batchRemove() end");
			return R.ok();
		}
		logger.info("SvnConfDiffController.batchRemove() end");
		return R.error();
	}

}
