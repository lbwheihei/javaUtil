package com.binggoling.javaUtil;

import java.io.File;
import java.util.Date;

public class test {
	
	 public static void main(String[] args) throws Exception {
	        String filePath = "F:\\nationwide\\ftp\\haze";
	       System.out.println(latestFielDate(filePath));
	 }
	 
	 /**
		 * 返回目录下最新文件时间
		 * */
		public static Date latestFielDate(String FileStr){
			File path=new File(FileStr);
			if (!path.exists()) {
				return null;
			}
			//列出该目录下所有文件和文件夹
			File[] files = path.listFiles();
			if (files.length == 0) {
				Date date = new Date();
				return date;
			}
			//按照文件最后修改日期倒序排序
//			Arrays.sort(files, new Comparator<File>() {
//			   @Override
//			   public int compare(File file1, File file2) {
//			      return (int)(file2.lastModified()-file1.lastModified());
//			   }
//			});
			
			Date date = null;
			for (int i = 0; i < files.length; i++) {
				if (date == null) {
					date = new Date(files[i].lastModified());
				}else {
					Date fileDate = new Date(files[i].lastModified());
					  if (fileDate.getTime() > date.getTime()) {
						  date = fileDate;
					  }
				}
			}
			
			return date;
		}
}
