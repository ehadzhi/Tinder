package com.tinder.controller.picture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.info.UserParam;
import com.tinder.info.PictureParam;
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
	public String uploadPicture(@RequestParam(PictureParam.TO_UPOAD)
	MultipartFile file, HttpServletRequest req)
			throws IllegalStateException, IOException{
		User user = (User) req.getSession().getAttribute(UserParam.USER);
		if (!file.isEmpty()) {
			return saveFile(file, user);
		}
		return "redirect:/Profile?error=empty-file";
	}

	private String saveFile(MultipartFile file, User user) throws IOException {
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf('.'));
		fileName = numPictures.incrementAndGet() + fileName;
		file.transferTo(new File("C:/resources/images/" + fileName));
		pictureDAO.addPicture(fileName, user.getId());
		return "redirect:/Profile";
	}
}
