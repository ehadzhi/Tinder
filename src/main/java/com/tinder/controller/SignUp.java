package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserParam;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.UnconfirmedUser;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/SignUp")
public class SignUp {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private MailSender mailSender;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,
		@RequestParam(value = UserParam.USERNAME, required = false) String username,
		@RequestParam(value = UserParam.PASSWORD, required = false) String password,
		@RequestParam(value = UserParam.EMAIL, required = false) String email,
		@RequestParam(value = UserParam.GENDER, required = false) String gender,
		@RequestParam(value = UserParam.AGE, required = false) int age,
		@RequestParam(value = UserParam.FULL_NAME, required = false) String fullName) {

		
		String uuid = generateUUID();
		userDAO.registerUnconfirmedUser(username, password, email,
			UserParam.parseGender(gender), age, fullName, uuid);
		
		UnconfirmedUser user = userDAO.getUnconfirmedUser(uuid);
		
		sendConfirmationEmail(user.getEmail(),uuid);
		request.setAttribute("error", "Please confirm your email!");
		
		return "forward:/login";
	}
	
	private String generateUUID(){
		return java.util.UUID.randomUUID().toString();
	}
	
	private void sendConfirmationEmail(String email,String UUID){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("it.talents.tinder@gmail.com");
		message.setTo(email);
		message.setSubject("confirm email.");
		message.setText("To confirm your email please visit: http://localhost:8080/Tinder/ConfirmEmail?UUID="+UUID);
		mailSender.send(message);
	}

}
