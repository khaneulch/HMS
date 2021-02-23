package com.hms.main.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hms.main.impl.MainService;
import com.hms.mgnt.impl.MgntService;
import com.hms.utils.GetPropertyUtils;
import com.hms.utils.JsonView;

@Controller
public class MainController {
	
	@Autowired MainService service;
	@Autowired MgntService mgntService;
	
	private static String uploadFolder = GetPropertyUtils.getProperty("upload.folder");

	/**
	 * 메인 페이지
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public String main() throws Exception {
		return "redirect:/mgnt/list";
	}
	
	/**
	 * 메인 페이지(팝업)
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/main-pop")
	public String viewPop(Model model, @RequestParam Map<String,Object> params) throws Exception {
		model.addAttribute("home", mgntService.getHomeSetting(params));
		return "/main/main_view_pop";
	}
	
	/**
	 * 헤더 데이터를 불러온다
	 * 로고 이미지/메뉴
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getHeaderData")
	public JsonView getHeaderData(Model model, @RequestParam Map<String,Object> params) throws Exception {
		Map<String,Object> home = new HashMap<String, Object>();
		Map<String,Object> map = mgntService.getHomeSetting(params);
		if( map != null) home.putAll(map);
				
		if( home != null) {
			home.compute("logoFileName", (k, v) -> (v == null) ? "logo.png" : v);
			params.put("logo", uploadFolder + "/logo/" + home.get("logoFileName"));
		}
		params.put("list", service.getMainComponent(params));
		params.put("home", home);
		return new JsonView(true, params);
	}
	
	/**
	 * 푸터 데이터를 불러온다
	 * 푸터 데이터는 컴포넌트 테이블에 0번으로 고정
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getFooterData")
	public JsonView getFooterData(Model model, @RequestParam Map<String,Object> params) throws Exception {
		params.put("home", mgntService.getHomeSetting(params));
		return new JsonView(true, params);
	}
	
	
	/**
	 * 메인 컴포넌트 리스트를 불러온다.
	 * @param model
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getListData")
	public JsonView getListData(Model model, @RequestParam Map<String,Object> params) throws Exception {
		return new JsonView(true, service.getMainComponent(params));
	}
}
