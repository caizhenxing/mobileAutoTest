package com.bmtc.svn.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 */
public class EncryptUtil {
	/**
	 * 
	 */
	private static final String CVT= "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz0123456789#@$";

	/**
	 * 
	 */
	private static final int FILLCHAR = '*';

	/**
	 * 加密
	 * 
	 * @param str
	 *            明文
	 * @return 密文
	 */
	public static String encrypt(String str) {
		byte[] data = str.getBytes();
		int c;
		int len = data.length;
		StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
		for (int i = 0; i < len; ++i) {
			c = (data[i] >> 2) & 0x3f;
			ret.append(CVT.charAt(c));
			c = (data[i] << 4) & 0x3f;
			if (++i < len) {
				c |= (data[i] >> 4) & 0x0f;
			}
			ret.append(CVT.charAt(c));
			if (i < len) {
				c = (data[i] << 2) & 0x3f;
				if (++i < len) {
					c |= (data[i] >> 6) & 0x03;
				}
				ret.append(CVT.charAt(c));
			} else {
				++i;
				ret.append((char) FILLCHAR);
			}
			if (i < len) {
				c = data[i] & 0x3f;
				ret.append(CVT.charAt(c));
			} else {
				ret.append((char) FILLCHAR);
			}
		}
		return ret.toString();
	}

	/**
	 * 解密
	 * 
	 * @param str
	 *            密文
	 * @return 明文
	 */
	public static String decrypt(String str) {
		byte[] data = str.getBytes();
		int c, c1;
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; ++i) {
			c = CVT.indexOf(data[i]);
			++i;
			c1 = CVT.indexOf(data[i]);
			c = ((c << 2) | ((c1 >> 4) & 0x3));
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (FILLCHAR == c) {
					break;
				}
				c = CVT.indexOf((char) c);
				c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
				ret.append((char) c1);
			}
			if (++i < len) {
				c1 = data[i];
				if (FILLCHAR == c1) {
					break;
				}
				c1 = CVT.indexOf((char) c1);
				c = ((c << 6) & 0xc0) | c1;
				ret.append((char) c);
			}
		}
		return ret.toString();
	}

	/**
	 * apache SHA1 加密
	 * 
	 * @param str
	 *            明文
	 * @return 密文
	 */
	public static String encriptSHA1(String str) {
		try {
			return new sun.misc.BASE64Encoder().encode(MessageDigest
					.getInstance("SHA1").digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
