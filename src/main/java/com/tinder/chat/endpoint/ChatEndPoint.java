package com.tinder.chat.endpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

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
	
	@OnOpen
	public void onOpen(EndpointConfig config, Session session) {
		sessions.put(((User)config.getUserProperties().get("user")).getUsername(),session);
	}

	@OnMessage
	public void onMessage(String message, @PathParam("username") String username,EndpointConfig config) {
		String sender = ((User)config.getUserProperties().get("user")).getUsername();
		Session toSend = sessions.get(username);
		if(toSend!=null){
			try {
				toSend.getBasicRemote().sendText(buildJSONData(sender,message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//zapisvame v bazata
	}

	@OnClose
	public void onClose(EndpointConfig config) {
		String username = ((User)config.getUserProperties().get("user")).getUsername();
		if(sessions.get(username)!=null)
			sessions.remove(username);
	}
	
	private String buildJSONData(String username,String message){
		JSONPObject json = new JSONPObject("message",username+": "+message);
		return json.toString();
	}
}
