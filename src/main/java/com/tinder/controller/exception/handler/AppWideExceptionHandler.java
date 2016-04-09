package com.tinder.controller.exception.handler;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class AppWideExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String noHandlerFound(Model model,
			NoHandlerFoundException exception) {
		addExceptionInfoToViewModel(model, exception,
				HttpStatus.SC_NOT_FOUND,
				IExcepionInfo.NOT_FOUND_MESSAGE);
		return "error";
	}

	
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public String global(Model model,
			Exception exception) throws Exception {
		if (AnnotationUtils.findAnnotation(exception.getClass(),
				ResponseStatus.class) != null)
            throw exception;
		
		
		addExceptionInfoToViewModel(model, exception,
				HttpStatus.SC_INTERNAL_SERVER_ERROR,
				IExcepionInfo.INTERNAL_SERVER_ERROR_MESSAGE);
		return "error";
	}

	private void addExceptionInfoToViewModel(Model model, Exception exception,
			int statusCode,String message) {
		model.addAttribute(IExcepionViewParam.STATUS, statusCode);
		model.addAttribute(IExcepionViewParam.MESSAGE, message);
		model.addAttribute(IExcepionViewParam.EXCEPTION,exception);
	}
}
