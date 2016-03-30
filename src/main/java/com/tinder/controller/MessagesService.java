package com.tinder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.exceptions.UnauthorizedException;
import com.tinder.model.dao.MessageDAO;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.Message;

@RestController
public class MessagesService{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			HttpSession session = request.getSession(false);
			if (session == null) {
				throw new UnauthorizedException("The user is not logged in.");
			}
			List<Message> toReturn = MessageDAO.getLastMessagesFrom(
					Integer.parseInt(request.getParameter("nummessages")),
					UserDAO.getUser(request.getParameter("username1")),
					UserDAO.getUser(request.getParameter("username2")),
					LocalDateTime.parse(request.getParameter("fromtime")));

			//json.put("messages", toReturn);

		} catch (UnauthorizedException e) {
			e.printStackTrace();
			response.sendRedirect("./Home");
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

}
