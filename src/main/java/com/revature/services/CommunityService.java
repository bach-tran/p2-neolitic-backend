package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.repositories.ICommunityDAO;

@Service
public class CommunityService {

	@Autowired
	private ICommunityDAO communityDAO;
	
	public CommunityService() {
		super();
	}

}
