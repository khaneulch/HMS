package com.hms.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@Import({ContextDatasource.class, ContextMapper.class})
@EnableTransactionManagement
@ComponentScan(basePackages="com.hms",
				useDefaultFilters = false,
				includeFilters={
						@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Service.class)
						, @ComponentScan.Filter(type=FilterType.ANNOTATION, value=Repository.class)
				},
				excludeFilters={
						@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class)
						, @ComponentScan.Filter(type=FilterType.ANNOTATION, value=Configuration.class)
				})
@PropertySource("classpath:/config.properties")
@EnableScheduling
public class RootContext {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public LoggerAspect loggerAspect() {
		return new LoggerAspect();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
		return txManager;
    }
}
