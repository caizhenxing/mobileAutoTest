package com.bmtc.system.controller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.config.Constant;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.service.DeptService;

/**
 * 部门管理
 * 
 * @author nienannan
 * 
 */

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(DeptController.class);

	private String prefix = "system/dept";

	@Autowired
	private DeptService sysDeptService;

	@GetMapping("")
	@Log("跳转到产品机构界面")
	@RequiresPermissions("system:sysDept:sysDept")
	String dept() {
		logger.info("DeptController.dept() start");
		logger.info("DeptController.dept() end");
		return prefix + "/dept";
	}

	@GetMapping("/list")
	@Log("查询产品机构数据")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("DeptController.list() start");
		params.put("parentId", params.get("deptId"));
		params.remove("deptId");
		Query query = new Query(params);
		List<DeptDO> sysDeptList = sysDeptService.list(query);
		int total = sysDeptService.count(query);
		PageUtils pageUtil = new PageUtils(sysDeptList, total);
		logger.info("DeptController.list() end");
		return pageUtil;
	}

	@GetMapping("/edit/{deptId}")
	@Log("编辑产品")
	@RequiresPermissions("system:sysDept:edit")
	String edit(@PathVariable("deptId") Long deptId, Model model) {
		logger.info("DeptController.edit() start");
		DeptDO sysDept = sysDeptService.get(deptId);
		model.addAttribute("sysDept", sysDept);
		if (Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
			model.addAttribute("parentDeptName", "无");
		} else {
			DeptDO parDept = sysDeptService.get(sysDept.getParentId());
			model.addAttribute("parentDeptName", parDept.getName());
		}
		logger.info("DeptController.edit() end");
		return prefix + "/edit";
	}

	@ResponseBody
	@Log("保存产品数据")
	@PostMapping("/save")
	@RequiresPermissions("system:sysDept:add")
	 R save() {
		logger.info("DeptController.save() start");
		try {
			sysDeptService.save();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.info("urllink为空或格式不正确");
			return R.error("服务器异常,请联系管理员");
		}
		logger.info("DeptController.save() end");
		return R.ok();
	}

	@ResponseBody
	@Log("修改产品数据")
	@RequestMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public R update(DeptDO sysDept) {
		logger.info("DeptController.update() start");
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (sysDeptService.update(sysDept) > 0) {
			logger.info("DeptController.update() end");
			return R.ok();
		}
		logger.info("DeptController.update() end");
		return R.error();
	}

	@GetMapping("/trees")
	@Log("获取产品机构树形图数据")
	@ResponseBody
	public Tree<DeptDO> trees() {
		logger.info("DeptController.trees() start");
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = sysDeptService.getTrees();
		logger.info("DeptController.trees() end");
		return tree;
	}

	@GetMapping("/tree")
	@Log("获取产品机构树形图数据")
	@ResponseBody
	public Tree<DeptDO> tree() {
		logger.info("DeptController.tree() start");
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = sysDeptService.getTree();
		logger.info("DeptController.tree() end");
		return tree;
	}

	
	 @GetMapping("/treeView") 
	 @Log("注册时访问产品机构树形图")
	 String treeView() { 
		 return prefix + "/deptTree";
	  }
	 

}
