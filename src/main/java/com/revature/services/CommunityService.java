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
		
		Community previouslyExisting = communityDAO.findByName(name);
		if (previouslyExisting != null && previouslyExisting.getName().equalsIgnoreCase(name)) {
			log.error("Community with name " + name + " already exists");
			throw new CommunityException("Community name " + name + " already exists. Cannot add community");
		}
		
		Community insertedCommunity = communityDAO.insertCommunity(c);
		if(insertedCommunity == null) {
			log.error("Community was not successfully inserted");
			throw new CommunityException("Community was not successfully inserted.");
		}
		
		return insertedCommunity;
	}
	
	public Set<Community> getCommunities() {
		Set<Community> communities = communityDAO.findAll();
		
		if (communities == null) {
			communities = new HashSet<Community>();
		}
		
		return communities;
	}
	
}
