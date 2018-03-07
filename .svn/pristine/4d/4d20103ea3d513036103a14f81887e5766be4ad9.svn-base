package com.bmtc.device.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@author: Jason.ma
 *@date: 2018年1月22日下午1:42:20
 *
 */
public class StreamWatch extends Thread{
	private final Logger logger = LoggerFactory.getLogger(StreamWatch.class);
	private InputStream is = null;
	private ArrayList<String> stream = null;
	private InputStreamReader isr = null;
	private BufferedReader br = null;

	public StreamWatch(InputStream is) {
		this.is = is;
		stream = new ArrayList<String>();
	}

	@Override
	public void run() {
		try {
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.info(line);
				stream.add(line);
			}

		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			try {
				if (null != isr || null != br || null != is) {
					isr.close();
					br.close();
					is.close();
				}
			} catch (IOException e) {
				logger.error("关闭流错误 {}", e);
			}

		}
	}

	public ArrayList<String> getStream() {
		return stream;
	}
}

