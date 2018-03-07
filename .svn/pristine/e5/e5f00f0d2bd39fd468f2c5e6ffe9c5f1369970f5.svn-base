package com.bmtc.system.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


import com.bmtc.system.utils.BMTC.BMTCSoap;

public class GetDataByATP {
	
	public static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 从ATP获取数据
	 * @param dataType
	 * @return
	 * @throws MalformedURLException 
	 * @throws Exception
	 */
	
	public static BMTCSoap  getData() throws MalformedURLException{
		URL url = new URL("http://22.188.48.106:9090/IFWebService/BMTC.asmx?wsdl");
		QName qName = new QName("BMTC","BMTC");
		Service service = Service.create(url,qName);
		BMTCSoap bmtcSoap = service.getPort(BMTCSoap.class);
		return bmtcSoap;
		
	}
	
	
	
}