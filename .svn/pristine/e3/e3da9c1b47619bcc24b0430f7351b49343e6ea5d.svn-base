package com.bmtc.device.domain;

import java.io.Serializable;


/**
 *@author: Jason.ma
 *@date: 2018年1月18日上午11:14:01
 *
 */
public class Robot implements Serializable{
	
	private static final long serialVersionUID = 8849744292040280450L;
	//appium服务uri
	private String url;
	//测试设备udid
	private String udid;
	//测试设备版本
	private String verison;
	//Android:uiautomator-server端口
	private int systemPort;
	//IOS: webdriverAgent 端口 
	private int wdaLocalPort;
	//测试用例名称
	private String caseName;
	//测试套路径
	private String testSuite;
	//rf log路径
	private String log;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String host, int port) {
		this.url = "http://" + host + ":" + port + "/wd/hub";
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getVerison() {
		return verison;
	}
	public void setVerison(String verison) {
		this.verison = verison;
	}
	public int getSystemPort() {
		return systemPort;
	}
	public void setSystemPort(int systemPort) {
		this.systemPort = systemPort;
	}

	public int getWdaLocalPort() {
		return wdaLocalPort;
	}
	public void setWdaLocalPort(int wdaLocalPort) {
		this.wdaLocalPort = wdaLocalPort;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(String testSuite) {
		this.testSuite = testSuite;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	@Override
	public String toString() {
		return "Robot [url=" + url + ", udid=" + udid + ", verison=" + verison
				+ ", systemPort=" + systemPort + ", wdaLocalPort="
				+ wdaLocalPort + ", caseName=" + caseName + ", testSuite="
				+ testSuite + ", log=" + log + "]";
	}
	
}

