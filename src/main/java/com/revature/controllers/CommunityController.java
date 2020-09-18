package com.revature.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.revature.services.CommunityService;
import com.revature.services.UserService;

@Controller
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	
	private static Logger log = Logger.getLogger(CommunityController.class);
	
}
