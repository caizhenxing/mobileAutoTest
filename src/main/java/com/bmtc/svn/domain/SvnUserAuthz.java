package com.bmtc.svn.domain;

import java.sql.Timestamp;

import com.bmtc.boc.domain.BocBaseDO;

/**
 * SVN用户权限信息类
 * @author lpf7161
 *
 */
public class SvnUserAuthz extends BocBaseDO {
	
	/**
	 * svn用户权限id
	 */
	private long id;
	
	/**
	 * svn用户权限
	 */
	private String svnUserAuthz; 
	
	/**
	 * svn路径
	 */
	private String svnPath;
	
	/**
	 * svn用户id
	 */
	private long svnUserId;
	
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
	 * svn用户权限创建时间
	 */
	private Timestamp svnUserAuthzCreateDate = SvnUserAuthz.getNowTimestamp();
	
	/**
	 * svn用户权限修改时间
	 */
	private Timestamp svnUserAuthzModifyDate = SvnUserAuthz.getNowTimestamp();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSvnUserAuthz() {
		return svnUserAuthz;
	}

	public void setSvnUserAuthz(String svnUserAuthz) {
		this.svnUserAuthz = svnUserAuthz;
	}

	public String getSvnPath() {
		return svnPath;
	}

	public void setSvnPath(String svnPath) {
		this.svnPath = svnPath;
	}

	public long getSvnUserId() {
		return svnUserId;
	}

	public void setSvnUserId(long svnUserId) {
		this.svnUserId = svnUserId;
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

	public Timestamp getSvnUserAuthzCreateDate() {
		return svnUserAuthzCreateDate;
	}

	public void setSvnUserAuthzCreateDate(Timestamp svnUserAuthzCreateDate) {
		this.svnUserAuthzCreateDate = SvnUserAuthz.getNowTimestamp();
	}

	public Timestamp getSvnUserAuthzModifyDate() {
		return svnUserAuthzModifyDate;
	}

	public void setSvnUserAuthzModifyDate(Timestamp svnUserAuthzModifyDate) {
		this.svnUserAuthzModifyDate = SvnUserAuthz.getNowTimestamp();
	}

	public String getSvnRepoName() {
		return svnRepoName;
	}

	public void setSvnRepoName(String svnRepoName) {
		this.svnRepoName = svnRepoName;
	}

	
	@Override
	public String toString() {
		return "SvnUserAuthz [id=" + id + ", svnUserAuthz=" + svnUserAuthz
				+ ", svnPath=" + svnPath + ", svnUserId=" + svnUserId
				+ ", svnRepoId=" + svnRepoId + ", svnRepoName=" + svnRepoName
				+ ", status=" + status + ", svnUserAuthzCreateDate="
				+ svnUserAuthzCreateDate + ", svnUserAuthzModifyDate="
				+ svnUserAuthzModifyDate + "]";
	}

	public SvnUserAuthz(long id, String svnUserAuthz, String svnPath,
			long svnUserId, long svnRepoId, String svnRepoName, long status,
			Timestamp svnUserAuthzCreateDate, Timestamp svnUserAuthzModifyDate) {
		super();
		this.id = id;
		this.svnUserAuthz = svnUserAuthz;
		this.svnPath = svnPath;
		this.svnUserId = svnUserId;
		this.svnRepoId = svnRepoId;
		this.svnRepoName = svnRepoName;
		this.status = status;
		this.svnUserAuthzCreateDate = svnUserAuthzCreateDate;
		this.svnUserAuthzModifyDate = svnUserAuthzModifyDate;
	}

	public SvnUserAuthz() {
		super();
	}

}
