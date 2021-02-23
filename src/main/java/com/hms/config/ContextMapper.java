package com.hms.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.hms.utils.CamelListMap;

@Configuration
@Lazy
@MapperScan(basePackages = "com.hms.**.mapper")
public class ContextMapper {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources("classpath:/config/mapper/*_mapper.xml");
		
		Properties properties = new Properties();
		properties.setProperty("mapUnderscoreToCamelCase", "true");
		properties.setProperty("lazyLoadingEnabled", "false");
		properties.setProperty("jdbcTypeForNull", "NULL");
		properties.setProperty("callSettersOnNulls", "true");
		sqlSessionFactoryBean.setConfigurationProperties(properties);
		
		sqlSessionFactoryBean.setMapperLocations(resources);
		sqlSessionFactoryBean.setTypeAliases(CamelListMap.class);

		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean SqlSessionTemplate sqlSession( SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate( sqlSessionFactory);
	}
	
	
}
