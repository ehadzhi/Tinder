package com.tinder.controller.chat;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.tinder.model.dao.chat.IChatDAO;
import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;
import com.tinder.model.pojo.chat.IncomingMessage;
import com.tinder.model.pojo.chat.OutgoingMessage;

@Controller
public class StompController {

	private static final int INITIAL_LOAD_MESSAGE_COUNT = 20;

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IChatDAO chatDAO;

	@Autowired
	private IMessageDAO messageDAO;

	@Autowired
	private SimpMessageSendingOperations messageSender;

	@SubscribeMapping({ "/getInitialData" })
	public Map<String, Map<String, Object>> sendInitialData(Principal principal) {

		User whoWantsIt = userDAO.getUser(principal.getName());
		Map<String, Map<String,Object>> toReturn = new HashMap<String, Map<String,Object>>();
		
		List<User> users = chatDAO.getUserChatFriends(whoWantsIt);
		putAllMessagesFromUserFriends(whoWantsIt, toReturn, users);
		
		return toReturn;
	}


	@MessageMapping("/dispatcher")
	public void handleMessage(IncomingMessage incoming, Principal sender) {

		OutgoingMessage toSend = prepareOutgoingMessage(incoming, sender);

		messageDAO.sendMessage(incoming.getMessage(), userDAO.getUser(toSend.getSender()),
				userDAO.getUser(incoming.getReceiver()));

		messageSender.convertAndSend("/topic/" + incoming.getReceiver(), toSend);
	}


	private OutgoingMessage prepareOutgoingMessage(IncomingMessage incoming, Principal sender) {
		OutgoingMessage toSend = new OutgoingMessage();
		toSend.setMessage(incoming.getMessage());
		toSend.setSender(sender.getName());
		toSend.setTimeOfSending(LocalDateTime.now());
		return toSend;
	}

	private void putAllMessagesFromUserFriends(User whoWantsIt, Map<String, Map<String, Object>> toReturn,
			List<User> users) {
		for (User user : users) {
			Map<String,Object> value = new HashMap<String,Object>();
			value.put("messages",
					messageDAO.getLastMessagesFrom(INITIAL_LOAD_MESSAGE_COUNT,
							whoWantsIt, user, LocalDateTime.now()));
			value.put("picture", user.getAvatarName());
			toReturn.put(user.getUsername(),value);
		}
	}
}
