package com.tinder.chat.endpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tinder.model.pojo.User;


@ServerEndpoint(value = "/chat/{username}", configurator = ChatroomServerConfigurator.class)
public class ChatEndPoint {
	public static Map<String,Session> sessions = Collections.synchronizedMap(new HashMap<String,Session>());
	private User user;
	@OnOpen
	public void onOpen(EndpointConfig config, Session session,@PathParam("username") String username) {
		user = ((User)config.getUserProperties().get("user"));
		System.out.println(user.getUsername());
		sessions.put(user.getUsername(),session);
	}

	@OnMessage
	public void onMessage(String message, @PathParam("username") String username) {
		System.out.println(message + "za " + username);
		String sender = user.getUsername();
		Session toSend = sessions.get(username);
		if(toSend!=null){
			try {
				toSend.getBasicRemote().sendText(sender+ " :" + message + "/n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//zapisvame v bazata
	}

	@OnClose
	public void onClose(Session session) {
		String toRemove=null;
		for(String username : sessions.keySet()){
			if(sessions.get(username)==session){
				toRemove = username;
			}
		}
		if(toRemove!=null)
			sessions.remove(toRemove);
	}
	
	private String buildJSONData(String username,String message){
		JSONPObject json = new JSONPObject("message",username+": "+message);
		return json.toString();
	}
}
