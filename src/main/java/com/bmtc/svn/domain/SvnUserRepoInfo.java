package com.bmtc.svn.domain;

import java.sql.Timestamp;

import com.bmtc.boc.domain.BocBaseDO;


/**
 * SVN用户信息类
 * @author lpf7161
 *
 */
public class SvnUserRepoInfo extends BocBaseDO {
	
	/**
	 * svn仓库id
	 */
	private long svnRepoId;
	
	/**
	 * svn库名
	 */
	private String svnRepoName;
	
	/**
	 * svn仓库路径
	 */
	private String svnRepoPath;
	
	/**
	 * svn仓库url
	 */
	private String svnRepoUrl;
	
	/**
	 * svn仓库描述
	 */
	private String svnRepoDes;
	
	/**
	 * svn用户id
	 */
	private long svnUserId;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * svn用户名
	 */
	private String svnUserName;
	
	/**
	 * svn密码
	 */
	private String svnPassword;
	
	/**
	 * 审批状态
	 * 0:待审批，1:已审批
	 */
	private long userStatus;

	/**
	 * svn用户创建时间
	 */
	private Timestamp svnUserCreateDate;
	
	/**
	 * svn用户修改时间
	 */
	private Timestamp svnUserModifyDate;

	public long getSvnRepoId() {
		return svnRepoId;
	}

	public void setSvnRepoId(long svnRepoId) {
		this.svnRepoId = svnRepoId;
	}

	public String getSvnRepoName() {
		return svnRepoName;
	}

	public void setSvnRepoName(String svnRepoName) {
		this.svnRepoName = svnRepoName;
	}

	public String getSvnRepoPath() {
		return svnRepoPath;
	}

	public void setSvnRepoPath(String svnRepoPath) {
		this.svnRepoPath = svnRepoPath;
	}

	public String getSvnRepoUrl() {
		return svnRepoUrl;
	}

	public void setSvnRepoUrl(String svnRepoUrl) {
		this.svnRepoUrl = svnRepoUrl;
	}

	public String getSvnRepoDes() {
		return svnRepoDes;
	}

	public void setSvnRepoDes(String svnRepoDes) {
		this.svnRepoDes = svnRepoDes;
	}

	public long getSvnUserId() {
		return svnUserId;
	}

	public void setSvnUserId(long svnUserId) {
		this.svnUserId = svnUserId;
	}

	public String getSvnUserName() {
		return svnUserName;
	}

	public void setSvnUserName(String svnUserName) {
		this.svnUserName = svnUserName;
	}

	public String getSvnPassword() {
		return svnPassword;
	}

	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}

	public long getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(long userStatus) {
		this.userStatus = userStatus;
	}

	public Timestamp getSvnUserCreateDate() {
		return svnUserCreateDate;
	}

	public void setSvnUserCreateDate(Timestamp svnUserCreateDate) {
		this.svnUserCreateDate = svnUserCreateDate;
	}

	public Timestamp getSvnUserModifyDate() {
		return svnUserModifyDate;
	}

	public void setSvnUserModifyDate(Timestamp svnUserModifyDate) {
		this.svnUserModifyDate = svnUserModifyDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public String toString() {
		return "SvnUserRepoInfo [svnRepoId=" + svnRepoId + ", svnRepoName="
				+ svnRepoName + ", svnRepoPath=" + svnRepoPath
				+ ", svnRepoUrl=" + svnRepoUrl + ", svnRepoDes=" + svnRepoDes
				+ ", svnUserId=" + svnUserId + ", name=" + name
				+ ", svnUserName=" + svnUserName + ", svnPassword="
				+ svnPassword + ", userStatus=" + userStatus
				+ ", svnUserCreateDate=" + svnUserCreateDate
				+ ", svnUserModifyDate=" + svnUserModifyDate + "]";
	}

	
	public SvnUserRepoInfo(long svnRepoId, String svnRepoName,
			String svnRepoPath, String svnRepoUrl, String svnRepoDes,
			long svnUserId, String name, String svnUserName,
			String svnPassword, long userStatus, Timestamp svnUserCreateDate,
			Timestamp svnUserModifyDate) {
		super();
		this.svnRepoId = svnRepoId;
		this.svnRepoName = svnRepoName;
		this.svnRepoPath = svnRepoPath;
		this.svnRepoUrl = svnRepoUrl;
		this.svnRepoDes = svnRepoDes;
		this.svnUserId = svnUserId;
		this.name = name;
		this.svnUserName = svnUserName;
		this.svnPassword = svnPassword;
		this.userStatus = userStatus;
		this.svnUserCreateDate = svnUserCreateDate;
		this.svnUserModifyDate = svnUserModifyDate;
	}

	public SvnUserRepoInfo() {
		super();
	}

	
}
