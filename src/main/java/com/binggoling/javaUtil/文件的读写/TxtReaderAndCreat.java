package com.binggoling.javaUtil.文件的读写;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TxtReaderAndCreat {
	/**
	 * 写入txt文件
	 * @param filePath 文件地址
	 * @param list 写入txt内容
	 * */
	@SuppressWarnings("unused")
	private boolean writeTXTFile(String filePath,List<String> txtlist){
		
		if(filePath.length() ==0 ){
			System.out.println("处理地址为空！");
			return false;
		}
		if (txtlist == null || txtlist.size() == 0) {
			System.out.println("写入数据为空！");
			return false;
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		BufferedWriter bw = new BufferedWriter(fw);
		String n = "\r\n";// 换行符
		
		try {
			for (int i = 0; i < txtlist.size(); i++) {
				bw.write(txtlist.get(i)+n);
			}
			return true;
		} catch (Exception e) {
			System.out.println("写入txt文件失败："+filePath);
			return false;
		}finally{
			try {
				if(bw!=null){bw.close();}
				if(fw!=null){fw.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 读取txt文件
	 * @param filePath 路径
	 * */
	@SuppressWarnings("unused")
	private List<String> readFile(String filePath) {
		File file = new File(filePath);
		
		if(filePath.length() ==0 ){
			System.out.println("处理地址为空！");
			return null;
		}
		
		InputStream fr = null;
		try {
			fr = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(fr==null){
			System.out.println("读取文件失败！");
			System.out.println("file dir is:"+file.getAbsolutePath());
			return null;
		}
		
		List<String> txtlist = new ArrayList<String>();
		InputStreamReader isReader = null;
		try {
			isReader = new InputStreamReader(fr,"GBK");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(isReader);
		String linestr = null;

		try {
			while ((linestr = br.readLine()) != null) {
				if (linestr == null || linestr.trim().length() == 0) {// 没有内容
					continue;
				}
				txtlist.add(linestr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null){br.close();}
				if(fr!=null){fr.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return txtlist;
	}


}
