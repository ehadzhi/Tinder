package com.tinder.chat.endpoint;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
public class ChatEndPoint {
	private static List<Session> sessions = new LinkedList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session.getId() + " has opened a connection");
		try {
			System.out.println(session.getQueryString());
			session.getBasicRemote().sendText("Connection Established");
			sessions.add(session);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + ": " + message);
		try {
			for (Session sess : sessions) {
				if (sess.isOpen()) {
					sess.getBasicRemote().sendText(message);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " has ended");
	}
}
