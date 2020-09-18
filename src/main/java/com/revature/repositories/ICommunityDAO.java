package com.revature.repositories;

import java.util.List;
import java.util.Set;

import com.revature.models.Community;

public interface ICommunityDAO {
	
	public Set<Community> findAll();
	
	public Community findById(int id);
	
	public Community findByName(String name);
	
	public Community insertCommunity(Community c);
	
}
