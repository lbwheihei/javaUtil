package com.binggoling.javaUtil.时间;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Start_EndTimeToList {
	public static final String DATE_SEC = "yyyy-MM-dd HH:mm:ss";
	
	public List<String> getDateList(String startTime, String endTime){

		List<String> list = new ArrayList<String>();
		Date startDateTime = str2Date(startTime, "yyyyMMdd");
		Date endDateTime = str2Date(endTime, "yyyyMMdd");

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDateTime);
		long startLon = startCal.getTimeInMillis();

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDateTime);
		long endLon = endCal.getTimeInMillis();

		while (startLon <= endLon) {

			String startStr = date2Str(startCal.getTime(), "yyyyMMdd");
			list.add(startStr);
			startLon = startLon + 24 * 60 * 60 * 1000;
			startCal.setTimeInMillis(startLon);
		}
		return list;
	}
	
	public static Date str2Date(String str, String format) {
		if (null == str || "".equals(str)) {
			return null;
		}
		if (null == format || "".equals(format)) {
			format = DATE_SEC;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
