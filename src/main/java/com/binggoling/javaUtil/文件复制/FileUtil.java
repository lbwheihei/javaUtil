package com.binggoling.javaUtil.文件复制;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 操作文件夹与文件工具类
 *
 */
public class FileUtil {
	
	/**
	 * 返回目录下最新文件时间
	 * */
	public static Date latestFielDate(String FileStr){
		File path=new File(FileStr);
		//列出该目录下所有文件和文件夹
		File[] files = path.listFiles();
		//按照文件最后修改日期倒序排序
		Arrays.sort(files, new Comparator<File>() {
		   @Override
		   public int compare(File file1, File file2) {
		      return (int)(file2.lastModified()-file1.lastModified());
		   }
		});
		Date date = new Date(files[0].lastModified());
		return date;
	}
	
	/**
	 * 通过时间的比较，返回目录下最新文件
	 * */
	public static List<File> latestFile(String FileStr,Date date){
		List<File> list = new LinkedList<File>();
		File path=new File(FileStr);
		//列出该目录下所有文件和文件夹
		File[] files = path.listFiles();
		//按照文件最后修改日期倒序排序
		Arrays.sort(files, new Comparator<File>() {
		   @Override
		   public int compare(File file1, File file2) {
		      return (int)(file2.lastModified()-file1.lastModified());
		   }
		});
		
		for (int i = 0; i < files.length; i++) {
			Date fileDate = new Date(files[i].lastModified());
			  if (fileDate.getTime() > date.getTime()) {
				  list.add(files[i]);
			  }
		}
		
		return list;
	}

	//找出当前路径下的子文件夹名
	public static ArrayList<String> getDirFolderName(String strPath){ 
		ArrayList<String> folderNames = new ArrayList<String>();
		File f = new File(strPath); 
		if(f.isDirectory()){ 
			File[] fList=f.listFiles(); 
			for(int j=0;j<fList.length;j++){ 
				if(fList[j].isDirectory()){
					folderNames.add(fList[j].getName());
				} 
	         } 
		}
		return folderNames;
	}
	
	//找出当前路径下的文件名
	public static ArrayList<String> getDirFileName(String strPath){ 
		ArrayList<String> FileNames = new ArrayList<String>();
		File f = new File(strPath); 
		if(f.isDirectory()){ 
			File[] fList=f.listFiles(); 
			for(int j=0;j<fList.length;j++){ 
				if(!fList[j].isDirectory()){
					FileNames.add(fList[j].getName());
				} 
	         } 
		}
		return FileNames;
	}
	
	//找出当前目录下的所有文件夹与文件
	public void getDirFile(String strPath){ 
		File f = new File(strPath); 
		if(f.isDirectory()){ 
			File[] fList=f.listFiles(); 
			for(int j=0;j<fList.length;j++){ 
				if(fList[j].isDirectory()) 
					getDirFile(fList[j].getPath()); 
				System.out.println(fList[j].getName());
	         } 
		}
	}	
}
