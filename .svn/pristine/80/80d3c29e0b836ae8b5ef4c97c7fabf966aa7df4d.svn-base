package com.bmtc.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtil {
	private List<String> testSuiteTxtList = new ArrayList<String>();

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public List<String> listPath(File file) {
		if (file.isFile()) {
			if (file.getName().endsWith(".txt")
					&& !file.getName().contains("init")) {
				testSuiteTxtList.add(file.getAbsolutePath());
			}
		}
		else {
			File files[] = file.listFiles();
			
			for (File f : files) {
				if (f.isDirectory()) {
					listPath(f);
				} else {
					if (f.getName().endsWith(".txt")
							&& !f.getName().contains("init")) {
						testSuiteTxtList.add(f.getAbsolutePath());
					}
				}
			}
		}
		return testSuiteTxtList;
	}
}
