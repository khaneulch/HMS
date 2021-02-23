package com.hms.main.impl;

import java.util.List;
import java.util.Map;

public interface MainService {

	/**
	 * 메인에 보여질 컴포넌트를 불러옴
	 * @param params 
	 * @return
	 */
	List<Map<String, Object>> getMainComponent(Map<String, Object> params);
	
	/**
	 * 다운로드될 컴포넌트 목록의 파일경로를 불러옴
	 * @return
	 */
	List<Map<String, Object>> searchFilePath(Map<String, Object> params);
	
	/**
	 * 그룹명을 불러옴
	 * @return
	 */
	List<Map<String, Object>> getGroupName();
}