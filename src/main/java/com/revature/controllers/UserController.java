package com.revature.controllers;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.revature.dto.LoginDTO;
import com.revature.exceptions.LoginException;
import com.revature.models.User;
import com.revature.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public User login(@RequestBody LoginDTO dto) throws LoginException {
		
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
		
		return null;
	}
		
}
