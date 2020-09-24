package com.revature.controllers;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.revature.dto.AddCommentDTO;
import com.revature.dto.SendCommentDTO;
import com.revature.exceptions.AddCommentException;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.models.Comment;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.CommentService;
import com.revature.services.UserService;

public class CommentControllerTest {

	@Mock
	private CommentService commentService;
	
	@Mock
	private HttpServletRequest req;
	
	@InjectMocks
	private CommentController commentController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
	}
	
	@Test
	public void getComments_success() throws Exception {
		when(commentService.getCommentsFromPost(eq(1)))
			.thenReturn(Sets.newSet(new Comment(1, "looks nice!", 
			new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")),
			new Post(1, new byte[0], "A dog", new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")),
					new Community(1, "Animals", "Post animals here"), new Timestamp(0L)), new Timestamp(0L))));
		
		String expectedJson = new ObjectMapper().writeValueAsString(Sets.newSet(
				new SendCommentDTO(1, "looks nice!", new User(1, "billy_bob", "", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L))));
		
		this.mockMvc.perform(get("/comment").queryParam("postId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().json(expectedJson));
	}
	
	@Test
	public void getComments_successEmpty() throws Exception {
		when(commentService.getCommentsFromPost(eq(1)))
			.thenReturn(Sets.newSet());
		
		String expectedJson = new ObjectMapper().writeValueAsString(Sets.newSet());
		
		this.mockMvc.perform(get("/comment").queryParam("postId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().json(expectedJson));
	}
	
	@Test
	public void postComment_success() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(commentService.addCommentToPost(1, "looks nice!", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"))))
				.thenReturn(new Comment(1, "looks nice!", 
						new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Post(1, new byte[0], "A dog", new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")),
								new Community(1, "Animals", "Post animals here"), new Timestamp(0L)), new Timestamp(0L)));
		
		String expectedJson = new ObjectMapper()
				.writeValueAsString(new SendCommentDTO(1, "looks nice!", 
						new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L)));
		
		AddCommentDTO dto = new AddCommentDTO();
		dto.setPostId(1);
		dto.setText("looks nice!");
		
		this.mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}
	
	@Test(expected = AddPostException.class)
	public void postComment_noUser() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		
		when(commentService.addCommentToPost(1, "looks nice!", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"))))
				.thenReturn(new Comment(1, "looks nice!", 
						new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Post(1, new byte[0], "A dog", new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")),
								new Community(1, "Animals", "Post animals here"), new Timestamp(0L)), new Timestamp(0L)));
		
		String expectedJson = new ObjectMapper()
				.writeValueAsString(new SendCommentDTO(1, "looks nice!", 
						new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), new Timestamp(0L)));
		
		AddCommentDTO dto = new AddCommentDTO();
		dto.setPostId(1);
		dto.setText("looks nice!");
		
		try {
			this.mockMvc.perform(post("/comment").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
		
	}
	
}
