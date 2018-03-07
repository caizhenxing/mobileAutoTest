package com.bmtc.device.service.impl;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bmtc.common.exception.BDException;
import com.bmtc.device.domain.Appium;
import com.bmtc.device.service.AppiumService;
import com.bmtc.device.utils.AvailablePortFinder;
import com.bmtc.device.utils.ExecuteCmdUtils;
import com.bmtc.device.utils.PropertiesUtils;
import com.bmtc.device.utils.StreamWatch;
import com.bmtc.device.utils.SystemUtils;

/**
 * @author: Jason.ma
 * @date: 2018年1月2日上午10:19:14
 *
 */
@Service
public class AppiumServiceImpl implements AppiumService {
	private static final Logger logger = LoggerFactory.getLogger(AppiumServiceImpl.class);
	
	private static final long START_TIMEOUT_MILLISECONDS = 30000;
	private static final String STATUS_PATH = "/wd/hub/status";
	private String host = "127.0.0.1";
	private int appiumPort = -1;
	private int bootstrapPort = -1;
	private int chromeDriverPort = -1;
	private String node;
	private String appium;
	
	@Override
	public Appium startAppium(String log) {
		List<String> cmds = buildAppiumCmds(log);
		final ProcessBuilder pb = new ProcessBuilder(cmds).redirectErrorStream(true);
		logger.debug("启动appium服务 {}", cmds.toString());

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Process process = pb.start();
					StreamWatch inputStream = new StreamWatch(process.getInputStream());
					inputStream.setName("appiumInfo");
					inputStream.start();
					int exitvalue = process.waitFor();
					logger.info("退出appium服务， {}", exitvalue);
				} catch (Exception e) {
					logger.warn("启动appium服务异常  {}", e);
				}

			}
		}).start();

		long start = System.currentTimeMillis();
		boolean state = isRunning(appiumPort);
		while (!state) {
			long end = System.currentTimeMillis();
			if ((end - start) > START_TIMEOUT_MILLISECONDS) {
				stopAppium(appiumPort);
				throw new BDException("appium服务器在 "+ START_TIMEOUT_MILLISECONDS + " seconds"+"内启动失败");
			}
			state = isRunning(appiumPort);
		}
		Appium appium = new Appium();
		appium.setHost(host);
		appium.setPort(appiumPort);
		appium.setChromeDriverPort(chromeDriverPort);
		appium.setBootstrapPort(bootstrapPort);
		appium.setAppiumLog(log);
		appium.setSystemPort(AvailablePortFinder.getNextAvailable());
		appium.setWadLocalPort(AvailablePortFinder.getNextAvailable());
		
		return appium;
	}

	@Override
	public void stopAppium(int port) {
		String pid = SystemUtils.getPid(port);
		if (!pid.equals("")) {
			String shell =  PropertiesUtils.getKillAppiumSh(pid);
			ExecuteCmdUtils.execute(shell);
		}else{
			logger.warn("appium停止异常，未找到appium {} 服务pid", port);
		}
	}
	
	/**
	 * 检查端口是否启动
	 * @param port
	 * @return 启动:true 未启动：false
	 */
	
	private boolean isRunning(int port) {
		try {
			URI uri = new URIBuilder().setScheme("http").setHost(host)
					.setPort(appiumPort).setPath(STATUS_PATH).build();
			HttpGet httpget = new HttpGet(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(1000).setConnectionRequestTimeout(1000)
					.setSocketTimeout(1000).build();
			httpget.setConfig(requestConfig);

			CloseableHttpClient client = HttpClients.createDefault();
			HttpResponse response = client.execute(httpget);
			String strResult = EntityUtils.toString(response.getEntity());
			int status = (int) JSONObject.parseObject(strResult).get("status");
			return status == 0;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * appium启动参数准备
	 * @param log
	 * @return appium启动参数
	 */
	private List<String> buildAppiumCmds(String log) {
		appiumPort = AvailablePortFinder.getNextAvailable();
		chromeDriverPort = AvailablePortFinder.getNextAvailable();
		bootstrapPort = AvailablePortFinder.getNextAvailable();
		
		List<String> cmds = new LinkedList<String>();
		node = PropertiesUtils.getNode();
		appium = PropertiesUtils.getAppiumSh();
		host = PropertiesUtils.getHost();
		cmds.add(node);
		cmds.add(appium);
		cmds.add(String.format("--address=%s", host));
		cmds.add(String.format("--port=%d", appiumPort));
		cmds.add(String.format("--bootstrap-port=%d", bootstrapPort));
		cmds.add(String.format("--chromedriver-port=%d", chromeDriverPort));
		cmds.add(String.format("--log=%s", log+"/appium.txt"));
		cmds.add("--session-override");
		cmds.add("--log-timestamp");
		cmds.add("--local-timezone");
		return cmds;
	}
}
