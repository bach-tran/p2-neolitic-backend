package com.revature.controllers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.AddCommunityDTO;
import com.revature.dto.SendCommunityDTO;
import com.revature.exceptions.CommunityException;
import com.revature.models.Community;
import com.revature.services.CommunityService;

public class CommunityControllerTest {

	@Mock
	private CommunityService communityService;
	
	@InjectMocks
	private CommunityController communityController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
	}
	
	@Test
	public void addCommunity_success() throws JsonProcessingException, Exception {
		AddCommunityDTO dto = new AddCommunityDTO();
		dto.setName("Animals");
		dto.setDescription("Post pictures of animals here!");
		
		SendCommunityDTO sendDto = new SendCommunityDTO(1, "Animals", "Post pictures of animals here!");
		String expectedJson = new ObjectMapper().writeValueAsString(sendDto);
		
		when(communityService.addCommunity(eq("Animals"), eq("Post pictures of animals here!"))).thenReturn(new Community(1, "Animals", "Post pictures of animals here!"));
		
		this.mockMvc.perform(post("/community").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
	
	@Test(expected = CommunityException.class)
	public void addCommunity_emptyName() throws JsonProcessingException, Exception {
		AddCommunityDTO dto = new AddCommunityDTO();
		dto.setName("");
		dto.setDescription("Post pictures of animals here!");
		
		SendCommunityDTO sendDto = new SendCommunityDTO(1, "Animals", "Post pictures of animals here!");
		String expectedJson = new ObjectMapper().writeValueAsString(sendDto);
		
		when(communityService.addCommunity(eq("Animals"), eq("Post pictures of animals here!"))).thenReturn(new Community(1, "Animals", "Post pictures of animals here!"));
		
		try {
			this.mockMvc.perform(post("/community").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test(expected = CommunityException.class)
	public void addCommunity_emptyDescription() throws JsonProcessingException, Exception {
		AddCommunityDTO dto = new AddCommunityDTO();
		dto.setName("Animals");
		dto.setDescription("");
		
		SendCommunityDTO sendDto = new SendCommunityDTO(1, "Animals", "Post pictures of animals here!");
		String expectedJson = new ObjectMapper().writeValueAsString(sendDto);
		
		when(communityService.addCommunity(eq("Animals"), eq("Post pictures of animals here!"))).thenReturn(new Community(1, "Animals", "Post pictures of animals here!"));
		
		try {
			this.mockMvc.perform(post("/community").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}

	@Test(expected = CommunityException.class)
	public void addCommunity_emptyNameAndDescription() throws JsonProcessingException, Exception {
		AddCommunityDTO dto = new AddCommunityDTO();
		dto.setName("");
		dto.setDescription("");
		
		SendCommunityDTO sendDto = new SendCommunityDTO(1, "Animals", "Post pictures of animals here!");
		String expectedJson = new ObjectMapper().writeValueAsString(sendDto);
		
		when(communityService.addCommunity(eq("Animals"), eq("Post pictures of animals here!"))).thenReturn(new Community(1, "Animals", "Post pictures of animals here!"));
		
		try {
			this.mockMvc.perform(post("/community").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test
	public void getCommunities_success() throws Exception {
		
		when(communityService.getCommunities()).thenReturn(Sets.newSet(new Community(1, "Animals", "Post pictures of animals here!"), 
				new Community(2, "Gaming", "Post pictures of your favorite gaming moments here!")));
		
		SendCommunityDTO dto1 = new SendCommunityDTO(1, "Animals", "Post pictures of animals here!");
		SendCommunityDTO dto2 = new SendCommunityDTO(2, "Gaming", "Post pictures of your favorite gaming moments here!");
		String expectedJson = new ObjectMapper().writeValueAsString(Sets.newSet(dto1, dto2));
		
		this.mockMvc.perform(get("/community"))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
}
