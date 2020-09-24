package com.revature.controllers;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.dto.LoginDTO;
import com.revature.dto.RegisterDTO;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.HibernateUtility;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest req;
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody LoginDTO dto) throws LoginException {
		
		log.info("login method invoked");
		
		HttpSession session = req.getSession();
		if (userService.userLoggedIn(session)) {
			User alreadyLoggedInUser = userService.getCurrentUser(session);
			throw new LoginException(alreadyLoggedInUser.getUsername() + " is already logged into this session");
		}
		
		String username = dto.getUsername();
		String password = dto.getPassword();
		log.info("Username and password retrieved from DTO");
		
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
		
		log.info("User " + username + " 's password successfully hashed to " + hashedPassword);
		
		// Call login, which will also set currentUser to the user retrieved from the DAO
		User user = userService.login(username, hashedPassword, session);
		
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(value = "/user/current", method = RequestMethod.GET)
	public ResponseEntity<User> checkCurrentUser() {
		
		log.info("checkLoggedIn method invoked");
		
		HttpSession session = req.getSession();
		
		if (userService.userLoggedIn(session)) {
			return ResponseEntity.ok(userService.getCurrentUser(session));
		} else {
			return ResponseEntity.noContent().build();
		}
	
	}
	
	@RequestMapping(value = "/user/logout", method=RequestMethod.GET)
	public ResponseEntity<?> logout() {
		log.info("logout method invoked");
		
		HttpSession session = req.getSession();
		
		if (!userService.userLoggedIn(session)) {
			log.info("currentUser not found for session");
		} else {
			log.info(userService.getCurrentUser(session).getUsername() + " has been logged out");
			session.setAttribute("currentUser", null);
		}
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody RegisterDTO dto) throws RegistrationException, LoginException {
		log.info("register method invoked");
		
		HttpSession session = req.getSession();
		if (userService.userLoggedIn(session)) {
			throw new RegistrationException("Cannot register while " + userService.getCurrentUser(session).getUsername() + " is already logged in.");
		}
				
		String firstName = dto.getFirstName();
		String lastName = dto.getLastName();
		String username = dto.getUsername();
		String password = dto.getPassword();
		String confirmPassword = dto.getConfirmPassword();
		log.info("Registration information retrieved from DTO");
				
		if (!password.equals(confirmPassword)) {
			throw new RegistrationException("Password and confirm password do not match");
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
		
		User loggedInUser = userService.login(user.getUsername(), user.getPassword(), session);
		
		return ResponseEntity.ok(loggedInUser);
	}
		
}
