package com.tinder.controller.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<Message> doPost(@RequestParam("nummessages")int numMessages,
			@RequestParam("username")String username,
			@RequestParam("fromtime") String fromTime,
			Principal principal) throws JSONException{
		
		LocalDateTime time = stringToLocalDateTime(fromTime);

		return messageDAO.getLastMessagesFrom(
			numMessages, userDAO.getUser(principal.getName()),
			userDAO.getUser(username), time);
	}

	private LocalDateTime stringToLocalDateTime(String fromTime) throws JSONException {
		JSONObject jsontime = new JSONObject(fromTime);
		LocalDateTime time = LocalDateTime.of(2016, 4, jsontime.getInt("dayOfMonth"),
				jsontime.getInt("hour"), jsontime.getInt("minute"), 
				jsontime.getInt("second"), jsontime.getInt("nano"));
		return time;
	}

}
