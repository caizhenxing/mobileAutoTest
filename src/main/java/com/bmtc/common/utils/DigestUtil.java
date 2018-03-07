package com.bmtc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DigestUtil {
	
	/** 
     * 获取文件的摘要值
     *
     * @param file
     * @param algorithm 所请求算法的名称  for example: MD5, SHA1, SHA-256, SHA-384, SHA-512 etc.
     * @return
	 * @throws IOException
     */  
    public static String getFileDigest(File file, String algorithm) throws IOException {
  
        if (!file.isFile()) {
            return null;
        }
        
        if (file == null || !file.exists()) {
        	return file + "文件不存在";
        }

        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;

        try (FileInputStream in = new FileInputStream(file)) {
            digest = MessageDigest.getInstance(algorithm);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
        	throw new RuntimeException(e.getMessage());
		}
        
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /** 
     * 获取文件夹中文件的摘要值
     *  
     * @param dirFile
     * @param algorithm 所请求算法的名称  for example: MD5, SHA1, SHA-256, SHA-384, SHA-512 etc.
     * @param listChild 是否递归子目录中的文件
     * @return
     * @throws IOException 
     */
    public static Map<String, String> getDirDigest(File dirFile, String algorithm, boolean listChild) throws IOException {
  
        if (!dirFile.isDirectory()) {
            return null;
        }
  
        // <filepath, algCode>
        Map<String, String> pathAlgMap = new HashMap<String, String>();
        String algCode;
        File files[] = dirFile.listFiles();
  
        for (int i = 0; i < files.length; i++) {
            File file = files[i];  
            if (file.isDirectory() && listChild) {
                pathAlgMap.putAll(getDirDigest(file, algorithm, listChild));
            } else {
                algCode = getFileDigest(file, algorithm);
                if (algCode != null) {
                    pathAlgMap.put(file.getPath(), algCode);
                }
            }
        }
        return pathAlgMap;
    }
}
