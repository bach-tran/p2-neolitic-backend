package com.revature.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.controllers.UserController;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.User;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;
import com.revature.models.Role;

@Service
public class UserService {
	
	@Autowired
	private IUserDAO userDAO;
	
	private static Logger log = Logger.getLogger(UserService.class);

	public UserService() {
		super();
	}
	
	public User registerAccount(String firstName, String lastName, String username, String hashedPassword) throws RegistrationException
	{
		if (firstName.equals("") || lastName.equals("") || username.equals("") || hashedPassword.equals("")) {
			throw new RegistrationException("Registration information fields cannot be blank");
		}
		
		firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
		lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
		
		User u = new User(0, username, hashedPassword, firstName, lastName, new Role(2, "consumer"));
		
		u = userDAO.register(u);
		
		return u;
	}
	
	public User login(String username, String hashedPassword, HttpSession session) throws LoginException
	{
		User user = userDAO.login(username, hashedPassword);
		
		if (session == null) {
			throw new LoginException("Unable to login because provided session variable is null");
		} else if (user == null) {
			throw new LoginException("Credentials were unable to be matched with a user record");
		} else {
			session.setAttribute("currentUser", user);
			log.info("User " + username + " sucessfully logged in");
		}
		
		return user;
	}
	
	public boolean userLoggedIn(HttpSession session) {
		
		if (session != null) {
			User user = (User) session.getAttribute("currentUser");
			
			if (user != null) {
				log.info(user.getUsername() + " is currently logged in");
				return true;
			} else {
				log.info("No user is currently logged in");
				return false;
			}
			
		} else {
			log.info("Session provided was null");
			return false;
		}
	}
	
	public User getCurrentUser(HttpSession session) {
		if (session != null) {
			User user = (User) session.getAttribute("currentUser");
			return user;
		} else {
			return null;
		}
	}
		
	/* 
	 * The following two methods are used to hash passwords with SHA-256. 
	 * 
	 * The first generates the password hash as a byte array
	 * The second converts the password into a hex string
	 */

	public byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
    public String toHexString(byte[] hash) 
    { 
        BigInteger number = new BigInteger(1, hash);  
  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 
        
}
