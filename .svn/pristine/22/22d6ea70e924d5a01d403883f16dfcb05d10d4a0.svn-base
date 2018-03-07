package com.bmtc.task.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 执行明细实体类
 * @author Administrator
 *
 */
public class ExecuteDetail implements Serializable{

	/**
	 * 属性
	 */
	private static final long serialVersionUID = 1L;
	// 主键执行token
	private String token;
	//产品信息
	private ProductSvn productSvn;
	// 测试套路径
	private String testSuitePath;
	// 任务id
	private Long taskId;
	// 测试套执行状态
	private String status;
	// 测试案例名称
	private String caseName;
	// 设备类型:android;IOS
	private String deviceType;
	// 设备版本号
	private String version;
	// 设备唯一标示udid
	private String udid;
	//测试报告路径
	private String  testReportPath;
	//appium日志路径
	private String appiumLogPath;
	//rf日志路径
	private String rfLogPath;
	// 执行计划id
	private Long executePlanId;
	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtCreate;
	// 修改时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;

	/**
	 * 构造
	 */
	public ExecuteDetail() {
		super();
	}
	/**
	 * set&get
	 */

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ProductSvn getProductSvn() {
		return productSvn;
	}

	public void setProductSvn(ProductSvn productSvn) {
		this.productSvn = productSvn;
	}

	public String getTestSuitePath() {
		return testSuitePath;
	}

	public void setTestSuitePath(String testSuitePath) {
		this.testSuitePath = testSuitePath;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
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

	public void setAppiumLogPath(String appiumLogPath) {
		this.appiumLogPath = appiumLogPath;
	}

	public String getRfLogPath() {
		return rfLogPath;
	}

	public void setRfLogPath(String rfLogPath) {
		this.rfLogPath = rfLogPath;
	}

	public Long getExecutePlanId() {
		return executePlanId;
	}

	public void setExecutePlanId(Long executePlanId) {
		this.executePlanId = executePlanId;
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
		return "ExecuteDetail [token=" + token + ", productSvn=" + productSvn
				+ ", testSuitePath=" + testSuitePath + ", taskId=" + taskId
				+ ", status=" + status + ", caseName=" + caseName
				+ ", deviceType=" + deviceType + ", version=" + version
				+ ", udid=" + udid + ", testReportPath=" + testReportPath
				+ ", appiumLogPath=" + appiumLogPath + ", rfLogPath="
				+ rfLogPath + ", executePlanId=" + executePlanId
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ "]";
	}
}
