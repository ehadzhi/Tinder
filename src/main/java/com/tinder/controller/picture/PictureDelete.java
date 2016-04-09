package com.tinder.controller.picture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tinder.info.PictureViewParam;
import com.tinder.info.UserViewParam;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/PictureDelete/{picture_name:[0-9a-zA-Z.]+}")
public class PictureDelete {

	@Autowired
	private IPictureDAO pictureDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String deletePicture(@PathVariable(PictureViewParam.TO_DELETE)
	String pictureName, HttpServletRequest request) {
		
		User owner = (User) request.getSession().getAttribute(UserViewParam.USER);
		
		if(owner.getAvatarName().equals(pictureName)){
			owner.setAvatarName(UserViewParam.DEFAULT_AVATAR);
		}
		
		pictureDAO.deletePicture(pictureName, owner);
		return "redirect:/Profile";
	}

}
