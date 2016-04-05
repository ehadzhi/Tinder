package com.tinder.controller.chat;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.tinder.config.chat.IncomingMessage;
import com.tinder.config.chat.OutgoingMessage;
import com.tinder.model.dao.chat.IChatDAO;
import com.tinder.model.dao.message.IMessageDAO;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.Message;
import com.tinder.model.pojo.User;

@Controller
public class StompController {

	private static final int INITIAL_LOAD_MESSAGE_COUNT = 5;

	private static final Logger logger = LoggerFactory.getLogger(StompController.class);

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IChatDAO chatDAO;

	@Autowired
	private IMessageDAO messageDAO;

	@Autowired
	private SimpMessageSendingOperations messageSender;

	@SubscribeMapping({ "/getInitialData" })
	public Map<String, Map<String, Object>> handleSubscription(Principal principal) {

		Map<String, Map<String,Object>> toReturn = new HashMap<String, Map<String,Object>>();
		User whoWantsIt = userDAO.getUser(principal.getName());
		List<User> users = chatDAO.getUserChatFriends(whoWantsIt);
		for (User user : users) {
			Map<String,Object> value = new HashMap<String,Object>();
			value.put("messages",
					messageDAO.getLastMessagesFrom(INITIAL_LOAD_MESSAGE_COUNT,
							whoWantsIt, user, LocalDateTime.now()));
			value.put("picture", user.getAvatarName());
			toReturn.put(user.getUsername(),value);
		}
		
		return toReturn;
	}

	@MessageMapping("/dispatcher")
	public void handleShout(IncomingMessage incoming, Principal sender) {
		logger.info("Received message: " + incoming.getMessage() + " from " + sender.getName() + " to "
				+ incoming.getReceiver());

		OutgoingMessage toSend = new OutgoingMessage();
		toSend.setMessage(incoming.getMessage());
		toSend.setSender(sender.getName());
		toSend.setTimeOfSending(LocalDateTime.now());

		User senderUser = userDAO.getUser(toSend.getSender());
		User receiverUser = userDAO.getUser(incoming.getReceiver());
		System.out.println(senderUser);
		System.out.println(receiverUser);

		messageDAO.sendMessage(incoming.getMessage(), userDAO.getUser(toSend.getSender()),
				userDAO.getUser(incoming.getReceiver()));

		messageSender.convertAndSend("/topic/" + incoming.getReceiver(), toSend);
	}

}
