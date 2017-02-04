package com.binggoling.javaUtil.时间;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.DateUtils;

public class CompareDateUtil {
	
	public boolean compareDate(String dateStr1, String pattern1, String timeZone, String pattern2, String value)
			throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
		String[] setValue = value.split("=");
		String pattern3 = pattern2;
		if (setValue.length == 2) {
			pattern3 = pattern2.replace(setValue[0], setValue[1]);
		}

		Date date = new Date();
		if (StringUtils.equalsIgnoreCase("cn", timeZone)) {
			Date cnDate = new Date(date.getTime() + 8 * 60 * 60 * 1000);
			date = sdf2.parse(DateUtils.format(cnDate, pattern3));
		} else if (StringUtils.equalsIgnoreCase("utc", timeZone)) {
			date = sdf2.parse(DateUtils.format(date, pattern3));
		}
		return compareDate(sdf1.parse(dateStr1), date);
	}
	
	public boolean compareDate(String dateStr1, String pattern1, Date date) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
		return compareDate(sdf1.parse(dateStr1), date);
	}
	
	public boolean compareDate(String dateStr1, String pattern1, String dateStr2, String pattern2)
			throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
		return compareDate(sdf1.parse(dateStr1), sdf2.parse(dateStr2));
	}

	public boolean compareDate(String dateStr1, String dateStr2, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return compareDate(sdf.parse(dateStr1), sdf.parse(dateStr2));
	}
	
	/**
	 * 比较两个date的前后 如果date1大于date2返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public boolean compareDate(Date date1, Date date2) {
		return date2.before(date1);
	}

}
