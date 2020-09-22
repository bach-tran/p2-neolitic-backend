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

import com.revature.exceptions.AddCommentException;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.CommunityDoesNotExist;
import com.revature.exceptions.CommunityException;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.exceptions.RegistrationException;
import com.revature.util.HibernateUtility;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);
	
	// ====================================================================================================================
	// Exception handlers
	
	@ExceptionHandler({CommunityException.class, RegistrationException.class, LoginException.class, PostException.class, AddPostException.class, GetImageException.class, AddCommentException.class})
	public void badRequestException(Exception ex) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		if (response != null) {
			response.sendError(400, ex.getMessage());
		}
		
		log.error(ex.getMessage());
		
		HibernateUtility.closeSession();
	}
	
	@ExceptionHandler({PostDoesNotExist.class, CommunityDoesNotExist.class})
	public void notFoundException(Exception ex) throws IOException {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		if (response != null) {
			response.sendError(404, ex.getMessage());
		}
		
		log.error(ex.getMessage());
		
		HibernateUtility.closeSession();
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
		
		HibernateUtility.closeSession();
	}
}
