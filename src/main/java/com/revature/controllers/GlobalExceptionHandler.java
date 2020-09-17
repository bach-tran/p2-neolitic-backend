package com.revature.controllers;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.revature.exceptions.LoginException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);
	
	// ====================================================================================================================
	// Exception handlers
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Username and/or password not properly provided in JSON format")
	@ExceptionHandler(LoginException.class)
	public void loginException(Exception ex) {
		log.error(ex.getMessage());
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void allOtherExceptions(Exception ex) throws Exception {
		log.error(ex.getMessage());
		log.error("Unhandled exception encountered");
		
		// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
		// If the exception is annotated with @ResponseStatus rethrow it and let
	    // the framework handle it - like the OrderNotFoundException example
	    // at the start of this post.
	    // AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation
                (ex.getClass(), ResponseStatus.class) != null)
			throw ex;
	}
}
