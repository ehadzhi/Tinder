package com.tinder.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.user.UserDAO;
import com.tinder.model.pojo.User;

@RestController
public class UserPhotosService {

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if(request.getSession(false)==null){
//			response.sendRedirect("./Home");
//		}
//		try {
//			response.setContentType("application/json");
//			JSONObject json = new JSONObject();
//			json.put("photos", UserDAO.getAllPhotosOfUser(((User)request.getSession(false).getAttribute("user")).getUsername()));
//			System.out.println(json);
//			PrintWriter pw = response.getWriter();
//			pw.print(json);
//			pw.flush();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
//	}

}
