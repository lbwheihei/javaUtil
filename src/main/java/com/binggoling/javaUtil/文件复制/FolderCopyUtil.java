package com.binggoling.javaUtil.文件复制;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FolderCopyUtil {
	
	/**
	* 复制单个文件
	* 
	* @param oldPath
	*            String 原文件路径 如：c:/fqf.txt
	* @param newPath
	*            String 复制后路径 如：f:/fqf.txt
	* @return boolean
	* @throws Exception 
	*/
	public boolean Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
