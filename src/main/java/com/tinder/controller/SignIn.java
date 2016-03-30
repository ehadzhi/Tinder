package com.tinder.controller;

import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.UserDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/SignIn")
public class SignIn {

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,
			HttpServletResponse response,Model model){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		try {
			boolean isExisting = UserDAO.isUserAndPassExisting(username, password);
			if (isExisting) {
				HttpSession session = request.getSession();
				UserDAO.setLocation(username, Double.parseDouble(latitude), Double.parseDouble(longitude));
				User user = (User) UserDAO.getUser(username);
				session.setAttribute("user", user);
				session.setAttribute("userCandidates", new LinkedList<User>());
				System.out.println("TUK SAM");
				return "redirect:/Home";
			} else {
				throw new ServletException("Ivalid username or password! :" + username + " " + password);
			}

		} catch (DBException e) {
			e.printStackTrace();
			model.addAttribute("errorMassage", e.getMessage());
			return "error";
		} catch (ServletException e) {
			e.printStackTrace();
			request.setAttribute("errorMassage", e.getMessage());
			return "login";
		}
	}

}
