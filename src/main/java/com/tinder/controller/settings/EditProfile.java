package com.tinder.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tinder.info.UserParam;

@Controller
@RequestMapping("/EditProfile")
public class EditProfile {

	@RequestMapping(method = RequestMethod.POST)
	public String doGet(
		@RequestParam(value=UserParam.EMAIL,required = false)String newEmail,
		@RequestParam(value=UserParam.AGE,required = false)String newAge,
		@RequestParam(value=UserParam.USERNAME,required = false)String newUsername,
		@RequestParam(value=UserParam.PASSWORD,required = false)String newPassword,
		@RequestParam(value=UserParam.DESCRIPTION,required = false)String newDescription
		) {

		if (newEmail != null) {
			
		}
		if (newAge != null) {
			
		}
		if (newUsername != null) {
			
		}
		if (newPassword != null) {
			
		}
		if (newDescription != null) {
			
		}

		return "redirect:/Profile";
	}

}
