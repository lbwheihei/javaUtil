package com.binggoling.javaUtil.时间;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class date_AddAndAubtract {
	
	/**
	 * 获取当前时间减xx分钟 ，返回日期date
	 * 
	 * @return
	 */
	public static Date getBeforeMiniTime(Date date, int mini) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 0 - mini);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间减xx分钟 ，返回日期date
	 * 
	 * @return
	 */
	public static Date getAfterMiniTime(Date date, int mini) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, mini);
		return calendar.getTime();
	}
	

	/**
	 * 获取当前时间减xx小时,返回日期 date
	 * 
	 * @return
	 */
	public static Date getDateBeforeHourTime(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 0 - hour);
		return calendar.getTime();
	}
	
	/**
	 * 时间加上多少分钟
	 * 
	 * @param time
	 *            如201309011200
	 * @param minute
	 * @return
	 */
	public static long timeAddMinute(long time, int minute) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			cal.setTime(dateFormat.parse(time + ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.MINUTE, minute);
		return timeToLong(cal.getTime(), "yyyyMMddHHmm");

	}

	/**
	 * 时间加上多少分钟
	 * 
	 * @param time
	 *            如201309011200
	 * @param minute
	 * @return
	 */
	public static Date timeAddMinute(Date time, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 返回时间格式long类型数据(如201309011200)
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long timeToLong(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String retTime = dateFormat.format(date);
		return Long.valueOf(retTime);
	}

	/**
	 * 时间加上多少小时
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date timeAddHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return cal.getTime();
	}


}
