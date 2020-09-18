package com.revature.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public UserService() {
		super();
	}
	
	public User registerAccount(String firstName, String lastName, String username, String hashedPassword) throws RegistrationException
	{
		User u = new User(0, username, hashedPassword, firstName, lastName, new Role(2, "consumer"));
		
		userDAO.register(u);
		
		return u;
	}
	
	public User login(String username, String hashedPassword) throws LoginException
	{
		User user = userDAO.login(username, hashedPassword);
		
		return user;
	}
	
//	public boolean createPost(byte[] image, String caption, Community community)
//	{
//		return false;
//	}
//	
//	public List<Post> viewCommunityPosts(Community c)
//	{
//		return null;
//	}
//	
//	public boolean commentOnPost(Post post, User u, String comment)
//	{
//		return false;
//	}
//	
//	/*
//	 * The following methods are restricted to Administrators
//	 */
//	
//	public boolean removePost(Post post)
//	{
//		return false;
//	}
//	
//	public boolean addNewCommunity(Community c)
//	{
//		return false;
//	}
	
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
