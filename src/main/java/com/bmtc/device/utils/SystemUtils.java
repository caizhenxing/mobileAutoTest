package com.bmtc.device.utils;

import java.util.List;

/**
 * @author: Jason.ma
 * @date: 2018年1月2日下午2:29:27
 *
 */
public class SystemUtils {
	/**
	 * 获取系统类型
	 * 
	 * @return
	 */
	public static String getSystemType() {
		String sys = System.getProperty("os.name");
		if (sys.toLowerCase().startsWith("windows")) {
			return "0";
		} else {
			return "1";
		}
	}
	
	public static String getPid(int port){
		String pid ="";
		String sysType = getSystemType();
		String pidShell = PropertiesUtils.getPidSh(String.valueOf(port));
		
		if ("0".equals(sysType)) {
			List<String> pidInfo = ExecuteCmdUtils.execute(pidShell);
			for (String info : pidInfo) {
				if (info.contains("LISTENING")) {
					pid = info.split("LISTENING")[1].replace(" ", "").trim();
					break;
				}
			}
		}
		if ("1".equals(sysType)) {
			List<String> pidInfo = ExecuteCmdUtils.runShell(pidShell);
			if (pidInfo.size() >= 1) {
				pid = pidInfo.get(0);
			}
		}
		return pid;
	}
}
