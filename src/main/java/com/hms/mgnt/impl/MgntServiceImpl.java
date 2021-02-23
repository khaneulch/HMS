package com.hms.mgnt.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hms.mgnt.mapper.MgntMapper;
import com.hms.utils.HmsFileUtils;

@Service("mgntService")
public class MgntServiceImpl implements MgntService {
	
	@Autowired MgntMapper mapper;

	@Override
	public int insertComponent(Map<String, Object> params) {
		return mapper.insertComponent(params);
	}

	@Override
	public Map<String, Object> getComponent(Map<String, Object> params) {
		Map<String, Object> component = mapper.getComponent(params);
		if( component != null) {
			if( component.getOrDefault("compType", "X").equals("I")) {
				component.put("slider", mapper.searchSliderComponent(params));
			}
		}
		return component;
	}
	
	@Override
	public int searchComponentCnt(Map<String, Object> params) {
		return mapper.searchComponentCnt(params);
	}

	@Override
	public List<Map<String, Object>> searchComponent(Map<String, Object> params) {
		return mapper.searchComponent(params);
	}

	@Override
	public void updateComponent(Map<String, Object> params) {
		mapper.updateComponent(params);		
	}

	@Override
	public void insertMainComponent(Map<String, Object> params) {
		boolean isNew = params.getOrDefault("groupCode", "").equals("-1") ? true : false;
		mapper.insertMainComponent(params);
		if( isNew) mapper.insertHomeSetting(params);
	}

	@Override
	public void deleteMainComponent(Map<String, Object> params) {
		if( params.getOrDefault("deleteGroup", "N").equals("Y")) {
			mapper.deleteHomeSetting(params);
		}
		mapper.deleteMainComponent(params);
	}
	
	@Override
	public void updateMainComponent(Map<String, Object> params) {
		mapper.updateMainComponent(params);
	}

	@Override
	public int insertSliderComponent(Map<String, Object> params, MultipartFile[] sliderImg) {
		int seq = mapper.insertComponent(params);
		
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.putAll(params);
		
		// 이미지 업로드 및 테이블 insert 처리
		uploadSliderImg(newMap, sliderImg);
		
		return seq;
	}

	@Override
	public void updateSliderComponent(Map<String, Object> params, MultipartFile[] sliderImg) {
		mapper.updateComponent(params);
		
		String seq = params.get("seq") + "";
		String deleteFiles = params.getOrDefault("deleteFiles", "") + "";
		String files[] = deleteFiles.split(",");
		
		// 제거된 파일 삭제 및 테이블 delete
		if( files != null && files.length > 0) {
			for( String fileName : files) {
				if( !fileName.trim().equals("")) {
					Map<String, Object> fileMap = new HashMap<String, Object>();
					fileMap.put("seq", seq);
					fileMap.put("fileName", fileName);
					mapper.deleteSliderComponent(fileMap);
				}
			}
		}

		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.putAll(params);

		// 이미지 업로드 및 테이블 insert 처리
		uploadSliderImg(newMap, sliderImg);
	}
	
	private void uploadSliderImg(Map<String, Object> newMap, MultipartFile[] sliderImg) {
		String filePath = newMap.get("filePath") + "";
		
		int i = 1;
		for( MultipartFile mFile : sliderImg) {
			File file = HmsFileUtils.uploadFile(mFile, filePath);
			newMap.put("fileName", file.getName());
			newMap.put("fileOrgName", mFile.getOriginalFilename());
			newMap.put("dpSeq", i);
			mapper.insertSliderComponent(newMap);
			i ++;
		}
	}

	@Override
	public Map<String, Object> getHomeSetting(Map<String, Object> params) {
		return mapper.getHomeSetting(params);
	}

	@Override
	public void updateHomeSetting(Map<String, Object> params) {
		mapper.updateHomeSetting(params);
	}

	@Override
	public void deleteComponent(Map<String, Object> params) {
		mapper.deleteComponent(params);
		mapper.deleteMainComponent(params);
	}
}