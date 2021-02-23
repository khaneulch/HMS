package com.hms.main.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.main.mapper.MainMapper;
import com.hms.mgnt.mapper.MgntMapper;

@Service("mainService")
public class MainServiceImpl implements MainService {

	@Autowired
	MainMapper mapper;
	
	@Autowired
	MgntMapper mgntMapper;
	
	@Override
	public List<Map<String, Object>> getMainComponent(Map<String, Object> params) {
		List<Map<String, Object>> compList = mapper.getMainComponent(params);
		
		for( Map<String, Object> m : compList) {
			if( m.getOrDefault("compType", "X").equals("I")) {
				m.put("slider", mgntMapper.searchSliderComponent(m));
			}
		}
		
		return compList;
	}

	@Override
	public List<Map<String, Object>> searchFilePath(Map<String, Object> params) {
		return mapper.searchFilePath(params);
	}

	@Override
	public List<Map<String, Object>> getGroupName() {
		return mapper.getGroupName();
	}
}