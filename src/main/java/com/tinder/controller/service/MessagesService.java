package com.tinder.controller.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.chat.Message;

@RestController
public class MessagesService{
	
	@Autowired
	private IMessageDAO messageDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	@RequestMapping(value="/MessagesService",method=RequestMethod.GET)
	public List<Message> doPost(HttpServletRequest request){

		return messageDAO.getLastMessagesFrom(
			Integer.parseInt(request.getParameter("nummessages")),
			userDAO.getUser(request.getParameter("username1")),
			userDAO.getUser(request.getParameter("username2")),
			LocalDateTime.parse(request.getParameter("fromtime")));
	}

}
