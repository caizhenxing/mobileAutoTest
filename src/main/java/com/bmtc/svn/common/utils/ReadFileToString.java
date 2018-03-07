package com.bmtc.svn.common.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 读取文件的工具类
 * @author lpf7161
 *
 */
public class ReadFileToString {
	
	/**
	 * 分隔符
	 */
	private static final String SEP = System.getProperty("line.separator");
	
	/**
	 * 读取文本文件为StringBuffer
	 * @param filePath 文件路径
	 * @return 读取文件的内容
	 */
	public static StringBuffer getStrBuf (String filePath) throws IOException {
		StringBuffer strBuf = new StringBuffer();
		try (Reader reader = new FileReader(filePath);
				BufferedReader br = new BufferedReader(reader)) {
			String data = null;
			while((data = br.readLine()) != null) {
				strBuf.append(data).append(SEP);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return strBuf;
	}
}
