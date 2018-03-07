package com.bmtc.svn.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TimeUtils {
	private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss"; 
	
	public static String getNowTime()
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return String.valueOf(sdf.format(now));
	}
	
	public static String getNowTimeToTimestamp()
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP);
		return String.valueOf(sdf.format(now));
	}
	
	public static String getNowTime(String dateformatType)
	{
		Date now = new Date();
		if (StringUtils.isEmpty(dateformatType))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return String.valueOf(sdf.format(now));
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dateformatType);
			return String.valueOf(sdf.format(now));
		}
	}
	
	public static Timestamp getNowTimestamp()
	{
		Date now = new Date();
		Timestamp tt = new Timestamp(now.getTime());
		return tt;
	}
	
	public static Timestamp getTimestamp(String input) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP);
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  //10/18/2017 19:25:46
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");  //10/18/2017 19:25:46
		Date date = sdf.parse(input);
		Timestamp timeStamp = new Timestamp(date.getTime());
		return timeStamp;
	}
	
	public static Date getDate(String input) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP);
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  //10/18/2017 19:25:46
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");  //10/18/2017 19:25:46
		Date date = sdf.parse(input);
		return date;
	}
	
	public static Timestamp getTimestamp(String input, String dateformatType) throws ParseException
	{
		SimpleDateFormat sdf = null;
		if (StringUtils.isEmpty(dateformatType))
		{
			sdf = new SimpleDateFormat(TIMESTAMP);
		}
		else
		{
			sdf = new SimpleDateFormat(dateformatType);
		}
		Date date = sdf.parse(input);
		Timestamp timeStamp = new Timestamp(date.getTime());
		return timeStamp;
	}
	
	public static String getStringTime(Timestamp input) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP);
		Date inputDate = new Date(input.getTime());
		String result = sdf.format(inputDate);
		return result;
	}
	
	public static String getStringTime(Timestamp input, String dateformatType) throws ParseException
	{
		SimpleDateFormat sdf = null;
		if (StringUtils.isEmpty(dateformatType))
		{
			sdf = new SimpleDateFormat(TIMESTAMP);
		}
		else
		{
			sdf = new SimpleDateFormat(dateformatType);
		}
		Date inputDate = new Date(input.getTime());
		String result = sdf.format(inputDate);
		return result;
	}
}
