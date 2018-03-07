package com.bmtc.svn.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.tmatesoft.svn.core.SVNException;


public class CommonUtils 
{
	
	/**
	 * @author HaoYong
	 * @category uuid
	 * @名称：getId
	 * @简述：获取uuid
	 * @return:	String
	 */
	public static String getId()
	{
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString().replace("-", "");
		return result;
	}
	
	/**
	 * @author HaoYong
	 * @category 时间id
	 * @名称：getNowId
	 * @简述：时间id
	 * @return:	String
	 */
	public static String getNowId()
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");		
		return String.valueOf(sdf.format(now));
	}
	
	
	public static void main(String[] args) {		
		System.out.print(getNowId());
	}
}
