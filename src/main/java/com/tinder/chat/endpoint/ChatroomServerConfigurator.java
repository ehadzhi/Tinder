package com.tinder.chat.endpoint;

import javax.servlet.http.*;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;

public class ChatroomServerConfigurator extends ServerEndpointConfig.Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) (request.getHttpSession());
		config.getUserProperties().put("user", httpSession.getAttribute("user"));
	}
}
