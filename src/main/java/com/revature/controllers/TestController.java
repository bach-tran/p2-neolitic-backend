package com.revature.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
// This is a test controller to make sure everything works nice and dandy
public class TestController {

	private static Logger log = Logger.getLogger(TestController.class);
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String home() {
		log.info("Test controller invoked");
		return "home";
	}
}
