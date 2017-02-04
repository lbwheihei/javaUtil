package com.binggoling.javaUtil.读取系统配置文件;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public final class PropertyUtils {
	//public static final String DEFAULT_PROPERTIES = "\\src\\main\\resources\\application.properties";
	public static final String DEFAULT_PROPERTIES = "\\conf\\application.properties";
	
	
	public PropertyUtils() {
		
	}

	public static String get(String key,String propertiesPath) {
		Properties props = new Properties();
		StringBuffer path = new StringBuffer();
		path.append(System.getProperty("user.dir"));
		path.append(propertiesPath);
	
		try {
			props.load(new FileInputStream(path.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object result = props.get(key);
		return result == null ? "" : result.toString();
	}
	
	public static String get(String key){
		return get(key,DEFAULT_PROPERTIES);
	}
	
	

}
