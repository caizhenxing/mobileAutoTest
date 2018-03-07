package com.bmtc.task.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmtc.common.controller.BaseController;
import com.bmtc.task.service.BMTCExecuteDetailService;

/**
 * 执行明细的controller类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/executeDetail")
public class BMTCExecuteDetailController extends BaseController{

	private static Logger logger = Logger.getLogger(BMTCTaskController.class);
	// 返回映射路径的前缀
	private String prefix = "executeDetail";
	@Autowired
	BMTCExecuteDetailService executeDetailService;
	
	/**
	 * 前段请求访问测试任务列表页面
	 * @return String
	 */
	@RequiresPermissions("test:task:executeDetail")
	@GetMapping()
	String executeDetail() {
		logger.info("ExecuteDetailController.executeDetail() start");
		logger.info("ExecuteDetailController.executeDetail() end");
		return prefix + "/executeDetail";
	}
	
}
