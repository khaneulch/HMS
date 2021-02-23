package com.hms.mgnt.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hms.main.impl.MainService;
import com.hms.mgnt.impl.MgntService;
import com.hms.utils.CommonUtils;
import com.hms.utils.CustomTagUtils;
import com.hms.utils.HmsFileUtils;
import com.hms.utils.JsonView;
import com.hms.utils.PaginationUtils;

/**
 * 관리자 화면
 */
@Controller
@RequestMapping("/mgnt")
public class MgntController {
	private static final Logger logger = LoggerFactory.getLogger(MgntController.class);
	
	@Autowired MgntService service;
	@Autowired MainService mainService;

	/**
	 * 컴포넌트 리스트
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(Model model, @RequestParam Map<String,Object> params) throws Exception {
		PaginationUtils.setPage(service.searchComponentCnt(params), params, model);
		model.addAttribute("list", service.searchComponent(params));
		model.addAttribute("home", service.getHomeSetting(params));
		model.addAttribute("group", mainService.getGroupName());
		return "/mgnt/mgnt_list";
	}
	
	/**
	 * 컴포넌트 생성 양식
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form")
	public String form(Model model, @RequestParam Map<String,Object> params) throws Exception {
		
		if( !params.getOrDefault("seq", "").equals("")) {
			model.addAttribute("component", service.getComponent(params));
		} else {
			model.addAttribute("filePath", CommonUtils.getSavedFileName());
		}
		return "/mgnt/mgnt_form";
	}
	
	/**
	 * 컴포넌트 상세
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public String view(Model model, @RequestParam Map<String,Object> params) throws Exception {
		model.addAttribute("component", service.getComponent(params));
		return "/mgnt/mgnt_view";
	}
	
	/**
	 * 컴포넌트 생성/수정
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model
			, @RequestParam Map<String, Object> params
			, @RequestParam(value="bgImg", required = false) MultipartFile bgImg) throws Exception {
		int seq = Integer.parseInt(params.getOrDefault("seq", "-1") + "");
		params.compute("menuYn", (k, v) -> (v == null) ? "N" : v);
		
		if( bgImg != null && !bgImg.isEmpty()) {
			File file = HmsFileUtils.uploadFile(bgImg, params.get("filePath") + "");
			params.put("bgImg", file.getName());
			
			if( !params.getOrDefault("oldBgImg", "").equals("")) {
				HmsFileUtils.deleteFile(params.get("oldBgImg") + "", params.get("filePath") + "");
			}
		}
		
		if( seq == -1) {
			// 컴포넌트 생성
			seq = service.insertComponent(params);
		} else {
			// 컴포넌트 수정
			service.updateComponent(params);
		}
		return "redirect:/mgnt/view?seq=" + params.get("seq");
	}
	
	/**
	 * 컴포넌트 생성/수정
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert-slider")
	public String insertSlider(Model model
			, @RequestParam Map<String, Object> params
			, @RequestParam(value="sliderImg") MultipartFile[] sliderImg
			, @RequestParam(value="bgImg", required = false) MultipartFile bgImg) throws Exception {
		int seq = Integer.parseInt(params.getOrDefault("seq", "-1") + "");
		params.compute("menuYn", (k, v) -> (v == null) ? "N" : v);
		
		if( bgImg != null && !bgImg.isEmpty()) {
			File file = HmsFileUtils.uploadFile(bgImg, params.get("filePath") + "");
			params.put("bgImg", file.getName());
			
			if( !params.getOrDefault("oldBgImg", "").equals("")) {
				HmsFileUtils.deleteFile(params.get("oldBgImg") + "", params.get("filePath") + "");
			}
		}
		
		if( seq == -1) {
			// 컴포넌트 생성
			seq = service.insertSliderComponent(params, sliderImg);
		} else {
			// 컴포넌트 수정
			service.updateSliderComponent(params, sliderImg);
		}
		return "redirect:/mgnt/view?seq=" + params.get("seq");
	}

	/**
	 * 컴포넌트를 삭제
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST )
	public JsonView delete(Model model
			, @RequestParam Map<String, Object> params) throws Exception {
		service.deleteComponent(params);
		return new JsonView(true);
	}
	
	/**
	 * 로고 이미지 업로드
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/logo-upload", method=RequestMethod.POST )
	public JsonView logoUpload(Model model
			, @RequestParam Map<String, Object> params
			, @RequestParam(value="logoImage") MultipartFile logoImage) throws Exception {
		File logo = HmsFileUtils.uploadFile(logoImage, "logo");
		params.put("logoFileName", logo.getName());
		service.updateHomeSetting(params);
		return new JsonView(true);
	}
	
	/**
	 * 메인에 컴포넌트를 등록
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/add-main", method=RequestMethod.POST )
	public JsonView addMain(Model model
			, @RequestParam Map<String, Object> params) throws Exception {
		String seqs = params.getOrDefault("seqs", "") + ""; 
		if( seqs != null && !seqs.equals("")) {
			String seq[] = seqs.split(",");
			params.put("seq", seq);
			service.insertMainComponent(params);
		}
		
		return new JsonView(true);
	}
	
	/**
	 * 메인에 컴포넌트를 삭제
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delete-main", method=RequestMethod.POST )
	public JsonView deleteMain(Model model
			, @RequestParam Map<String, Object> params) throws Exception {
		service.deleteMainComponent(params);
		return new JsonView(true);
	}
	
	/**
	 * 메인에 컴포넌트를 순서변경
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/update-main", method=RequestMethod.POST )
	public JsonView updateMain(Model model
			, @RequestParam Map<String, Object> params) throws Exception {
		service.updateMainComponent(params);
		return new JsonView(true);
	}
	
	/**
	 * 메인에 등록된 컴포넌트를 zip파일로 생성 
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/make-home")
	public JsonView makeHome(Model model
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> params) throws Exception {
		String zipFileName = "";
		try {
			zipFileName = HmsFileUtils.downloadZip(request, response, params);
		} catch( Exception e) {
			e.printStackTrace();
			return new JsonView(false);
		}
		return new JsonView(zipFileName);
	}
	
	/**
	 * 메인에 컴포넌트를 순서변경
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/download-home")
	public void downloadHome(Model model
			, HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam Map<String, Object> params) throws Exception {
		String zipFileName = params.getOrDefault("name", "") + "";
		HmsFileUtils.zipDownload(zipFileName, response);
	}
	
	/**
	 * 기본 설정 변경
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/update-setting", method=RequestMethod.POST )
	public JsonView updateSetting(Model model
			, @RequestParam Map<String, Object> params) throws Exception {
		service.updateHomeSetting(params);
		return new JsonView(true);
	}
	
	/**
	 * 푸터 수정 페이지
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/form-footer")
	public String formFooter(Model model, @RequestParam Map<String,Object> params) throws Exception {
		model.addAttribute("home", service.getHomeSetting(params));
		return "/mgnt/mgnt_form_footer";
	}

	/**
	 * 푸터 생성/수정
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert-footer")
	public String insertFooter(Model model, @RequestParam Map<String,Object> params) throws Exception {
		service.updateHomeSetting(params);
		return "redirect:/mgnt/list?groupCode=" + params.get("groupCode");
	}
}
