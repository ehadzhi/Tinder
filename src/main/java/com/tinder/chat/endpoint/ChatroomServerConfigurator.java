package com.tinder.chat.endpoint;
import javax.servlet.http.*;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;

import org.springframework.http.HttpRequest;

import com.tinder.model.pojo.User;

public class ChatroomServerConfigurator extends ServerEndpointConfig.Configurator {
	
	public void mofifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response){
		HttpSession httpSession = (HttpSession)(request.getHttpSession());
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
	}
}  
