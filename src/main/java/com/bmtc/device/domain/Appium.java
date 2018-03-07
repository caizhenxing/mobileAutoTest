package com.bmtc.device.domain;

import java.io.Serializable;

/**
 * @author: Jason.ma
 * @date: 2018年1月3日上午11:40:51
 *
 */
public class Appium implements Serializable{
	private static final long serialVersionUID = 4828208058302969506L;
	
	private String host;
	private int port;
	private int chromeDriverPort;
	private int bootstrapPort;
	private int systemPort;
	private int wadLocalPort;
	
	private String appiumLog;

	public int getSystemPort() {
		return systemPort;
	}

	public void setSystemPort(int systemPort) {
		this.systemPort = systemPort;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getChromeDriverPort() {
		return chromeDriverPort;
	}

	public void setChromeDriverPort(int chromeDriverPort) {
		this.chromeDriverPort = chromeDriverPort;
	}

	public int getBootstrapPort() {
		return bootstrapPort;
	}

	public void setBootstrapPort(int bootstrapPort) {
		this.bootstrapPort = bootstrapPort;
	}

	public String getAppiumLog() {
		return appiumLog;
	}

	public void setAppiumLog(String appiumLog) {
		this.appiumLog = appiumLog;
	}

	
	public int getWadLocalPort() {
		return wadLocalPort;
	}

	public void setWadLocalPort(int wadLocalPort) {
		this.wadLocalPort = wadLocalPort;
	}

	@Override
	public String toString() {
		return "Appium [host=" + host + ", port=" + port
				+ ", chromeDriverPort=" + chromeDriverPort + ", bootstrapPort="
				+ bootstrapPort + ", systemPort=" + systemPort
				+ ", wadLocalPort=" + wadLocalPort + ", appiumLog=" + appiumLog
				+ "]";
	}

}
