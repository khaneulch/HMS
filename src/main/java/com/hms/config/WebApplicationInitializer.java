package com.hms.config;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootContext.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{ ServletContext.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[]{characterEncodingFilter};
	}
}
