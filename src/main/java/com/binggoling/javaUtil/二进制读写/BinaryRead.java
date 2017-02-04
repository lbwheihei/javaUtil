package com.binggoling.javaUtil.二进制读写;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;

public class BinaryRead {

	public static void main(String[] args) {
		String path = "C:\\Users\\quanxun\\Desktop\\quanxun\\AQI\\AQI_CON_6.grd";
		readBinary(path);

	}

	/**
	 * 读取二进制文件并解析出来
	 * 
	 * @param file
	 */
	public static void readBinary(String file) {
		float[][] data = new float[121][91];
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			
			for (int i = 0; i < 91; i++) {
				for (int j = 0; j < 121; j++) {
					float da = dis.readFloat();
					data[j][i] = da;
				}
				
			}
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter fw = new FileWriter("C:\\Users\\quanxun\\Desktop\\quanxun\\gridtest.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < data[0].length; i++) {
				for (int j = 0; j < data.length; j++) {
					bw.write(leng(String.valueOf(data[j][i])));
				}
				bw.write("\r\n");
			}
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static String leng(String data){
		
		if (data.length() < 9) {
			int num = 9- data.length();
			String str = "";
			for (int i = 0; i <num; i++) {
				str = str + " ";
			}
			data = data +str;
		}
		return data;
	}
	

	    
	  

}
