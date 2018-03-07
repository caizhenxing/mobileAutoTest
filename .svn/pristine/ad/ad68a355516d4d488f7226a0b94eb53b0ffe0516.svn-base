package com.bmtc.task.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bmtc.task.domain.ExecutePlanVo;
import com.bmtc.task.domain.ProductSvn;

/**
 * webservice发送soap的工具类
 * @author Administrator
 *
 */
public class SendSoapUtils {

	public static void main(String[] args) {
		ExecutePlanVo executePlanVo = new ExecutePlanVo();
		executePlanVo.setBatchID(40);
		executePlanVo.setUserID(1);
		executePlanVo.setCaseName("11");
		executePlanVo.setScriptSVNPath("svn://22.11.31.36/automation_test/branches/801batch/BOC/Android/中银理财/产品购买和赎回/赎回.txt");
		ProductSvn productSvn = new ProductSvn();
		productSvn.setProductId(25);
		productSvn.setProductName("CSAR");
		productSvn.setUsername("wy_maxl");
		productSvn.setPassword("123456");
		productSvn.setRepository("svn://22.11.31.36/automation_test/branches");
		executePlanVo.setProductSvn(productSvn);
		try {
			String date = send("SaveATPCases",executePlanVo);
			System.out.println("=========>>		"+date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送请求获取数据
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public static String send(String dataType,ExecutePlanVo executePlanVo) throws Exception {
		String urlString = "http://22.188.48.106:9090/IFWebService/BMTC.asmx";
		String soapActionString = "http://tempuri.org/"+dataType;
		URL url = new URL(urlString);
		HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
		String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n"
				+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "<soap:Body>\n"
				+ "<"+dataType+" xmlns=\"http://tempuri.org/\" />\n"
				+ "<batchID>"+executePlanVo.getBatchID()+"</batchID>\n"
			    + "<productID>"+executePlanVo.getProductSvn().getProductId()+"</productID>\n"
			    + "<caseNum>"+executePlanVo.getCaseName()+"</caseNum>\n"
			    + "<userID>"+executePlanVo.getUserID()+"</userID>\n"
			    + "<scriptSVNPath>"+executePlanVo.getScriptSVNPath()+"</scriptSVNPath>\n"
			    + "<productName>"+executePlanVo.getProductSvn().getProductName()+"</productName>\n"
			    + "<repository>"+executePlanVo.getProductSvn().getRepository()+"</repository>\n"
			    + "<username>"+executePlanVo.getProductSvn().getUsername()+"</username>\n"
			    + "<password>"+executePlanVo.getProductSvn().getPassword()+"</password>\n"
				+ "</soap:Body>\n" + "</soap:Envelope>\n";
		System.out.println(soap);
		byte[] buf = soap.getBytes();
		httpconn.setRequestProperty("Content-Type", " text/xml; charset=utf-8");
		httpconn.setRequestProperty("Content-Length",String.valueOf(buf.length));
		httpconn.setRequestProperty("soapActionString", soapActionString);
		httpconn.setRequestMethod("POST");

		httpconn.setDoOutput(true);
		httpconn.setDoInput(true);
		OutputStream out = httpconn.getOutputStream();
		out.write(buf);
		out.close();

		byte[] datas = readInputStream(httpconn.getInputStream());
		String result = new String(datas);
		return result;
	}
	/**
	 * 读取文件
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inputStream)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inputStream.close();
		return data;
	}
}
