package com.tinder.config.persistance;

import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.tinder.info.DBCredentials;
import com.tinder.model.dao.picture.IPictureDAO;

@Configuration
@ComponentScan("com.tinder")
public class PersistanceConfig {

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DBCredentials.JDBC_DRIVER);
		dataSource.setUrl(DBCredentials.DB_ADDRES);
		dataSource.setUsername(DBCredentials.DB_USER);
		dataSource.setPassword(DBCredentials.DB_PASS);
		dataSource.setInitialSize(DBCredentials.INITIAL_POOL_SIZE);
		dataSource.setMaxTotal(DBCredentials.MAX_POOL_SIZE);
		return dataSource;
	}

	@Bean(name = "namedParameterJdbcTemplate")
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	 @Bean
	   public PlatformTransactionManager transactionManager(){
		 DataSourceTransactionManager transactionManager = 
				 new DataSourceTransactionManager(dataSource());
	      return transactionManager;
	   }

	@Bean(name = "numPictures")
	public AtomicInteger numPictures(IPictureDAO pictureDAO) {
		return new AtomicInteger(pictureDAO.getLastPhotoId());
	}
}
