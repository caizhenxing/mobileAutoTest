package com.bmtc.svn.domain;

import java.sql.Timestamp;


/**
 * SVN新建分支信息类
 * @author lpf7161
 *
 */
public class SvnCreateBranchInfo {
	
	/**
	 * svn新建分支表的主键id
	 */
	private long id;
	
	/**
	 * 所属产品机构id
	 */
	private Long deptId;
	
	/**
	 * 所属产品机构name
	 */
	private String deptName;
	
	/**
	 * 所属批次的ID
	 */
	private Long batchId;
	
	/**
	 * 所属批次的名称
	 */
	private String batchName;
	
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
	 * svn新建分支基线
	 */
	private String svnTrunk;
	
	/**
	 * svn需要新建的分支
	 */
	private String newBranch;
	
	/**
	 * 新建分支的备注信息
	 */
	private String createBranchComment;

    /**
     * 创建时间
     */
    private Timestamp createDate;
    
    /**
     * 修改时间
     */
    private Timestamp modifyDate;

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

	public String getSvnTrunk() {
		return svnTrunk;
	}

	public void setSvnTrunk(String svnTrunk) {
		this.svnTrunk = svnTrunk;
	}

	public String getNewBranch() {
		return newBranch;
	}

	public void setNewBranch(String newBranch) {
		this.newBranch = newBranch;
	}

	public String getCreateBranchComment() {
		return createBranchComment;
	}

	public void setCreateBranchComment(String createBranchComment) {
		this.createBranchComment = createBranchComment;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	@Override
	public String toString() {
		return "SvnCreateBranchInfo [id=" + id + ", deptId=" + deptId
				+ ", deptName=" + deptName + ", batchId=" + batchId
				+ ", batchName=" + batchName + ", svnRepoId=" + svnRepoId
				+ ", svnRepoName=" + svnRepoName + ", svnRepoPath="
				+ svnRepoPath + ", svnRepoUrl=" + svnRepoUrl + ", svnRepoDes="
				+ svnRepoDes + ", svnTrunk=" + svnTrunk + ", newBranch="
				+ newBranch + ", createBranchComment=" + createBranchComment
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ "]";
	}

	public SvnCreateBranchInfo(long id, Long deptId, String deptName,
			Long batchId, String batchName, long svnRepoId, String svnRepoName,
			String svnRepoPath, String svnRepoUrl, String svnRepoDes,
			String svnTrunk, String newBranch, String createBranchComment,
			Timestamp createDate, Timestamp modifyDate) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.deptName = deptName;
		this.batchId = batchId;
		this.batchName = batchName;
		this.svnRepoId = svnRepoId;
		this.svnRepoName = svnRepoName;
		this.svnRepoPath = svnRepoPath;
		this.svnRepoUrl = svnRepoUrl;
		this.svnRepoDes = svnRepoDes;
		this.svnTrunk = svnTrunk;
		this.newBranch = newBranch;
		this.createBranchComment = createBranchComment;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public SvnCreateBranchInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
