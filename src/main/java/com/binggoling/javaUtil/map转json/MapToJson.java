package com.binggoling.javaUtil.map转json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class MapToJson {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
//        map.put("1", "bb");
//        map.put("2", "cc");
//        map.put("3", "dd");
//        JSONObject jsonObject = JSONObject.fromObject(map);
//        System.out.println(jsonObject);
		String json = "{\"3\":\"dd\",\"2\":\"cc\",\"1\":\"bb\"}";
        
		JSONObject jsonObject = JSONObject.fromObject(json);  
        Iterator it = jsonObject.keys();  
        // 遍历jsonObject数据，添加到Map对象  
        while (it.hasNext())  
        {  
            String key = String.valueOf(it.next());  
            String value = (String) jsonObject.get(key);  
            map.put(key, value);  
        }  
        System.out.println("map");
        
//        try {
//            String s = jsonObject.toString();
//            Object o = JSONUtil.deserialize(s);
//            Object o1 = ((HashMap)o).get("DATA");//此举将DATA作为对象，得到�?
//            Map map = (Map)o1;//然后强转o1         
//   
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
	}

}
