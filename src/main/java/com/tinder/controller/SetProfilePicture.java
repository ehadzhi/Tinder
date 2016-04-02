package com.tinder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.exceptions.DBException;
import com.tinder.model.dao.PictureDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/SetProfilePicture/{picture_name:[0-9a-zA-Z.]+}")
public class SetProfilePicture {
	
	@RequestMapping(method = RequestMethod.POST)
	public String deletePicture(@PathVariable("picture_name") String pictureName,
			HttpServletRequest request){
		System.out.println(pictureName);
		User toSet = (User) request.getSession().getAttribute("user");
		toSet.setAvatarName(pictureName);
		try {
			PictureDAO.setProfilePicture(pictureName, toSet);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/Profile";
	}

}
