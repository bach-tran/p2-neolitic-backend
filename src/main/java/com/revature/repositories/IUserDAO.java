package com.revature.repositories;

import java.util.Set;

import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.User;

public interface IUserDAO {

	public User register(User u) throws RegistrationException;
	
	public User login(String username, String hashedPassword) throws LoginException;
	
	public Set<User> getAllUsers();
	
	public User getUserById(int userId);
	
}
