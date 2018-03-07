package com.bmtc.svn.domain;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import com.bmtc.svn.exception.BusinessException;
import com.bmtc.svn.common.utils.TimeUtils;

/**
 * SVN群组信息表
 * @author lpf7161
 *
 */
public class SvnGroup {

	private static Logger logger = Logger.getLogger(SvnGroup.class);
	
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

	public String getSvn_repo_name() {
		return svn_repo_name;
	}

	public void setSvn_repo_name(String svn_repo_name) {
		this.svn_repo_name = svn_repo_name;
	}

	public SvnGroup() {
		super();
	}

	public SvnGroup(long svnGroupId, String svnGroupName, String svnGroupUsers, String svn_repo_name,
			Timestamp svnGroupCreateDate, Timestamp svnGroupModifyDate) {
		super();
		this.svnGroupId = svnGroupId;
		this.svnGroupName = svnGroupName;
		this.svnGroupUsers = svnGroupUsers;
		this.svn_repo_name = svn_repo_name;
		this.svnGroupCreateDate = svnGroupCreateDate;
		this.svnGroupModifyDate = svnGroupModifyDate;
	}

	@Override
	public String toString() {
		return "SvnGroup [svnGroupId=" + svnGroupId + ", svnGroupName=" + svnGroupName + ", svnGroupUsers="
				+ svnGroupUsers + ", svn_repo_name=" + svn_repo_name + ", svnGroupCreateDate=" + svnGroupCreateDate
				+ ", svnGroupModifyDate=" + svnGroupModifyDate + "]";
	}

		
}
