package com.tinder.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tinder.model.dao.user.IUserDAO;

@Component
public class UserLoader {

	@Autowired
	private IUserDAO userDAO;
	
	public void loadUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session == null || session.getAttribute("user") == null) {
			session.setAttribute("user", 
					userDAO.getUser(request.getUserPrincipal().getName()));
		}
	}
}