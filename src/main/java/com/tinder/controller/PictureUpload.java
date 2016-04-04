package com.tinder.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.exceptions.UnauthorizedException;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.pojo.User;

@Controller
@RequestMapping(value = "/PictureUpload")
public class PictureUpload {
	
	@Autowired
	private AtomicInteger numPictures;
	
	@Autowired
	private IPictureDAO pictureDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String uploadPicture(@RequestParam("picture") MultipartFile file,
			HttpServletRequest req) throws UnauthorizedException {
		String fileName = null;
		User user = (User) req.getSession().getAttribute("user");
		if( user == null){
			throw new UnauthorizedException("Not logged in user tries to upload picture");
		}
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename();
				fileName = fileName.substring(fileName.lastIndexOf('.'));
				fileName = numPictures.incrementAndGet() + fileName;
				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(
						new FileOutputStream(new File("C:/resources/images/" + fileName)));
				buffStream.write(bytes);
				buffStream.close();
				pictureDAO.addPicture(fileName,user.getId() );
				return "redirect:/Profile";
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/Profile";
			}
		} else {
			return "redirect:/Profile";
		}
	}
}
