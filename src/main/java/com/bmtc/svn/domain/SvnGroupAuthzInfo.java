package com.bmtc.svn.domain;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import com.bmtc.svn.exception.BusinessException;
import com.bmtc.svn.common.utils.TimeUtils;
/**
 * SVN群组和权限信息表
 * @author lpf7161
 *
 */
public class SvnGroupAuthzInfo {
	
	private static Logger logger = Logger.getLogger(SvnGroupAuthzInfo.class);
	
	/**
	 * svn群组id
	 */
	private long svnGroupId;
	
	/**
	 * svn群组名
	 */
	private String svnGroupName;
	
	/**
	 * svn群组用户名集合，user1,user2,…
	 */
	private String svnGroupUsers;

	/**
	 * svn库名
	 */
	private String svn_repo_name;
	
	/**
	 * svn群组创建时间
	 */
	private Timestamp svnGroupCreateDate;
	
	/**
	 * svn群组修改时间
	 */
	private Timestamp svnGroupModifyDate;
	
	/**
	 * svn用户群组权限id
	 */
	private long svnGroupAuthzId;
	
	/**
	 * svn用户群组权限
	 */
	private String svnGroupAuthz;
	
	/**
	 * svn路径
	 */
	private String svnGroupPath;
	
	/**
	 * svn群组权限创建时间
	 */
	private Timestamp svnGroupAuthzCreateDate;
	
	/**
	 * svn群组权限修改时间
	 */
	private Timestamp svnGroupAuthzModifyDate;

	public long getSvnGroupId() {
		return svnGroupId;
	}

	public void setSvnGroupId(long svnGroupId) {
		this.svnGroupId = svnGroupId;
	}

	public String getSvnGroupName() {
		return svnGroupName;
	}

	public void setSvnGroupName(String svnGroupName) {
		this.svnGroupName = svnGroupName;
	}

	public String getSvnGroupUsers() {
		return svnGroupUsers;
	}

	public void setSvnGroupUsers(String svnGroupUsers) {
		this.svnGroupUsers = svnGroupUsers;
	}
	
	public String getSvn_repo_name() {
		return svn_repo_name;
	}

	public void setSvn_repo_name(String svn_repo_name) {
		this.svn_repo_name = svn_repo_name;
	}

	public Timestamp getSvnGroupCreateDate() {
		return svnGroupCreateDate;
	}

	public void setSvnGroupCreateDate(String svnGroupCreateDate) {
		try 
		{
			if (!StringUtils.isEmpty(svnGroupCreateDate))
			{
				this.svnGroupCreateDate = TimeUtils.getTimestamp(svnGroupCreateDate);
			}
			else
			{
				this.svnGroupCreateDate = null;
			}
		}
		catch (ParseException e) 
		{
			logger.info("parse date fail" + e);
			throw new BusinessException("0010","parse date fail");
		}
	}

	public Timestamp getSvnGroupModifyDate() {
		return svnGroupModifyDate;
	}

	public void setSvnGroupModifyDate(String svnGroupModifyDate) {
		try 
		{
			if (!StringUtils.isEmpty(svnGroupModifyDate))
			{
				this.svnGroupModifyDate = TimeUtils.getTimestamp(svnGroupModifyDate);
			}
			else
			{
				this.svnGroupModifyDate = null;
			}
		}
		catch (ParseException e) 
		{
			logger.info("parse date fail" + e);
			throw new BusinessException("0010","parse date fail");
		}
	}

	public long getSvnGroupAuthzId() {
		return svnGroupAuthzId;
	}

	public void setSvnGroupAuthzId(long svnGroupAuthzId) {
		this.svnGroupAuthzId = svnGroupAuthzId;
	}

	public String getSvnGroupAuthz() {
		return svnGroupAuthz;
	}

	public void setSvnGroupAuthz(String svnGroupAuthz) {
		this.svnGroupAuthz = svnGroupAuthz;
	}

	public String getSvnGroupPath() {
		return svnGroupPath;
	}

	public void setSvnGroupPath(String svnGroupPath) {
		this.svnGroupPath = svnGroupPath;
	}

	public Timestamp getSvnGroupAuthzCreateDate() {
		return svnGroupAuthzCreateDate;
	}

	public void setSvnGroupAuthzCreateDate(String svnGroupAuthzCreateDate) {
		try 
		{
			if (!StringUtils.isEmpty(svnGroupAuthzCreateDate))
			{
				this.svnGroupAuthzCreateDate = TimeUtils.getTimestamp(svnGroupAuthzCreateDate);
			}
			else
			{
				this.svnGroupAuthzCreateDate = null;
			}
		}
		catch (ParseException e) 
		{
			logger.info("parse date fail" + e);
			throw new BusinessException("0010","parse date fail");
		}
	}

	public Timestamp getSvnGroupAuthzModifyDate() {
		return svnGroupAuthzModifyDate;
	}

	public void setSvnGroupAuthzModifyDate(String svnGroupAuthzModifyDate) {
		try 
		{
			if (!StringUtils.isEmpty(svnGroupAuthzModifyDate))
			{
				this.svnGroupAuthzModifyDate = TimeUtils.getTimestamp(svnGroupAuthzModifyDate);
			}
			else
			{
				this.svnGroupAuthzModifyDate = null;
			}
		}
		catch (ParseException e) 
		{
			logger.info("parse date fail" + e);
			throw new BusinessException("0010","parse date fail");
		}
	}
	
	public SvnGroupAuthzInfo() {
		super();
	}

	public SvnGroupAuthzInfo(long svnGroupId, String svnGroupName, String svnGroupUsers, String svn_repo_name,
			Timestamp svnGroupCreateDate, Timestamp svnGroupModifyDate, long svnGroupAuthzId, String svnGroupAuthz,
			String svnGroupPath, Timestamp svnGroupAuthzCreateDate, Timestamp svnGroupAuthzModifyDate) {
		super();
		this.svnGroupId = svnGroupId;
		this.svnGroupName = svnGroupName;
		this.svnGroupUsers = svnGroupUsers;
		this.svn_repo_name = svn_repo_name;
		this.svnGroupCreateDate = svnGroupCreateDate;
		this.svnGroupModifyDate = svnGroupModifyDate;
		this.svnGroupAuthzId = svnGroupAuthzId;
		this.svnGroupAuthz = svnGroupAuthz;
		this.svnGroupPath = svnGroupPath;
		this.svnGroupAuthzCreateDate = svnGroupAuthzCreateDate;
		this.svnGroupAuthzModifyDate = svnGroupAuthzModifyDate;
	}

	@Override
	public String toString() {
		return "SvnGroupAuthzInfo [svnGroupId=" + svnGroupId + ", svnGroupName=" + svnGroupName + ", svnGroupUsers="
				+ svnGroupUsers + ", svn_repo_name=" + svn_repo_name + ", svnGroupCreateDate=" + svnGroupCreateDate
				+ ", svnGroupModifyDate=" + svnGroupModifyDate + ", svnGroupAuthzId=" + svnGroupAuthzId
				+ ", svnGroupAuthz=" + svnGroupAuthz + ", svnGroupPath=" + svnGroupPath + ", svnGroupAuthzCreateDate="
				+ svnGroupAuthzCreateDate + ", svnGroupAuthzModifyDate=" + svnGroupAuthzModifyDate + "]";
	}

	
}
