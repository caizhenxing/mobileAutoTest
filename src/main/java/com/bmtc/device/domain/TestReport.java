package com.bmtc.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: Jason.ma
 * @date: 2018年1月3日下午4:51:31
 *
 */
public class TestReport implements Serializable{
	private static final long serialVersionUID = 4392932830326739650L;
	
	//执行测试token
	private String token;
	//测试设备udid
	private String udid;
	//测试设备类型
	private String deviceType;
	// 测试套路径
	private String testSuitePath;
	//测试报告路径
	private String  testReportPath;
	//appium日志路径
	private String appiumLogPath;
	//rf日志路径
	private String rfLogPath;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtCreate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getTestReportPath() {
		return testReportPath;
	}
	public void setTestReportPath(String testReportPath) {
		this.testReportPath = testReportPath;
	}
	public String getAppiumLogPath() {
		return appiumLogPath;
	}
	
	public String getTestSuitePath() {
		return testSuitePath;
	}
	public void setTestSuitePath(String testSuitePath) {
		this.testSuitePath = testSuitePath;
	}
	public void setAppiumLogPath(String appiumLogPath) {
		this.appiumLogPath = appiumLogPath;
	}
	public String getRfLogPath() {
		return rfLogPath;
	}
	public void setRfLogPath(String rfLogPath) {
		this.rfLogPath = rfLogPath;
	}
	
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	@Override
	public String toString() {
		return "TestReport [token=" + token + ", udid=" + udid
				+ ", deviceType=" + deviceType + ", testReportPath="
				+ testReportPath + ", appiumLogPath=" + appiumLogPath
				+ ", rfLogPath=" + rfLogPath + "]";
	}
}
