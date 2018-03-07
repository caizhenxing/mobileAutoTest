package com.bmtc.svn.domain;

import java.sql.Timestamp;

import com.bmtc.boc.domain.BocBaseDO;


/**
 * SVN用户信息类
 * @author lpf7161
 *
 */
public class SvnUser extends BocBaseDO {
	
	/**
	 * svn用户id
	 */
	private long id;
	
	/**
	 * svn用户名
	 */
	private String svnUserName;
	
	/**
	 * svn密码
	 */
	private String svnPassword;
	
	/**
	 * svn库id
	 */
	private long svnRepoId;
	
	/**
	 * svn库名
	 */
	private String svnRepoName;
	
	/**
	 * 审批状态
	 * 0:待审批，1:已审批
	 */
	private long status;

	/**
	 * svn用户创建时间
	 */
	private Timestamp svnUserCreateDate = SvnUser.getNowTimestamp();
	
	/**
	 * svn用户修改时间
	 */
	private Timestamp svnUserModifyDate = SvnUser.getNowTimestamp();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getSvnRepoId() {
		return svnRepoId;
	}

	public void setSvnRepoId(long svnRepoId) {
		this.svnRepoId = svnRepoId;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Timestamp getSvnUserCreateDate() {
		return svnUserCreateDate;
	}

	public void setSvnUserCreateDate(Timestamp svnUserCreateDate) {
		this.svnUserCreateDate = SvnUser.getNowTimestamp();
	}

	public Timestamp getSvnUserModifyDate() {
		return svnUserModifyDate;
	}

	public void setSvnUserModifyDate(Timestamp svnUserModifyDate) {
		this.svnUserModifyDate = SvnUser.getNowTimestamp();
	}

	public String getSvnRepoName() {
		return svnRepoName;
	}

	public void setSvnRepoName(String svnRepoName) {
		this.svnRepoName = svnRepoName;
	}

	@Override
	public String toString() {
		return "SvnUser [id=" + id + ", svnUserName=" + svnUserName
				+ ", svnPassword=" + svnPassword + ", svnRepoId=" + svnRepoId
				+ ", svnRepoName=" + svnRepoName + ", status=" + status
				+ ", svnUserCreateDate=" + svnUserCreateDate
				+ ", svnUserModifyDate=" + svnUserModifyDate + "]";
	}

	public SvnUser(long id, String svnUserName, String svnPassword,
			long svnRepoId, String svnRepoName, long status,
			Timestamp svnUserCreateDate, Timestamp svnUserModifyDate) {
		super();
		this.id = id;
		this.svnUserName = svnUserName;
		this.svnPassword = svnPassword;
		this.svnRepoId = svnRepoId;
		this.svnRepoName = svnRepoName;
		this.status = status;
		this.svnUserCreateDate = svnUserCreateDate;
		this.svnUserModifyDate = svnUserModifyDate;
	}

	public SvnUser() {
		super();
	}

	
}
