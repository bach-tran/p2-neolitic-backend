package com.revature.aspects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revature.models.User;

@Component
@Aspect
public class AuthorizationAspect {
	
	@Around("@annotation(com.revature.annotations.AuthorizedAdmin)")
	public Object authenticateAdmin(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("currentUser") == null) {
			response.sendError(401, "You must be logged in to access the resource");
			return null;
		} else {
			User user = (User) session.getAttribute("currentUser");
			if (!user.getRole().getUserRole().equals("admin")) {
				response.sendError(401, "Only admins can access this resource");
				return null;
			}
		}
		
		Object result = pjp.proceed(pjp.getArgs());
		return result;
	}
	
	@Around("@annotation(com.revature.annotations.AuthorizedConsumer)")
	public Object authenticateConsumer(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("currentUser") == null) {
			response.sendError(401, "You must be logged in to access the resource");
			return null;
		} 
		
		Object result = pjp.proceed(pjp.getArgs());
		return result;
	}
	
}
