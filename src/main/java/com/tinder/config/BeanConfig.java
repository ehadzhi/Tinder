package com.tinder.config;

import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tinder.model.dao.picture.IPictureDAO;

@Configuration
@ComponentScan("com.tinder")
public class BeanConfig {

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306" + "?autoReconnect=true&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setInitialSize(5);
		dataSource.setMaxTotal(10);
		return dataSource;
	}

	@Bean(name = "namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Bean(name = "numPictures")
	public AtomicInteger numPictures(IPictureDAO pictureDAO) {
		return new AtomicInteger(pictureDAO.getLastPhotoId());
	}
}
