package com.tinder.config;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Configuration
@EnableWebSecurity
@ComponentScan("com.tinder")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IUserDAO userDAO;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/js/**").permitAll().antMatchers("/css/**").permitAll()
				.antMatchers("/SignUp/**").permitAll()
				.antMatchers("/SignUpValidationService/**").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.permitAll().defaultSuccessUrl("/Home", true)
				.and().httpBasic()
				.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT username,password_hash as 'password',true"
						+ " FROM tinder.users where binary username = ?;")
				.authoritiesByUsernameQuery("select ? as 'username' ,'ROLE_USER' as 'ROLE_USER';")
				.passwordEncoder(new Md5PasswordEncoder());
	}
}
