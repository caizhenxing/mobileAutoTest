package com.bmtc.device.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
	private static final String config = "./config/autotest.properties";

	public static String getPybot() {
		return getValueByKey(config, "rf.bin");
	}
	
	public static String getAutoLog() {
		return getValueByKey(config, "auto.log");
	}
	
	public static String getSysType() {
		return getValueByKey(config, "sys.type");
	}
	
	public static String getPidSh(String port) {
		return getValueByKey(config, "port.pid").replace("port", port);
	}
	
	public static String getKillAppiumSh(String pid) {
		return getValueByKey(config, "appium.kill").replace("PID", pid);
	}
	
	public static String getNode() {
		return getValueByKey(config, "node");
	}
	
	public static String getAppiumSh() {
		return getValueByKey(config, "appium.bin");
	}
	
	public static String getHost() {
		return getValueByKey(config, "host");
	}
	
	public static String getAndroidUdidSh() {
		return getValueByKey(config, "android.device.udid");
	}
	
	public static String getAndroidVersionSh(String udid) {
		return getValueByKey(config, "android.device.verison").replace("udid", udid);
	}
	
	public static String getAndroidResolutionSh(String udid) {
		return getValueByKey(config, "android.device.resolution").replace("udid", udid);
	}
	
	
	public static String getAndroidModelSh(String udid) {
		return getValueByKey(config, "android.device.model").replace("udid", udid);
	}
	
	public static String getAndroidBrandSh(String udid) {
		return getValueByKey(config, "android.device.brand").replace("udid", udid);
	}
	
	public static String getAndroidStatusSh(String udid){
		
		return getValueByKey(config, "android.device.status").replace("udid", udid);
	}
	
	public static String getIOSUdidSh(){
		return getValueByKey(config, "ios.device.udid");
	}
	
	public static String getIOSInfoSh(String udid){
		return getValueByKey(config, "ios.device.info").replace("udid", udid);
	}
	
	public static String getIOSNameSh(String udid){
		return getValueByKey(config, "ios.device.ProductType").replace("udid", udid);
	}
	
	public static String getIOSVersionSh(String udid){
		return getValueByKey(config, "ios.device.version").replace("udid", udid);
	}
	
	public static String getIOSStatusSh(String udid){
		return getValueByKey(config, "ios.device.status").replace("udid", udid);
	}
	
	public static String getSvnRootPath(){
		return getValueByKey(config, "svn.local.path");
	}
	
	
	public static String getValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		InputStream in = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(filePath);
		try {
			pps.load(in);
			String value = pps.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, String> GetAllProperties(String filePath) {
		Properties pps = new Properties();
		InputStream in = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(filePath);
		Map<String, String> property = new HashMap<String, String>();
		Enumeration en;

		try {
			pps.load(in);
			en = pps.propertyNames();
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = pps.getProperty(strKey);
				property.put(strKey, strValue);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return property;
	}
}
