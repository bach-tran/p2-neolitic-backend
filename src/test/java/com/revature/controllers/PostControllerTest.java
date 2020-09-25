package com.revature.controllers;

import static org.junit.Assert.*;

import org.apache.tika.Tika;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.services.PostService;

public class PostControllerTest {

	@Mock
	private PostService postService;
	
	@Mock
	private Tika tika;
	
	@InjectMocks
	private PostController postController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
	}
	
}
