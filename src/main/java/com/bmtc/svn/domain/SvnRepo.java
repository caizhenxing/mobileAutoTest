package com.bmtc.svn.domain;


/**
 * SVN仓库信息类
 * @author lpf7161
 *
 */
public class SvnRepo {
	
	/**
	 * svn仓库id
	 */
	private long id;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "SvnRepo [id=" + id + ", svnRepoName=" + svnRepoName
				+ ", svnRepoPath=" + svnRepoPath + ", svnRepoUrl=" + svnRepoUrl
				+ ", svnRepoDes=" + svnRepoDes + "]";
	}

	public SvnRepo(long id, String svnRepoName, String svnRepoPath,
			String svnRepoUrl, String svnRepoDes) {
		super();
		this.id = id;
		this.svnRepoName = svnRepoName;
		this.svnRepoPath = svnRepoPath;
		this.svnRepoUrl = svnRepoUrl;
		this.svnRepoDes = svnRepoDes;
	}

	public SvnRepo() {
		super();
	}
	
}
