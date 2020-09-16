package com.revature.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.revature.models.Community;
import com.revature.models.User;
import com.revature.models.Postcard;
import com.revature.models.Role;


public class UserService {

	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean registerAccount(String firstName, String lastName, String username, String password)
	{
		User u = new User(0, firstName, lastName, username, encryptPassword(password), new Role (2, "consumer"));
		
		return false;
	}
	
	public boolean login(String username, String password)
	{
		return false;
	}
	
	public boolean createPost(byte[] image, String caption, Community community)
	{
		return false;
	}
	
	public List<Postcard> viewCommunityPosts(Community c)
	{
		return null;
	}
	
	public boolean commentOnPost(Postcard post, User u, String comment)
	{
		return false;
	}
	
	/*
	 * The following methods are restricted to Administrators
	 */
	
	public boolean removePost(Postcard post)
	{
		return false;
	}
	
	public boolean addNewCommunity(Community c)
	{
		return false;
	}
	
	/* 
	 * The following three methods are used to hash passwords with SHA-256.
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
    
    public String encryptPassword(String password)
    {
    	try {
			return toHexString(getSHA(password));
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			return password;
		}
    }
}
