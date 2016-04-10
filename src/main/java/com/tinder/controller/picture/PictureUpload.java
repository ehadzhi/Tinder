package com.tinder.controller.picture;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.info.PictureViewParam;
import com.tinder.info.UserViewParam;
import com.tinder.model.pojo.User;


@Controller
@RequestMapping(value = "/PictureUpload")
public class PictureUpload {


	@Autowired
	private FileSaver fileSaver;

	@RequestMapping(method = RequestMethod.POST)
	public String uploadPicture(@RequestParam(PictureViewParam.TO_UPOAD) MultipartFile file, HttpServletRequest req)
			throws IllegalStateException, IOException, S3ServiceException {
		User user = (User) req.getSession().getAttribute(UserViewParam.USER);
		if (!file.isEmpty()) {
			return fileSaver.saveFile(file, user);
		}
		return "redirect:/Profile?error=empty-file";
	}
	

}
