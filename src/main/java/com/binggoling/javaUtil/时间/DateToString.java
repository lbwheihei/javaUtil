package com.binggoling.javaUtil.时间;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString {
	
	@SuppressWarnings("unused")
	private static String formatUtil(Date d, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(d);
	}

}
