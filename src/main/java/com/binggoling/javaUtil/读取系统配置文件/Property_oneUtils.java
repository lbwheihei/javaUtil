package com.binggoling.javaUtil.读取系统配置文件;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;


public final class Property_oneUtils {
	
	//全局
	public static final String DEFAULT_PROPERTIES = "application.properties";
	
	//DAT
	public static final String DAT_PROPERTIES = "application-dat.properties";
	//风廓线同步
	public static final String WP_PROPERTIES = "application-wp.properties";
	//雷达同步
	public static final String RADAR_PROPERTIES = "application-radar.properties";
	//LO
	public static final String DATA_PROPERTIES = "application-data.properties";
	//报表
	public static final String QC_REPORT_PROPERTIES = "application-qcreport.properties";
	//台风
	public static final String QC_TYPHOON_PROPERTIES = "application-typhoon.properties";
	//探空分析场
	public static final String QC_TEMPFILED_PROPERTIES = "application-temp.properties";
	//gfp
	public static final String GFS_PROPERTIES = "application-gfs.properties";
	
	//检验
	public static final String GFS_VERIFY = "application-verify.properties";
	
	//ftp
	public static final String FTP_PROPERTIES="application-ftp.properties";
	
	//hisy
	public static final String HISY_PROPERTIES="application-hisy.properties";
	
	//3公里
	public static final String THREE_PROPERTIES="application-threekm.properties";
	
	
	//xls数据配置
	public static final String XLS_PROPERTIES="application-xls.properties";
		
	//质量控制对比图表配置
	public static final String QCCP_PROPERTIES="application-qccompare.properties";
	
    //seeps评分报表配置
	public static final String SEEPS_PROPERTIES="application-seeps.properties";
	
	
	private Property_oneUtils() {
		
	}

	public static String get(Object key, String file) {
		Properties props = new Properties();
		PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(file);
		InputStream is = null;
		try {
			is = resource.getInputStream();
			propertiesPersister.load(props, new InputStreamReader(is, "UTF-8"));
			is.close();
		} catch (IOException e) {
			//LOG.error(e.getMessage(), e);
		}finally{
			try{
				if(is!=null)
					is.close();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
		Object result = props.get(key);
		return result == null ? "" : result.toString();
	}
	
	public static String get(Object key) {
		return get(key,DEFAULT_PROPERTIES);
	}
	
	public static String getDatProperties(Object key){
		return get(key,DAT_PROPERTIES);
	}
	
	public static String getWPProperties(Object key){
		return get(key,WP_PROPERTIES);
	}
	
	public static String getRrdarProperties(Object key){
		return get(key,RADAR_PROPERTIES);
	}
	
	public static String getDataProperties(Object key){
		return get(key,DATA_PROPERTIES);
	}	
	
	public static String getQCReportProperties(Object key){
		return get(key,QC_REPORT_PROPERTIES);
	}	
	
	public static String getTyphoonProperties(Object key){
		return get(key,QC_TYPHOON_PROPERTIES);
	}	
	//
	public static String getGFSProperties(Object key){
		return get(key,GFS_PROPERTIES);
	}	
	public static String getVerifyProperties(Object key){
		return get(key,GFS_VERIFY);
	}	
	public static String getFtpProperties(Object key){
		return get(key,FTP_PROPERTIES);
	}	
			
	public static String getHisyProperties(Object key) {
		return get(key,HISY_PROPERTIES);
	}

	public static String getThreeProperties(Object key) {
		return get(key,THREE_PROPERTIES);
	}
	
	public static String getXlsProperties(Object key) {
		return get(key,XLS_PROPERTIES);
	}
	
	public static String getCompareProperties(Object key) {
		return get(key,QCCP_PROPERTIES);
	}
	
	public static String getSeepsProperties(Object key) {
		return get(key,SEEPS_PROPERTIES);
	}
	public static String getTempFiledProperties(Object key) {
		return get(key,QC_TEMPFILED_PROPERTIES);
	}
	public static void put(String key,String value){
		Properties fos = new Properties();
		PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(DEFAULT_PROPERTIES);
		InputStream is = null;
		OutputStream out = null;
		
		try {
			is = resource.getInputStream();
			propertiesPersister.load(fos, new InputStreamReader(is, "UTF-8"));
			is.close();

			out = new FileOutputStream(resource.getFile().getPath());
			fos.setProperty(key, value);
			fos.store(out, "");
			out.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}finally{
			try{
				if(is!=null)
					is.close();
				if(out!=null)
					out.close();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	public static boolean put2(String propertyName,String propertyValue){
		boolean writeOK=true;         
		Properties p=new Properties();          
		InputStream in;         
		try {                           
			in = new FileInputStream(DEFAULT_PROPERTIES);             
			p.load(in);//             
			in.close();              
			p.setProperty(propertyName,propertyValue);//设置属性值，如不属性不存在新建            
			//p.setProperty("testProperty","testPropertyValue");              
			FileOutputStream out=new FileOutputStream(DEFAULT_PROPERTIES);//输出流            
			p.store(out,"");//设置属性头，如不想设置，请把后面一个用""替换掉            
			out.flush();//清空缓存，写入磁盘             
			out.close();//关闭输出流   
		} catch (Exception e) {    
			//e.printStackTrace(); 
		}       
		return writeOK;     
	}

	/**
	 *  读取自定义properties文件
	 * @param filePath
	 * @param key
	 * @return
	 */
    public static String getUsr(String key,String filePath) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty (key);
            return value;
        } catch (Exception e) {
           // e.printStackTrace();
            return null;
        }
    }


    /**
     * 写入自定义properties文件
     * @param filePath
     * @param parameterName
     * @param parameterValue
     */
    public static void putUsr(String parameterName,String parameterValue,String filePath) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            //从输入流中读取属性列表（键和元素对）
            prop.load(fis);
            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性,强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty(parameterName, parameterValue);
            //以适合使用 load 方法加载到 Properties 表中的格式，将此 Properties 表中的属性列表（键和元素对）写入输出流
             prop.store(fos, "Update '" + parameterName + "' value");
        } catch (IOException e) {
        	//LOG.info("写入 "+filePath+" 的值： "+parameterName+" 失败");
        }
    }
}
