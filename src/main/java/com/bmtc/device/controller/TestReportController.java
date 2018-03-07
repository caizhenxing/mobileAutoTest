package com.bmtc.device.controller;



import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bmtc.device.domain.Response;
import com.bmtc.device.domain.TestReport;
import com.bmtc.device.service.ExecuteDetailService;
import com.bmtc.task.domain.ExecuteDetail;


/**
 *@author: Jason.ma
 *@date: 2018年1月24日上午9:22:27
 *
 */
@RequestMapping("/bmtc")
@RestController
public class TestReportController {
	
	@Autowired
	private ExecuteDetailService executeDetailService;
	
	@RequestMapping(value="/test/report", method=RequestMethod.GET)
	@ResponseBody
	public Response<TestReport> getTestReport(@RequestParam String token, @RequestParam String udid, @RequestParam String testSuitePath){
		Response<TestReport> response = new Response<TestReport>();
		ExecuteDetail executeDetail = executeDetailService.getExecuteResult(token, udid, testSuitePath);
		TestReport testReport = new TestReport();
		testReport.setToken(executeDetail.getToken());
		testReport.setTestSuitePath(executeDetail.getTestSuitePath());
		testReport.setDeviceType(executeDetail.getDeviceType());
		testReport.setUdid(executeDetail.getUdid());
		testReport.setTestReportPath(executeDetail.getTestReportPath());
		testReport.setAppiumLogPath(executeDetail.getAppiumLogPath());
		testReport.setRfLogPath(executeDetail.getRfLogPath());
		testReport.setGmtCreate(executeDetail.getGmtCreate());
		testReport.setGmtModified(executeDetail.getGmtModified());
		
		response.setCode("0000");
		response.setData(testReport);
		response.setMsg("操作成功");
		
		return response;
	}
	
	@RequestMapping(value="/test/status", method=RequestMethod.GET)
	@ResponseBody
	public Response<List<String>> getTestStatus(@RequestParam String token) throws IOException{
		Response<List<String>> response = new Response<List<String>>();
		
		List<String> allStatus= executeDetailService.getAllExecuteStatus(token);
		
		response.setCode("0000");
		response.setData(allStatus);
		response.setMsg("操作成功");
		
		return response;
	}
}

