package com.bmtc.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ConfigInfoDO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 系统配置主键
    private Long id;
    
    // svn服务器IP地址
    private String svnServer;
    
    // svn服务器用户名
    private String svnServerUserName;
    
    // svn服务器登录口令
    private String svnServerPassword;
    
    // svn配置文件临时存放位置，里面包含conf和confBackup文件夹
    private String svnConfFilesLocation;
    
    // 超级用户的svn用户名
    private String svnRootUserName;
    
    // 创建时间
    private Timestamp createDate;
    
    // 修改时间
    private Timestamp modifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSvnServer() {
		return svnServer;
	}

	public void setSvnServer(String svnServer) {
		this.svnServer = svnServer;
	}

	public String getSvnServerUserName() {
		return svnServerUserName;
	}

	public void setSvnServerUserName(String svnServerUserName) {
		this.svnServerUserName = svnServerUserName;
	}

	public String getSvnServerPassword() {
		return svnServerPassword;
	}

	public void setSvnServerPassword(String svnServerPassword) {
		this.svnServerPassword = svnServerPassword;
	}

	public String getSvnConfFilesLocation() {
		return svnConfFilesLocation;
	}

	public void setSvnConfFilesLocation(String svnConfFilesLocation) {
		this.svnConfFilesLocation = svnConfFilesLocation;
	}

	public String getSvnRootUserName() {
		return svnRootUserName;
	}

	public void setSvnRootUserName(String svnRootUserName) {
		this.svnRootUserName = svnRootUserName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ConfigInfoDO [id=" + id + ", svnServer=" + svnServer
				+ ", svnServerUserName=" + svnServerUserName
				+ ", svnServerPassword=" + svnServerPassword
				+ ", svnConfFilesLocation=" + svnConfFilesLocation
				+ ", svnRootUserName=" + svnRootUserName + ", createDate="
				+ createDate + ", modifyDate=" + modifyDate + "]";
	}

	public ConfigInfoDO(Long id, String svnServer, String svnServerUserName,
			String svnServerPassword, String svnConfFilesLocation,
			String svnRootUserName, Timestamp createDate, Timestamp modifyDate) {
		super();
		this.id = id;
		this.svnServer = svnServer;
		this.svnServerUserName = svnServerUserName;
		this.svnServerPassword = svnServerPassword;
		this.svnConfFilesLocation = svnConfFilesLocation;
		this.svnRootUserName = svnRootUserName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public ConfigInfoDO() {
		super();
	}

    
}
