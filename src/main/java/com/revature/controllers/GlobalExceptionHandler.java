package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);
	
	// ====================================================================================================================
	// Exception handlers
//	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Unable to login using provided credentials")
	@ExceptionHandler(LoginException.class)
	public void loginException(Exception ex) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		response.sendError(400, ex.getMessage());
		log.error(ex.getMessage());
	}
	
//	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Unable to register using provided credentials.")
	@ExceptionHandler(RegistrationException.class)
	public void registrationException(Exception ex) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		response.sendError(400, ex.getMessage());
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
