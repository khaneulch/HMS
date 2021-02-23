package com.hms.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
	List<Map<String, Object>> getMainComponent(Map<String, Object> params);
	List<Map<String, Object>> searchFilePath(Map<String, Object> params);
	List<Map<String, Object>> getGroupName();
}