package com.tinder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.tinder.config.chat.Shout;
import com.tinder.model.dao.user.IUserDAO;

@Controller
public class StompController {

	private static final Logger logger = LoggerFactory.getLogger(StompController.class);
	
	@Autowired
	private IUserDAO userDAO;

	@MessageMapping("/marco")
	public void handleShout(Shout incoming) {
		logger.info("Received message: " + incoming.getMessage() + " ei " 
				+ userDAO.getUser("pass"));
	}
	
	@SubscribeMapping({"/marco"})
	public Shout handleSubscription() {
	Shout outgoing = new Shout();
	outgoing.setMessage("Polo!");
	return outgoing;
	}
}
