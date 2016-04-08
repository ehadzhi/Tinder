package com.tinder.config;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.tinder.info.DBCredentials;
import com.tinder.model.dao.picture.IPictureDAO;

@Configuration
@ComponentScan("com.tinder")
public class BeanConfig {

	@Bean
	public MailSender mailSender(Environment env) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("it.talents.tinder@gmail.com");
		mailSender.setPassword("radiator");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}

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

	@Bean(name = "numPictures")
	public AtomicInteger numPictures(IPictureDAO pictureDAO) {
		return new AtomicInteger(pictureDAO.getLastPhotoId());
	}
}
