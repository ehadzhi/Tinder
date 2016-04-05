package com.tinder.controller.picture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/PictureDelete/{picture_name:[0-9a-zA-Z.]+}")
public class PictureDelete {

	@Autowired
	private IPictureDAO pictureDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String deletePicture(@PathVariable("picture_name")
	String pictureName, HttpServletRequest request) {
		User owner = (User) request.getSession().getAttribute("user");
		
		if(owner.getAvatarName().equals(pictureName)){
			owner.setAvatarName("avatar_default.jpg");
		}
		
		pictureDAO.deletePicture(pictureName, owner);
		return "redirect:/Profile";
	}

}
