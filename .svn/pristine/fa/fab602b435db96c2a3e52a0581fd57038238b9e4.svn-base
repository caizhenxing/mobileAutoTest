package com.bmtc.script.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.controller.BaseController;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.script.domain.Script;
import com.bmtc.script.service.ScriptService;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.service.DeptService;

/**
 * 脚本管理的controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/script")
public class ScriptController extends BaseController{

	private static Logger logger = Logger.getLogger(ScriptController.class);
	// 返回映射路径的前缀
	private String prefix = "script";
	@Autowired
	ScriptService scriptService;
	@Autowired
	DeptService deptService;
	
	/**
	 * 前端请求访问脚本列表页面
	 * @return
	 */
	@RequiresPermissions("test:script:script")
	@GetMapping()
	String script() {
		logger.info("ScriptController.script() start");
		logger.info("ScriptController.script() end");
		return prefix + "/script";
	}
	/**
	 * 查询脚本列表数据
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	@RequiresPermissions("test:script:script")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("ScriptController.list() start");
		// 分页查询参数数据
		Query query = new Query(params);
		// 查询数据库场景数据
		List<Script> scripts = scriptService.list(query);
		// 查询总记录数
		int total = scriptService.count(query);
		// 封装到返回对象
		PageUtils pageUtil = new PageUtils(scripts, total);
		logger.info("ScriptController.list() end");
		// 返回数据
		return pageUtil;
	}
	/**
	 * 同步更新脚本列表信息
	 * @param params
	 * @return
	 */
	@PostMapping("/updateScripts")
	@RequiresPermissions("test:script:script")
	@ResponseBody
	R updateScripts(DeptDO DeptDO) {
		logger.info("ScriptController.updateScripts() start");
		// 更新本地及数据库脚本信息
		if(!scriptService.updateScriptData(DeptDO)){
			logger.error("脚本信息同步数据库异常");
			return R.error("更新失败!");
		}
		logger.info("ScriptController.updateScripts() end");
		// 更新成功
		return R.ok("更新成功!");
	}
	/**
	 * 前端测试任务列表   关联脚本   列表展示
	 * @return
	 */
	@GetMapping("/showScript/{id}")
	String showScript(Model model, @PathVariable("id") Long id) {
		logger.info("ScriptController.showScript() start");
		model.addAttribute("id", id);
		logger.info("ScriptController.showScript() end");
		return prefix+"/showScript";
	}
	/**
	 * 查询执行计划关联脚本信息(树形结构)
	 * @return
	 */
	@GetMapping("/getTreeData/{id}")
	@ResponseBody
	public Tree<Script> getTreeData(@PathVariable("id") Long id) {
		logger.info("ScriptController.getTreeData() start");
		Tree<Script> tree = new Tree<Script>();
		tree = scriptService.getTaskScriptTreeData(id);
		logger.info("ScriptController.getTreeData() end");
		return tree;
	}
	/**
	 * 前端请求访问案例列表页面
	 * @return
	 */
	@GetMapping("/showCaseName/{scriptId}")
	String showCaseName(Model model, @PathVariable("scriptId") Long scriptId) {
		logger.info("ScriptController.showCaseName() start");
		model.addAttribute("scriptId", scriptId);
		logger.info("ScriptController.showCaseName() end");
		return prefix+"/showCaseName";
	}
	/**
	 * 查询脚本关联的casename(树形结构)
	 * @return
	 */
	@GetMapping("/getCaseNameTree/{scriptId}")
	@ResponseBody
	public Tree<String> getScriptTree(@PathVariable("scriptId") Long scriptId) {
		logger.info("ScriptController.getScriptTree() start");
		Tree<String> tree = new Tree<String>();
		tree = scriptService.getTree(scriptId);
		logger.info("ScriptController.getScriptTree() end");
		return tree;
	}
	/**
	 * 测试任务修改页面打开关联脚本页面
	 * @return
	 */
	@GetMapping("/scripts")
	String scripts() {
		logger.info("ScriptController.scripts() start");
		logger.info("ScriptController.scripts() end");
		return prefix + "/scriptTree";
	}
	/**
	 * 测试任务修改页面查询关联场景数据(树形结构) 
	 * @return
	 */
	@GetMapping("/getScriptTree")
	@ResponseBody
	public Tree<Script> getScriptTree() {
		logger.info("ScriptController.getScriptTree() start");
		Tree<Script> tree = new Tree<Script>();
		tree = scriptService.getTree();
		logger.info("ScriptController.getScriptTree() end");
		return tree;
	}
	/**
	 * 脚本更新时，选择要更新脚本的产品及批次页面
	 * @return
	 */
	@GetMapping("/openUpdatePage")
	String openUpdatePage() {
		logger.info("ScriptController.scripts() start");
		logger.info("ScriptController.scripts() end");
		return prefix + "/updatePage";
	}
}
