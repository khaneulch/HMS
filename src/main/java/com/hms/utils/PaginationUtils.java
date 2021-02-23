package com.hms.utils;

import java.util.Map;
import org.springframework.ui.Model;

public class PaginationUtils {
	public static void setPage(Object objTotalCount, Map<String, Object> param, Model model) {
		int totalCount = (int)objTotalCount;
		setPage(totalCount, param, model);
	}

	public static void setPage(int totalCount, Map<String, Object> param, Model model) {
		Pagination pagination = Pagination.getInstance(param, totalCount);
		param.put("pagination", pagination);
		model.addAttribute("param", param); 
		model.addAttribute("pagination", pagination);
	}
}