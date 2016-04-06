package com.tinder.controller.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class AppWideExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String testHandler(Model model) {
		return "error";
	}

}
