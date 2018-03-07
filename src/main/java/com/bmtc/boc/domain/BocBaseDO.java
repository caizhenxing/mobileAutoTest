package com.bmtc.boc.domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 时间基类
 * @author lpf7161
 *
 */
public class BocBaseDO {

	/**
	 * 返回当前时间戳
	 * @return
	 */
	public static Timestamp getNowTimestamp()
	{
		Date now = new Date();
		Timestamp tt = new Timestamp(now.getTime());
		return tt;
	}
}
