package com.bmtc.common.utils;

import java.security.MessageDigest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Utils {
	private static final String SALT = "1qazxsw2";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	private static final char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}
	
	/**
	 * @param pswd
	 * @return MD5(pswd) 小写表示
	 * @throws Exception
	 */
	/*
	public static String encryptPureMD5(String pswd) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pswd.getBytes()); 
		byte b[] = md.digest(); 
		int i;
		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i < 0) i += 256; 
			if(i < 16) buf.append("0");
			buf.append(Integer.toHexString(i)); 
		} 
		return buf.toString();
	}*/
	
	/**
	 * @param pswd
	 * @return MD5(pswd) 大写表示
	 * @throws Exception
	 */
	public static String encryptPureMD5(String pswd) throws Exception {
        try {
            byte[] btInput = pswd.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	public static void main(String[] args) {
		
		System.out.println(MD5Utils.encrypt("admin", "1"));
	}

}
