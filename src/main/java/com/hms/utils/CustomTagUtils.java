package com.hms.utils;

import java.text.SimpleDateFormat;

public class CustomTagUtils {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getFormattedDate(String date) {
		return getFormattedDate(date, "yyyy.MM.dd");
	}

	public static String getFormattedDate(String date, String format) {
		String newDateString = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			newDateString = sdf.format(dateFormat.parse(date));
		}
		catch( Exception e) {
			newDateString = date;
		}
		return newDateString;
	}

}
