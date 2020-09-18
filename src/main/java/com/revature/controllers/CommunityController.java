package com.revature.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.annotations.AuthorizedAdmin;
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
	public ResponseEntity<Community> addCommunity() {
		log.info("addCommunity method invoked");
		
		return null;
	}
	
	
}
