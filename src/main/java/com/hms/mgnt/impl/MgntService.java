package com.hms.mgnt.impl;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface MgntService {

	/**
	 * 컴포넌트 생성
	 * @param params
	 * @return
	 */
	int insertComponent(Map<String, Object> params);

	/**
	 * 특정 컴포넌트를 불러옴
	 * @param params
	 * @return
	 */
	Map<String, Object> getComponent(Map<String, Object> params);
	
	/**
	 * 컴포넌트 리스트
	 * @param params
	 * @return
	 */
	int searchComponentCnt(Map<String, Object> params);
	List<Map<String, Object>> searchComponent(Map<String, Object> params);

	/**
	 * 컴포넌트 수정
	 * @param params
	 */
	void updateComponent(Map<String, Object> params);

	/**
	 * 메인 컴포넌트 등록
	 * @param params
	 */
	void insertMainComponent(Map<String, Object> params);

	/**
	 * 메인 컴포넌트 삭제
	 * @param params
	 */
	void deleteMainComponent(Map<String, Object> params);
	
	/**
	 * 메인 컴포넌트 순서변경
	 * @param params
	 */
	void updateMainComponent(Map<String, Object> params);

	/**
	 * 슬라이더 컴포넌트 생성
	 * @param params
	 */
	int insertSliderComponent(Map<String, Object> params, MultipartFile[] sliderImg);

	/**
	 * 슬라이더 컴포넌트 수정
	 * @param params
	 */
	void updateSliderComponent(Map<String, Object> params, MultipartFile[] sliderImg);

	/**
	 * 홈기본 설정을 불러옴
	 * @param params
	 */
	Map<String, Object> getHomeSetting(Map<String, Object> params);
	
	/**
	 * 홈기본 설정 수정
	 * @param params
	 */
	void updateHomeSetting(Map<String, Object> params);

	/**
	 * 컴포넌트 삭제
	 * @param params
	 */
	void deleteComponent(Map<String, Object> params);


}
