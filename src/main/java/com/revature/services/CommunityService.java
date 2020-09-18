package com.revature.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.CommunityException;
import com.revature.models.Community;
import com.revature.repositories.ICommunityDAO;

@Service
public class CommunityService {

	@Autowired
	private ICommunityDAO communityDAO;
	
	private static Logger log = Logger.getLogger(CommunityService.class);
	
	public CommunityService() {
		super();
	}

	public Community addCommunity(String name, String description) throws CommunityException {
		Community c = new Community(0, name, description);
		
		communityDAO.insertCommunity(c);
		
		if (c.getId() == 0) {
			log.error("Community was not successfully inserted");
			throw new CommunityException("Community was not successfully inserted. Check if name is already taken.");
		}
		
		return c;
	}
	
	public Set<Community> getCommunities() {
		Set<Community> communities = communityDAO.findAll();
		
		if (communities == null) {
			communities = new HashSet<Community>();
		}
		
		return communities;
	}
	
}
