package com.bmtc.task.controller;

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

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.task.domain.ExecutePlan;
import com.bmtc.task.service.ExecutePlanService;

/**
 * 测试任务执行计划的Controller类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/executePlan")
public class ExecutePlanController extends BaseController{

	private static Logger logger = Logger.getLogger(ExecutePlanController.class);
	// 返回映射路径的前缀
	private String prefix = "executePlan";
	@Autowired
	ExecutePlanService executePlanService;
	
	/**
	 * 前段请求访问执行计划列表页面
	 * @param params
	 * @return String
	 */
	@RequiresPermissions("test:executePlan:executePlan")
	@GetMapping()
	String executePlan() {
		logger.info("ExecutePlanController.executePlan() start");
		logger.info("ExecutePlanController.executePlan() end");
		return prefix + "/executePlan";
	}
	/**
	 * 查询执行计划列表数据
	 * @param params
	 * @return PageUtils
	 */
	@GetMapping("/list")
	@RequiresPermissions("test:executePlan:list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("ExecutePlanController.list() start");
		// 分页查询参数数据
		Query query = new Query(params);
		// 查询数据库执行计划数据
		List<ExecutePlan> executePlans = executePlanService.list(query);
		// 查询总记录数
		int total = executePlanService.count(query);
		// 封装到返回对象
		PageUtils pageUtil = new PageUtils(executePlans, total);
		
		logger.info("ExecutePlanController.list() end");
		
		// 返回数据
		return pageUtil;
	}
	/**
	 * 执行计划执行测试任务
	 * @param params
	 * @return String
	 */
	@RequiresPermissions("test:executePlan:executePlan")
	@PostMapping("/active")
	@ResponseBody
	String active(Long id) {
		logger.info("ExecutePlanController.active() start");
		String result = executePlanService.active(id);
		logger.info("ExecutePlanController.active() end");
		return result;
	}
	/**
	 * 前段请求访问执行计划添加页面
	 * @param 
	 * @return String
	 */
	@RequiresPermissions("test:executePlan:add")
	@GetMapping("/add")
	String add() {
		logger.info("ExecutePlanController.add() start");
		logger.info("ExecutePlanController.add() end");
		return prefix + "/add";
	}
	/**
	 * 保存执行计划
	 * @param ExecutePlan executePlan
	 * @return R
	 */
	@RequiresPermissions("test:executePlan:add")
	@Log("保存执行计划")
	@PostMapping("/save")
	@ResponseBody
	R save(ExecutePlan executePlan) {
		logger.info("ExecutePlanController.save() start");
		if (executePlanService.save(executePlan) > 0) {
			logger.info("ExecutePlanController.save() end");
			return R.ok();
		}
		logger.info("ExecutePlanController.save() end");
		return R.error();
	}
	/**
	 * 执行计划edit页面跳转
	 * @param Long id
	 * @return String
	 */
	@RequiresPermissions("test:executePlan:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		logger.info("ExecutePlanController.edit() start");
		ExecutePlan executePlan = executePlanService.get(id);
		model.addAttribute("executePlan", executePlan);
		logger.info("ExecutePlanController.edit() end");
		return prefix+"/edit";
	}
	/**
	 * 执行计划修改
	 * @param ExecutePlan executePlan
	 * @return R	
	 */
	@RequiresPermissions("test:executePlan:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(ExecutePlan executePlan) {
		logger.info("ExecutePlanController.update() start");
		if (executePlanService.update(executePlan) > 0) {
			logger.info("ExecutePlanController.update() end");
			return R.ok();
		}
		logger.info("ExecutePlanController.update() end");
		return R.error();
	}
	/**
	 * 执行计划删除
	 * @param id
	 * @return R
	 */
	@RequiresPermissions("test:executePlan:remove")
	@Log("删除执行计划")
	@PostMapping("/remove")
	@ResponseBody
	R remove(@RequestParam("id") Long id) {
		logger.info("ExecutePlanController.remove() start");
		int r = executePlanService.remove(id);
		if (r > 0) {
			logger.info("ExecutePlanController.remove() end");
			return R.ok();
		}
		logger.error("ExecutePlanController.remove() end");
		return R.error();
	}
	/**
	 * 执行计划批量删除
	 * @param ids
	 * @return R
	 */
	@RequiresPermissions("test:executePlan:batchRemove")
	@Log("批量删除执行计划")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		logger.info("BMTCTaskController.batchRemove() start");
		int r = executePlanService.batchremove(ids);
		if (r > 0) {
			logger.info("BMTCTaskController.batchRemove() end");
			return R.ok();
		}
		logger.info("BMTCTaskController.batchRemove() end");
		return R.error();
	}
}