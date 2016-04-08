package com.tinder.controller.initial;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserViewParam;
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
		@RequestParam(value = UserViewParam.USERNAME, required = false) String username,
		@RequestParam(value = UserViewParam.PASSWORD, required = false) String password,
		@RequestParam(value = UserViewParam.EMAIL, required = false) String email,
		@RequestParam(value = UserViewParam.GENDER, required = false) String gender,
		@RequestParam(value = UserViewParam.AGE, required = false) int age,
		@RequestParam(value = UserViewParam.FULL_NAME, required = false) String fullName) {
		
		String uuid = generateUUID();
		userDAO.registerUnconfirmedUser(username, password, email,
				UserViewParam.parseGender(gender),age , fullName, uuid);
		
		UnconfirmedUser user = userDAO.getUnconfirmedUser(uuid);
		
		sendConfirmationEmail(user.getEmail(),uuid);

		return "redirect:/login";
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
