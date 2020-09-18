package com.revature.controllers;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.annotations.AuthorizedAdmin;
import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.AddCommunityDTO;
import com.revature.exceptions.CommunityException;
import com.revature.models.Community;
import com.revature.services.CommunityService;
import com.revature.services.UserService;

@Controller
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	
	private static Logger log = Logger.getLogger(CommunityController.class);
	
	@AuthorizedAdmin
	@RequestMapping(value = "/community", method = RequestMethod.POST)
	public ResponseEntity<Community> addCommunity(@RequestBody AddCommunityDTO dto) throws CommunityException {
		log.info("addCommunity method invoked");
		
		String name = dto.getName();
		String description = dto.getDescription();
		
		if (name == null || name.equals("") || description == null || description.equals("")) {
			throw new CommunityException("Name and/or description cannot be blank when sending a request to add a community");
		}
		
		Community c = communityService.addCommunity(name, description);
		
		return ResponseEntity.ok(c);
	}
	
	@AuthorizedConsumer
	@RequestMapping(value = "/community", method = RequestMethod.GET)
	public ResponseEntity<Set<Community>> getCommunities() {
		log.info("getCommunities method invoked");
		
		Set<Community> communities = communityService.getCommunities();
		
		return ResponseEntity.ok(communities);
	}
	
	
}
