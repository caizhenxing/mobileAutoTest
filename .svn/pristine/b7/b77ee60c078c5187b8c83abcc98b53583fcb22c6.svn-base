package com.bmtc.device.controller;



import java.util.List;

import org.apache.xmlgraphics.util.io.Finalizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.device.domain.Response;
import com.bmtc.device.service.ExecuteDetailService;
import com.bmtc.device.service.TestCaseService;
import com.bmtc.task.domain.ExecuteDetail;

/**
 *@author: Jason.ma
 *@date: 2018年1月9日下午5:18:44
 *
 */
@RequestMapping("/bmtc")
@Controller
public class TestCaseController {
	private static final Logger logger = LoggerFactory.getLogger(TestCaseController.class);
	
	@Autowired
	private TestCaseService testCaseService;
	@Autowired
	private ExecuteDetailService executeDetailService;
	
	@RequestMapping(value="/android/run/case", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> runAndroidTestCase(@RequestBody List<ExecuteDetail> executeDetails){
		
		Response<Object> res = new Response<Object>();
		
		for (ExecuteDetail executeDetail : executeDetails) {
			String token = executeDetail.getToken();
			String udid = executeDetail.getUdid();
			String version = executeDetail.getVersion();
			String testSuitePath = executeDetail.getTestSuitePath();
			
			if (token == "" || token == null) {
				res.setCode("9999");
				res.setData("token不能为空");
				res.setMsg("参数错误");
				logger.debug("返回BMTC 请求参数格式错误 ：{}", res);
				return res;
			}
			
			if (udid == "" || udid == null) {
				res.setCode("9999");
				res.setData("udid不能为空");
				res.setMsg("参数错误");
				logger.debug("返回BMTC 请求参数格式错误 ：{}", res);
				return res;
			}
			
			if (version == "" || version == null) {
				res.setCode("9999");
				res.setData("version不能为空");
				res.setMsg("参数错误");	
				logger.debug("返回BMTC 请求参数格式错误 ：{}", res);
				return res;
			}
			
			if (testSuitePath == "" || testSuitePath == null) {
				res.setCode("9999");
				res.setData("testSuitePath不能为空");
				res.setMsg("参数错误");
				logger.debug("返回BMTC 请求参数格式错误 ：{}", res);
				return res;
			}
		}
		final List<ExecuteDetail>  executeDetailes = executeDetails;
		
		//执行测试套
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (ExecuteDetail executeDetail : executeDetailes) {
					testCaseService.runTestSuiteForAndroid(executeDetail);
				}
			}
		}).start();
		
		res.setCode("0000");
		res.setData("1");
		res.setMsg("操作成功");
		
		logger.debug("返回BMTC android调度结果 ：{}", res);
		
		return res;
	}
	
	@RequestMapping(value="/ios/run/case", method=RequestMethod.POST)
	@ResponseBody
	public Response<Object> runIOSTestCase(@RequestBody ExecuteDetail executeDetail){
		
		Response<Object> res = new Response<Object>();
		String token = executeDetail.getToken();
		String udid = executeDetail.getUdid();
		String version = executeDetail.getVersion();
		String testSuitePath = executeDetail.getTestSuitePath();
		
		if (token == "" || token == null) {
			res.setCode("9999");
			res.setData("token不能为空");
			res.setMsg("参数错误");
		}
		
		if (udid == "" || udid == null) {
			res.setCode("9999");
			res.setData("udid不能为空");
			res.setMsg("参数错误");
		}
		
		if (version == "" || version == null) {
			res.setCode("9999");
			res.setData("version不能为空");
			res.setMsg("参数错误");	
		}
		
		if (testSuitePath == "" || testSuitePath == null) {
			res.setCode("9999");
			res.setData("testSuitePath不能为空");
			res.setMsg("参数错误");
		}
		
		boolean flag = testCaseService.runTestSuiteForIOS(executeDetail);
		
		if (flag) {
			res.setCode("0000");
			res.setData("1");
			res.setMsg("操作成功");
		}else{
			res.setCode("9998");
			res.setData("脚本执行异常");
			res.setMsg("执行异常");

		}
		
		logger.debug("返回BMTC IOS调度结果 ：{}", res);
		return res;
	}
}

