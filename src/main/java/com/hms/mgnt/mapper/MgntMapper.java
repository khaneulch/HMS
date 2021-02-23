package com.hms.mgnt.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MgntMapper {

	int insertComponent(Map<String, Object> params);
	Map<String, Object> getComponent(Map<String, Object> params);
	int searchComponentCnt(Map<String, Object> params);
	List<Map<String, Object>> searchComponent(Map<String, Object> params);
	void updateComponent(Map<String, Object> params);
	void insertMainComponent(Map<String, Object> params);
	void deleteMainComponent(Map<String, Object> params);
	void updateMainComponent(Map<String, Object> params);
	void insertSliderComponent(Map<String, Object> newMap);
	List<Map<String, Object>> searchSliderComponent(Map<String, Object> params);
	void deleteSliderComponent(Map<String, Object> fileMap);
	Map<String, Object> getHomeSetting(Map<String, Object> params);
	void updateHomeSetting(Map<String, Object> params);
	void deleteComponent(Map<String, Object> params);
	void insertHomeSetting(Map<String, Object> params);
	void deleteHomeSetting(Map<String, Object> params);
}
