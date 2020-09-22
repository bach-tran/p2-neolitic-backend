package com.revature.controllers;

import java.util.HashSet;
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
import com.revature.dto.SendCommunityDTO;
import com.revature.exceptions.CommunityException;
import com.revature.models.Community;
import com.revature.services.CommunityService;
import com.revature.services.UserService;
import com.revature.util.HibernateUtility;

@Controller
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	
	private static Logger log = Logger.getLogger(CommunityController.class);
	
	@AuthorizedAdmin
	@RequestMapping(value = "/community", method = RequestMethod.POST)
	public ResponseEntity<SendCommunityDTO> addCommunity(@RequestBody AddCommunityDTO dto) throws CommunityException {
		log.info("addCommunity method invoked");
		
		String name = dto.getName();
		String description = dto.getDescription();
		
		if (name.equals("") || description.equals("")) {
			throw new CommunityException("Name and/or description cannot be blank when sending a request to add a community");
		}
		
		Community c = communityService.addCommunity(name, description);
		
		SendCommunityDTO sendDto = new SendCommunityDTO(c.getId(), c.getName(), c.getDescription());
		
		HibernateUtility.closeSession();
		
		return ResponseEntity.ok(sendDto);
	}
	
	@AuthorizedConsumer
	@RequestMapping(value = "/community", method = RequestMethod.GET)
	public ResponseEntity<Set<SendCommunityDTO>> getCommunities() {
		log.info("getCommunities method invoked");
		
		Set<Community> communities = communityService.getCommunities();
		
		Set<SendCommunityDTO> communitiesDto = new HashSet<>();
		for (Community community : communities) {
			communitiesDto.add(new SendCommunityDTO(community.getId(), community.getName(), community.getDescription()));
		}
		
		HibernateUtility.closeSession();
		
		return ResponseEntity.ok(communitiesDto);
	}
	
	
}
