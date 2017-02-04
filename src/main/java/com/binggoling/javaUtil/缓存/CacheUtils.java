package com.binggoling.javaUtil.缓存;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 缓存基础类 提供最基本的缓存方法
 * 
 * @author Administrator
 */
@Component
public class CacheUtils {

	public static final String CAHCHE_NAME_MONITOR_WP = "monitorWPCache";
	public static final String CAHCHE_NAME_MONITOR_RADAR = "monitorRadarCache";

	public static final String CAHCHE_NAME_MONITOR_L_SYNOP = "monitorLSynopCache";
	public static final String CAHCHE_NAME_MONITOR_L_BUOYS = "monitorLBuoysCache";
	public static final String CAHCHE_NAME_MONITOR_L_AIREP = "monitorLAirepCache";
	public static final String CAHCHE_NAME_MONITOR_L_RAIN = "monitorLRainCache";
	public static final String CAHCHE_NAME_MONITOR_L_SHIPS = "monitorLShipsCache";
	public static final String CAHCHE_NAME_MONITOR_L_TEMP = "monitorLTempCache";

	private static CacheManager ehCacheManager;

	/**
	 * 根据缓存名称获取缓存
	 * 
	 * @param cacheName
	 *            名称
	 * @return
	 */
	public static Cache getCache(String cacheName) {

		return ehCacheManager.getCache(cacheName);
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheName
	 * @param key
	 */
	public static void removeCache(String cacheName, String key) {

		Cache cache = CacheUtils.getCache(cacheName);
		if (cache != null) {
			cache.remove(key);
		}
	}

	/**
	 * 持久化缓存到磁盘
	 * 
	 * @param cacheName
	 */
	public static void flushCache(String cacheName) {

		Cache cache = CacheUtils.getCache(cacheName);
		if (cache != null) {
			cache.flush();
		}
	}

	/**
	 * 获取值
	 * 
	 * @param cacheName
	 * @param key
	 *            测试下isKeyInCache问题
	 * @return
	 */
	public static Object getVaule(String cacheName, String key) {

		Cache cache = getCache(cacheName);
		if (cache != null && cache.isKeyInCache(key)) {
			Element element = cache.get(key);
			// 必须flush下， 否则eache将会把.index文件清空
			cache.flush();
			if (element != null) {
				return element.getValue();
			}
		}
		return null;

	}

	/**
	 * 放入缓存
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param key
	 * @param value
	 */
	public static void putCache(String cacheName, String key, Object value) {

		Cache cache = getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
		// 必须flush下， 否则eache将会把.index文件清空
		cache.flush();
	}

	/**
	 * 判断缓存是否为空
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean isEmpty(String key) {

		Cache cache = getCache(key);
		return cache.getSize() == 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * 获取对应key的值
	 * 
	 * @param cacheName
	 * @param keys
	 * @return
	 */
	public static Map<String, String> getMapCache(String cacheName,
			String[] keys) {

		Map<String, String> resultMap = new HashMap<String, String>();

		if (keys != null && keys.length > 0) {

			for (String key : keys) {
				Object val = CacheUtils.getVaule(cacheName, key);
				if (val != null) {
					resultMap.put(key, String.valueOf(val));
				}
			}

		}

		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMapCache(String cacheName) {

		Map<String, String> resultMap = new HashMap<String, String>();
		Cache cache = getCache(cacheName);
		if (cache != null) {
			List<String> keys = cache.getKeys();
			for (String key : keys) {
				Object val = CacheUtils.getVaule(cacheName, key);
				if (val != null) {
					resultMap.put(key, String.valueOf(val));
				}
			}
		}
		return resultMap;
	}

	

	public static CacheManager getEhCacheManager() {

		return ehCacheManager;
	}

	@Autowired
	public void setEhCacheManager(CacheManager ehCacheManager) {
		CacheUtils.ehCacheManager = ehCacheManager;
	}

	
}
