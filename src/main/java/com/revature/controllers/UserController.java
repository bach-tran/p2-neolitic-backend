package com.revature.controllers;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.revature.dto.LoginDTO;
import com.revature.dto.RegisterDTO;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.User;
import com.revature.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public @ResponseBody User login(@RequestBody LoginDTO dto, HttpServletRequest req) throws LoginException {
		
		log.info("login method invoked");
		
		// username, password
		String username = dto.getUsername();
		String password = dto.getPassword();
		log.info("username and password retrieved from DTO");
		
		if (username.equals("") || username == null || password.equals("") || password == null) {
			throw new LoginException("Username and/or Password was not provided or unable to be retrieved");
		}
		
		byte[] hashedArray;
		String hashedPassword;
		try {
			hashedArray = userService.getSHA(password);
			hashedPassword = userService.toHexString(hashedArray);
		} catch (NoSuchAlgorithmException e) {
			log.error("Unable to get hashed password byte array");
			throw new LoginException(e);
		}
		
		log.info("user " + username + " 's password successfully hashed to " + hashedPassword);
		
		User user = userService.login(username, hashedPassword);
		
		log.info("user " + username + " sucessfully logged in");
		
		// Check if session already has currentUser
		HttpSession session = req.getSession(false);
		session.setAttribute("currentUser", user);
		
		return user;
	}
	
	@RequestMapping(value = "/user/current", method = RequestMethod.POST)
	public @ResponseBody User checkLoggedIn(HttpServletRequest req) {
		HttpSession session = req.getSession();
		
		User currentUser = (User) session.getAttribute("currentUser");
		return currentUser;
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestBody RegisterDTO dto) throws RegistrationException {
		
		log.info("register method invoked");
		
		String firstName = dto.getFirstName();
		String lastName = dto.getLastName();
		String username = dto.getUsername();
		String password = dto.getPassword();
		String confirmPassword = dto.getConfirmPassword();
		log.info("Registration information retrieved from DTO");
		
		if (firstName.equals("") || firstName == null || lastName.equals("") || lastName == null
			|| username.equals("") || username == null || password.equals("") || password == null 
			|| confirmPassword.equals("") || confirmPassword == null) {
			throw new RegistrationException("Not all registration information was provided or was unable to be retrieved");
		}
		
		byte[] hashedArray;
		String hashedPassword;
		try {
			hashedArray = userService.getSHA(password);
			hashedPassword = userService.toHexString(hashedArray);
		} catch (NoSuchAlgorithmException e) {
			log.error("Unable to get hashed password byte array");
			throw new RegistrationException(e);
		}
		
		User user = userService.registerAccount(firstName, lastName, username, hashedPassword);
		
		return null;
	}
		
}
