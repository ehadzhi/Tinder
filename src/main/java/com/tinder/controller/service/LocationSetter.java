package com.tinder.controller.service;

import java.security.Principal;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.model.dao.user.IUserDAO;

@Controller
@RequestMapping(value = "/LocationSetter")
public class LocationSetter {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private MailSender mailSender;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) throws InterruptedException {
		return "set-location";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(
			@RequestParam("latitude")double latitude,
			@RequestParam("longitude")double longitude,
			Principal prinacipal) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("it.talents.tinder@gmail.com");
		message.setTo("erol.hadzhi@gmail.com");
		message.setSubject("New spittle from ");
		message.setText(" says: " );
		mailSender.send(message);

	   
				userDAO.setLocation(prinacipal.getName(), latitude, longitude);
		return "redirect:/Home";
	}
}
