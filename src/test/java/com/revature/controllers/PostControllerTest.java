package com.revature.controllers;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tika.Tika;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.DeletePostException;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.PostService;

public class PostControllerTest {

	@Mock
	private PostService postService;
	
	@Mock
	private Tika tika;
	
	@Mock
	private HttpServletRequest req;
	
	@Mock
	private HttpServletResponse resp;
	
	@InjectMocks
	private PostController postController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
	}
	
	@Test
	public void getPostsInCommunity_success() throws Exception {
		
		when(postService.getPosts(eq(1))).thenReturn(Sets.newSet(new Post(1, "This is a bird", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "Animals", "Post pictures of animals here!"), new Timestamp(0L))));
		
		String expectedJson = new ObjectMapper().writeValueAsString(Sets.newSet(
				new SendPostDTO(1, "This is a bird", 
						new User(1, "billy_bob", "", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L))));
		
		this.mockMvc.perform(get("/post").queryParam("communityId", "1"))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
	
	@Test
	public void addPostToCommunity_success() throws Exception {
		MockMultipartFile image = new MockMultipartFile("file", "", "image/jpeg", new byte[0]);
						
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(postService.addPost(eq(1), eq("This is a bird"), eq(image), 
				eq(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")))))
				.thenReturn(new Post(1, "This is a bird", 
					new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
					new Community(1, "Animals", "Post pictures of animals here!"), new Timestamp(0L)));
		
		String expectedJson = new ObjectMapper().writeValueAsString(new SendPostDTO(1, "This is a bird", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L)));
		
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/post")
				.file(image)
				.param("communityId", "1")
				.param("caption", "This is a bird"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
	
	@Test(expected = AddPostException.class)
	public void addPostToCommunity_nullCurrentUser() throws Exception {
		MockMultipartFile image = new MockMultipartFile("file", "", "image/jpeg", new byte[0]);
						
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(null);
		
		when(postService.addPost(eq(1), eq("This is a bird"), eq(image), 
				eq(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")))))
				.thenReturn(new Post(1, "This is a bird", 
					new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
					new Community(1, "Animals", "Post pictures of animals here!"), new Timestamp(0L)));
		
		String expectedJson = new ObjectMapper().writeValueAsString(new SendPostDTO(1, "This is a bird", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L)));
		
		try {
			this.mockMvc.perform(MockMvcRequestBuilders.multipart("/post")
					.file(image)
					.param("communityId", "1")
					.param("caption", "This is a bird"))
					.andExpect(status().isOk())
					.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test(expected = AddPostException.class)
	public void addPostToCommunity_postServiceThrowsException() throws Exception {
		MockMultipartFile image = new MockMultipartFile("file", "", "image/jpeg", new byte[0]);
						
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(postService.addPost(eq(1), eq("This is a bird"), eq(image), 
				eq(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")))))
				.thenThrow(IOException.class);
		
		String expectedJson = new ObjectMapper().writeValueAsString(new SendPostDTO(1, "This is a bird", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L)));
		
		try {
			this.mockMvc.perform(MockMvcRequestBuilders.multipart("/post")
					.file(image)
					.param("communityId", "1")
					.param("caption", "This is a bird"))
					.andExpect(status().isOk())
					.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test
	public void getImage_successJpeg() throws Exception {
		byte[] mockImage = new byte[0];
		
		when(postService.getImage(eq(1))).thenReturn(mockImage);
		when(tika.detect(eq(mockImage))).thenReturn("image/jpeg");
		
		this.mockMvc.perform(get("/post/image/1"))
			.andExpect(content().contentType(MediaType.IMAGE_JPEG))
			.andExpect(status().isOk())
			.andExpect(content().bytes(mockImage));
	}
	
	@Test
	public void getImage_successGif() throws Exception {
		byte[] mockImage = new byte[0];
		
		when(postService.getImage(eq(1))).thenReturn(mockImage);
		when(tika.detect(eq(mockImage))).thenReturn("image/gif");
		
		this.mockMvc.perform(get("/post/image/1"))
			.andExpect(content().contentType(MediaType.IMAGE_GIF))
			.andExpect(status().isOk())
			.andExpect(content().bytes(mockImage));
	}
	
	@Test
	public void getImage_successPng() throws Exception {
		byte[] mockImage = new byte[0];
		
		when(postService.getImage(eq(1))).thenReturn(mockImage);
		when(tika.detect(eq(mockImage))).thenReturn("image/png");
		
		this.mockMvc.perform(get("/post/image/1"))
			.andExpect(content().contentType(MediaType.IMAGE_PNG))
			.andExpect(status().isOk())
			.andExpect(content().bytes(mockImage));
	}
	
	@Test
	public void deletePost_success() throws Exception {
		when(postService.deletePost(eq(1))).thenReturn(true);
		
		this.mockMvc.perform(delete("/post/1"))
			.andExpect(status().isOk());
	}
	
	@Test(expected = DeletePostException.class)
	public void deletePost_fail() throws Exception {
		when(postService.deletePost(eq(1))).thenReturn(false);
		
		try {
			this.mockMvc.perform(delete("/post/1"))
			.andExpect(status().isOk());
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
}
