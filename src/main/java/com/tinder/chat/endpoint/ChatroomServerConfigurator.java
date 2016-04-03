package com.tinder.chat.endpoint;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;

import com.tinder.model.pojo.User;

public class ChatroomServerConfigurator extends ServerEndpointConfig.Configurator {
	
	public void mofifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
		sec.getUserProperties().put("user",(User)((HttpSession) request.getHttpSession()).getAttribute("user"));
	}
}  
