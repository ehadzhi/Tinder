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
@RequestMapping(value = "/PictureDelete/{picture_name:[0-9a-zA-Z.]+}")
public class PictureDelete {
	
	@RequestMapping(method = RequestMethod.POST)
	public String deletePicture(@PathVariable("picture_name") String pictureName,
			HttpServletRequest request){
		User owner = (User) request.getSession().getAttribute("user");
		if(pictureName.equals(owner.getAvatarName())){
			owner.setAvatarName("avatar_default.jpg");
		}
		try {
			PictureDAO.deletePicture(pictureName, owner);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/Profile";
	}

}
