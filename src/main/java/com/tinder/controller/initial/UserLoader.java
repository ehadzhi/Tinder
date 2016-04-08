package com.tinder.controller.initial;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tinder.info.UserViewParam;
import com.tinder.model.dao.user.IUserDAO;
import com.tinder.model.pojo.User;

@Component
public class UserLoader {

	@Autowired
	private IUserDAO userDAO;
	
	public void loadUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(UserViewParam.USER) == null) {
			session.setAttribute(UserViewParam.USER, 
					userDAO.getUser(request.getUserPrincipal().getName()));
			session.setAttribute(UserViewParam.USER_CANDIDATES, new LinkedList<User>());
		}
	}
}
