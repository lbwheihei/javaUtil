package com.binggoling.javaUtil.读写shape;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.binggoling.javaUtil.中文转英文字母.ChineseToEnglish;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;


public class ReadShape {
	public static void main(String[] args) {
		String path = "C:\\Users\\quanxun\\Desktop\\sichuan\\四川省底图\\省界.shp";
		Map<String, MultiPolygon> map = getMultiPolygonShape(path);
		//creatShp(map);
		//testshp(map);
		reatOUT(map);
		
	}
	
	private static void reatOUT(Map<String, MultiPolygon> map) {
//		for (String key : map.keySet()) {
//			System.out.println(key);
//		}
		DecimalFormat df = new DecimalFormat("0.00000");
		
		String key  = "甘孜州";
		String path = "C:\\Users\\quanxun\\Desktop\\sichuan\\testOut\\ganzizhou_out.txt";
		String lonlat = map.get(key).toString().substring(16, map.get(key).toString().length()-3);
		String lonlatArray[] = lonlat.split(",");
		System.out.println(lonlatArray.length);
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < lonlatArray.length; i++) {
				String lonlatstr[] = lonlatArray[i].trim().split("\\s+");
				String str = df.format(Double.valueOf(lonlatstr[0])) +"  "+ df.format(Double.valueOf(lonlatstr[1])) + " ";
				System.out.println(str);
				bw.write(str);
			}
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		
	}

	private static void testshp(Map<String, MultiPolygon> map) {
		for (String key : map.keySet()) {
			String name = key;
			//System.out.println(name);
			String englishName = ChineseToEnglish.getPingYin(name);
			//System.out.println(englishName);
			String gradsstr = "'open C:\\QUANXUN\\sichuanGrads\\"+englishName+".ctl'";
			System.out.println(gradsstr);
		}
		
	}

	/**
	 * 读取shape文件
	 * @param path
	 * @return
	 */
	public static Map<String, MultiPolygon> getMultiPolygonShape(String path){
		Map<String, MultiPolygon>  map = new HashMap<String , MultiPolygon>();
		
		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
		try {
			ShapefileDataStore sds = (ShapefileDataStore) dataStoreFactory
					.createDataStore(new File(path).toURI().toURL());
			sds.setCharset(Charset.forName("GBK"));
			SimpleFeatureSource featureSource = sds.getFeatureSource();
			SimpleFeatureIterator itertor = featureSource.getFeatures().features();

//			FileWriter fw = new FileWriter("C:\\Users\\quanxun\\Desktop\\sichuan\\四川省底图\\sichuangShape.txt");
//			BufferedWriter bw = new BufferedWriter(fw);
			while (itertor.hasNext()) {
				SimpleFeature feature = itertor.next();
				Iterator<Property> it = feature.getProperties().iterator();

				String cityName = "";
				MultiPolygon multiPolygonStr = null;
						
				while (it.hasNext()) {
					Property pro = it.next();
					String name = pro.getName().toString();
					if (name.equals("NAME_1")) {
						cityName = (String) pro.getValue();
					}
					
					if (name.equals("the_geom")) {
						multiPolygonStr = (MultiPolygon) pro.getValue();
					}
				}
				map.put(cityName, multiPolygonStr);
				
			}
//			bw.close();
//			fw.close();
			itertor.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return map;
	}
	
	/**
	 * 生成shape文件
	 * @param map
	 */
	public static void creatShp(Map<String, MultiPolygon>  map){
		
		for(String key: map.keySet()){
			System.out.println(key);
		
		
		String cityName = key;
		String englishCityName = ChineseToEnglish.getPingYin(cityName);
		System.out.println(englishCityName);
		MultiPolygon mulStr = map.get(cityName);
		String filepath = "C:\\Users\\quanxun\\Desktop\\sichuan\\四川省底图\\sichuanCity\\" + englishCityName + ".shp";
		int num = 1;
		try {  
	        //创建shape文件对象  
	        File file = new File(filepath);  
	        Map<String, Serializable> params = new HashMap<String, Serializable>();  
	        params.put( ShapefileDataStoreFactory.URLP.key, file.toURI().toURL() );  
	        ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);  
	        //定义图形信息和属性信息  
	        SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();  
	        tb.setCRS(DefaultGeographicCRS.WGS84);  
	        tb.setName("shapefile");  
	        tb.add("the_geom", MultiPolygon.class);  
	        tb.add("POIID", Long.class);  
	        tb.add("NAME_1", String.class);  
	        
	        ds.createSchema(tb.buildFeatureType());  
	        ds.setCharset(Charset.forName("GBK"));  
	        //设置Writer  
	        FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);  
	        //写下一条  
	        SimpleFeature feature = writer.next();  
	       
	        feature.setAttribute("the_geom", mulStr);  
	        feature.setAttribute("POIID", num);  
	        feature.setAttribute("NAME_1", cityName);  
	        
	        writer.write();  
	        writer.close();  
	        ds.dispose();  
	          
	        //读取刚写完shape文件的图形信息  
	        ShpFiles shpFiles = new ShpFiles(filepath);  
	        ShapefileReader reader = new ShapefileReader(shpFiles, false, true, new GeometryFactory(), false);  
	        try {  
	            while (reader.hasNext()) {  
	                System.out.println(reader.nextRecord().shape());      
	            }  
	        } finally {  
	            reader.close();  
	        }  
	    } catch (Exception e) { 
	    	e.printStackTrace();
	    }  
		
	}
	}

}
