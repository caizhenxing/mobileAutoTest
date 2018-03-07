package com.bmtc.svn.domain;

import java.sql.Timestamp;

import com.bmtc.boc.domain.BocBaseDO;


/**
 * 根据SVN用户和权限数据库生成的的passwd、authz、svnserver.conf三个配置文件与SVN服务器上的配置文件的差异
 * @author lpf7161
 *
 */
public class SvnConfDiff extends BocBaseDO {
	
	/**
	 * svn配置文件差异表的主键id
	 */
	private long id;
	
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
	 * 根据数据库生成的passwd配置文件与svn服务器的passwd文件是否相同：相同； 不相同
	 */
	private String passwdStatus;
	
	/**
	 * 根据数据库生成的passwd配置文件与svn服务器的passwd文件的内容差异
	 */
	private String passwdContentDiff;
	
	/**
	 * 根据数据库生成的authz配置文件与svn服务器的authz文件是否相同：相同；不相同
	 */
	private String authzStatus;
	
	/**
	 * 根据数据库生成的authz配置文件与svn服务器的authz文件的内容差异
	 */
	private String authzContentDiff;

	/**
	 * 根据数据库生成的svnserver.conf配置文件与svn服务器的svnserver.conf文件是否相同：相同；不相同
	 */
	private String svnserverStatus;
	
	/**
	 * 根据数据库生成的svnserver.conf配置文件与svn服务器的svnserver.conf文件的内容差异
	 */
	private String svnserverContentDiff;
	
    // 创建时间
    private Timestamp createDate = SvnConfDiff.getNowTimestamp();
    
    // 修改时间
    private Timestamp modifyDate = SvnConfDiff.getNowTimestamp();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getPasswdStatus() {
		return passwdStatus;
	}

	public void setPasswdStatus(String passwdStatus) {
		this.passwdStatus = passwdStatus;
	}

	public String getPasswdContentDiff() {
		return passwdContentDiff;
	}

	public void setPasswdContentDiff(String passwdContentDiff) {
		this.passwdContentDiff = passwdContentDiff;
	}

	public String getAuthzStatus() {
		return authzStatus;
	}

	public void setAuthzStatus(String authzStatus) {
		this.authzStatus = authzStatus;
	}

	public String getAuthzContentDiff() {
		return authzContentDiff;
	}

	public void setAuthzContentDiff(String authzContentDiff) {
		this.authzContentDiff = authzContentDiff;
	}

	public String getSvnserverStatus() {
		return svnserverStatus;
	}

	public void setSvnserverStatus(String svnserverStatus) {
		this.svnserverStatus = svnserverStatus;
	}

	public String getSvnserverContentDiff() {
		return svnserverContentDiff;
	}

	public void setSvnserverContentDiff(String svnserverContentDiff) {
		this.svnserverContentDiff = svnserverContentDiff;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = SvnConfDiff.getNowTimestamp();
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = SvnConfDiff.getNowTimestamp();
	}

	@Override
	public String toString() {
		return "SvnConfDiff [id=" + id + ", svnRepoId=" + svnRepoId
				+ ", svnRepoName=" + svnRepoName + ", svnRepoPath="
				+ svnRepoPath + ", svnRepoUrl=" + svnRepoUrl + ", svnRepoDes="
				+ svnRepoDes + ", passwdStatus=" + passwdStatus
				+ ", passwdContentDiff=" + passwdContentDiff + ", authzStatus="
				+ authzStatus + ", authzContentDiff=" + authzContentDiff
				+ ", svnserverStatus=" + svnserverStatus
				+ ", svnserverContentDiff=" + svnserverContentDiff
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ "]";
	}
	
	public SvnConfDiff(long id, long svnRepoId, String svnRepoName,
			String svnRepoPath, String svnRepoUrl, String svnRepoDes,
			String passwdStatus, String passwdContentDiff, String authzStatus,
			String authzContentDiff, String svnserverStatus,
			String svnserverContentDiff, Timestamp createDate,
			Timestamp modifyDate) {
		super();
		this.id = id;
		this.svnRepoId = svnRepoId;
		this.svnRepoName = svnRepoName;
		this.svnRepoPath = svnRepoPath;
		this.svnRepoUrl = svnRepoUrl;
		this.svnRepoDes = svnRepoDes;
		this.passwdStatus = passwdStatus;
		this.passwdContentDiff = passwdContentDiff;
		this.authzStatus = authzStatus;
		this.authzContentDiff = authzContentDiff;
		this.svnserverStatus = svnserverStatus;
		this.svnserverContentDiff = svnserverContentDiff;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public SvnConfDiff() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
