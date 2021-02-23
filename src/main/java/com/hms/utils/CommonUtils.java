package com.hms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CommonUtils {
	
	public static String getSavedFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String savedName = sdf.format(new Date());
		return savedName;
	}
	
	public static Object serviceLoader( String serviceName) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return context.getBean(serviceName);
	}
}
