package com.bmtc.device.utils;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpRequestUtils {
	private static Logger logger = Logger.getLogger(HttpRequestUtils.class);

	public static JSONObject httpget(String url) {
		JSONObject jsonResult = new JSONObject();

		try {
			HttpGet request = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(3000).setConnectionRequestTimeout(2000)
					.setSocketTimeout(3000).build();
			request.setConfig(requestConfig);
			CloseableHttpClient client = HttpClients.createDefault();

			HttpResponse respone = client.execute(request);
			if (respone.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strResult = EntityUtils.toString(respone.getEntity());
				jsonResult = JSONObject.parseObject(strResult);
				url = URLDecoder.decode(url, "UTF-8");
			} else {
				logger.error("get 请求提交失败：" + url);
			}

		} catch (IOException e) {
			if (e.toString().contains("Connection refused") || e.toString().contains("UnknownHostException")) {
				logger.error("appium服务未启动：" + e);
				jsonResult.put("status", "2");
				logger.debug("appium服务未启动："+ jsonResult.toString());
			}else {
				jsonResult.put("status", "1");
				logger.debug("appium服务忙:"+ jsonResult.toString());
			}

		}

		return jsonResult;
	}

	public static JSONObject httpPost(String url, JSONObject jsonParam,
			boolean noNeedResponse) {
		JSONObject jsonResult = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				StringEntity entity = new StringEntity(jsonParam.toString(),
						"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = client.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					jsonResult = JSONObject.parseObject(str);
				} catch (IOException e) {
					logger.error("post请求提交失败：" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败：" + url, e);
		}

		return jsonResult;
	}

	public static JSONObject httpPost(String url, String jsonParam,
			boolean noNeedResponse) {
		JSONObject jsonResult = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				StringEntity entity = new StringEntity(jsonParam,
						"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = client.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					jsonResult = JSONObject.parseObject(str);
				} catch (IOException e) {
					logger.error("post请求提交失败：" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败：" + url, e);
		}
		return jsonResult;
	}
}
