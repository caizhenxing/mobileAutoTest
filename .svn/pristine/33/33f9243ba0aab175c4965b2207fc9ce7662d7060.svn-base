package com.bmtc.task.domain;

import java.io.Serializable;

/**
 * 发送执行计划的包装类
 * @author Administrator
 *
 */
public class ExecutePlanVo extends ExecutePlan implements Serializable{

	/**
	 * 属性
	 */
	private static final long serialVersionUID = 1L;
	// svn相关信息
	private ProductSvn productSvn;
	// 批次id
	private Integer batchID;
	// 操作人ID
	private Integer userID;
	// 脚本的SVN路径
	private String scriptSVNPath;
	// 关联的caseName
	private String caseName;
	/**
	 * 构造
	 */
	public ExecutePlanVo() {
		super();
	}
	public ExecutePlanVo(ProductSvn productSvn, Integer batchID,
			Integer userID, String scriptSVNPath, String caseName) {
		super();
		this.productSvn = productSvn;
		this.batchID = batchID;
		this.userID = userID;
		this.scriptSVNPath = scriptSVNPath;
		this.caseName = caseName;
	}
	
	/**
	 * set&get
	 */
	public ProductSvn getProductSvn() {
		return productSvn;
	}
	public void setProductSvn(ProductSvn productSvn) {
		this.productSvn = productSvn;
	}
	public Integer getBatchID() {
		return batchID;
	}
	public void setBatchID(Integer batchID) {
		this.batchID = batchID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getScriptSVNPath() {
		return scriptSVNPath;
	}
	public void setScriptSVNPath(String scriptSVNPath) {
		this.scriptSVNPath = scriptSVNPath;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "ExecutePlanVo [productSvn=" + productSvn + ", batchID="
				+ batchID + ", userID=" + userID + ", scriptSVNPath="
				+ scriptSVNPath + ", caseName=" + caseName + "]";
	}
}
