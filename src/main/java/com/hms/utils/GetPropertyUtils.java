package com.hms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

/**
 * 프로퍼티를 읽어오는 클래스
 */
@Repository
public class GetPropertyUtils {
	static Environment environment;
	
	@Autowired
	public void setEnvironment(Environment env) {
		environment = env;
	}
	public static String getProperty(String key) {
		return environment.getProperty(key);
	}
}