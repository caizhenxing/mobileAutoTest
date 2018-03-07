package com.bmtc.device.utils;

import java.util.LinkedList;
import java.util.List;

import com.bmtc.common.exception.BDException;
import com.bmtc.device.domain.Robot;

/**
 * @author: Jason.ma
 * @date: 2018年1月18日上午11:11:06
 *
 */
public class RobotUtils {

	public static List<String> buildParamForAndroid(Robot robot) {
		List<String> cmds = new LinkedList<String>();
		String pybot = PropertiesUtils.getPybot();

		String log = robot.getLog();
		String url = robot.getUrl();
		String udid = robot.getUdid();
		String version = robot.getVerison();
		int systemPort = robot.getSystemPort();
		String testCase = robot.getCaseName();
		String testSuite = robot.getTestSuite();

		
		cmds.add("cmd");
		cmds.add("/c");
		cmds.add(pybot);
		
		if (log != null && log != "") {
			cmds.add("-d");
			cmds.add(log);
		}

		if (url !=null && url != "") {
			cmds.add("-v");
			cmds.add("url:"+url);
		}

		if (udid !=null && udid !="") {
			cmds.add("-v");
			cmds.add("udid:"+udid);
		}

		if (version!=null && version !="") {
			cmds.add("-v");
			cmds.add("platformVersion:"+version);
		}

		if (systemPort != -1) {
			cmds.add("-v");
			cmds.add("systemPort:"+systemPort);
		}

		if (testCase != null && testCase != "") {
			cmds.add("-t");
			cmds.add(testCase);
		}

		if (testSuite == null  || testSuite =="") {
			throw new BDException("testSuite 不能为空");
		}
		cmds.add(testSuite);

		return cmds;
	}
	
	public static List<String> buildParamForIOS(Robot robot) {
		List<String> cmds = new LinkedList<String>();
		String pybot = PropertiesUtils.getPybot();

		String log = robot.getLog();
		String url = robot.getUrl();
		String udid = robot.getUdid();
		String version = robot.getVerison();
		int wdaLocalPort = robot.getWdaLocalPort();
		String testCase = robot.getCaseName();
		String testSuite = robot.getTestSuite();

		
		cmds.add("cmd");
		cmds.add("/c");
		cmds.add(pybot);
		
		if (log != null && log != "") {
			cmds.add("-d");
			cmds.add(log);
		}

		if (url !=null && url != "") {
			cmds.add("-v");
			cmds.add("url:"+url);
		}

		if (udid !=null && udid !="") {
			cmds.add("-v");
			cmds.add("udid:"+udid);
		}

		if (version!=null && version !="") {
			cmds.add("-v");
			cmds.add("platformVersion:"+ version);
		}

		if (wdaLocalPort != -1) {
			cmds.add("-v");
			cmds.add("wdaLocalPort:"+ wdaLocalPort);
		}

		if (testCase != null && testCase != "") {
			cmds.add("-t");
			cmds.add(testCase);
		}

		if (testSuite.equals("")) {
			throw new BDException("testSuite 不能为空");
		}
		cmds.add(testSuite);

		return cmds;
	}
}
