package com.bmtc.svn.domain;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import com.bmtc.svn.exception.BusinessException;
import com.bmtc.svn.common.utils.TimeUtils;

/**
 * SVN群组权限表
 * @author lpf7161
 *
 */
public class SvnGroupAuthz {
	
	private static Logger logger = Logger.getLogger(SvnGroupAuthz.class);
	
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
	 * svn用户id
	 */
	private long svnGroupId;
	
	/**
	 * svn群组权限创建时间
	 */
	private Timestamp svnGroupAuthzCreateDate;
	
	/**
	 * svn群组权限修改时间
	 */
	private Timestamp svnGroupAuthzModifyDate;

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

	public long getSvnGroupId() {
		return svnGroupId;
	}

	public void setSvnGroupId(long svnGroupId) {
		this.svnGroupId = svnGroupId;
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

	public SvnGroupAuthz(long svnGroupAuthzId, String svnGroupAuthz, String svnGroupPath, long svnGroupId,
			Timestamp svnGroupAuthzCreateDate, Timestamp svnGroupAuthzModifyDate) {
		super();
		this.svnGroupAuthzId = svnGroupAuthzId;
		this.svnGroupAuthz = svnGroupAuthz;
		this.svnGroupPath = svnGroupPath;
		this.svnGroupId = svnGroupId;
		this.svnGroupAuthzCreateDate = svnGroupAuthzCreateDate;
		this.svnGroupAuthzModifyDate = svnGroupAuthzModifyDate;
	}

	public SvnGroupAuthz() {
		super();
	}

	@Override
	public String toString() {
		return "SvnGroupAuthz [svnGroupAuthzId=" + svnGroupAuthzId + ", svnGroupAuthz=" + svnGroupAuthz
				+ ", svnGroupPath=" + svnGroupPath + ", svnGroupId=" + svnGroupId + ", svnGroupAuthzCreateDate="
				+ svnGroupAuthzCreateDate + ", svnGroupAuthzModifyDate=" + svnGroupAuthzModifyDate + "]";
	}
	
	
}
