package com.tinder.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.exceptions.UnauthorizedException;
import com.tinder.model.dao.MessageDAO;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.Message;

@RestController
public class MessagesService{
	
	@RequestMapping(value="/MessagesService",method=RequestMethod.GET)
	public List<Message> doPost(HttpServletRequest request){
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				throw new UnauthorizedException("The user is not logged in.");
			}
			return MessageDAO.getLastMessagesFrom(
					Integer.parseInt(request.getParameter("nummessages")),
					UserDAO.getUser(request.getParameter("username1")),
					UserDAO.getUser(request.getParameter("username2")),
					LocalDateTime.parse(request.getParameter("fromtime")));
		} catch (UnauthorizedException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
