package com.revature.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.LoginDTO;
import com.revature.dto.RegisterDTO;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserControllerTest {

	@Mock
	private UserService userService;
	
	@Mock
	private HttpServletRequest req;
	
	@InjectMocks
	private UserController userController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	/*
	 * user/current
	 */
	@Test
	public void testUserCurrent_containsUser() throws Exception {
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(true);
		
		this.mockMvc.perform(get("/user/current")).andExpect(status().isOk());
	}
	
	@Test
	public void testUserCurrent_noUser() throws Exception {
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		
		this.mockMvc.perform(get("/user/current")).andExpect(status().isNoContent());
	}
	
	/*
	 * Login
	 */
	@Test
	public void testLogin_Success() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("billy_bob");
		loginDTO.setPassword("12345");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
		.andExpect(status().isOk())
		.andExpect(content().json(jsonResult));
	}
	
	@Test(expected = LoginException.class)
	public void testLogin_userAlreadyLoggedIn() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("billy_bob");
		loginDTO.setPassword("12345");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(true); // expect throw exception
		User mockedUser = mock(User.class);
		when(userService.getCurrentUser(eq(session))).thenReturn(mockedUser);
		when(mockedUser.getUsername()).thenReturn("some_user");
		
		// Should throw exception before reaching these mocked method calls
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
		
	}
	
	@Test(expected = LoginException.class)
	public void testLogin_BlankUsername_NonblankPassword() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("");
		loginDTO.setPassword("12345");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		User mockedUser = mock(User.class);
		when(userService.getCurrentUser(eq(session))).thenReturn(mockedUser);
		when(mockedUser.getUsername()).thenReturn("some_user");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult)).andReturn();
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test(expected = LoginException.class)
	public void testLogin_NonblankUsername_blankPassword() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("billy_bob");
		loginDTO.setPassword("");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		User mockedUser = mock(User.class);
		when(userService.getCurrentUser(eq(session))).thenReturn(mockedUser);
		when(mockedUser.getUsername()).thenReturn("some_user");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
		
	}
	
	@Test(expected = LoginException.class)
	public void testLogin_blankUsername_blankPassword() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("");
		loginDTO.setPassword("");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		User mockedUser = mock(User.class);
		when(userService.getCurrentUser(eq(session))).thenReturn(mockedUser);
		when(mockedUser.getUsername()).thenReturn("some_user");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
		
	}
	
	@Test(expected = LoginException.class)
	public void testLogin_hashingException() throws Exception {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername("billy_bob");
		loginDTO.setPassword("12345");
		
		HttpSession session = mock(HttpSession.class);
		
		when(req.getSession()).thenReturn(session);
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		User mockedUser = mock(User.class);
		when(userService.getCurrentUser(eq(session))).thenReturn(mockedUser);
		when(mockedUser.getUsername()).thenReturn("some_user");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenThrow(NoSuchAlgorithmException.class);
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(loginDTO)))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test
	public void checkCurrentUser_noUser() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		
		this.mockMvc.perform(get("/user/current"))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void checkCurrentUser_hasUser() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(true);
		when(userService.getCurrentUser(eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		this.mockMvc.perform(get("/user/current"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
	}
	
	@Test
	public void logout_success() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(true);
		when(userService.getCurrentUser(eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		this.mockMvc.perform(get("/user/logout"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void logout_notLoggedIn() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		
		this.mockMvc.perform(get("/user/logout"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void register_success() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		RegisterDTO dto = new RegisterDTO();
		dto.setFirstName("Billy");
		dto.setLastName("Bob");
		dto.setPassword("12345");
		dto.setConfirmPassword("12345");
		dto.setUsername("billy_bob");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		
		when(userService.registerAccount(eq("Billy"), eq("Bob"), eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
	}
	
	@Test(expected = RegistrationException.class)
	public void register_userAlreadyLoggedIn() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(true);
		when(userService.getCurrentUser(eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		// THROWS EXCEPTION HERE
		
		RegisterDTO dto = new RegisterDTO();
		dto.setFirstName("Billy");
		dto.setLastName("Bob");
		dto.setPassword("12345");
		dto.setConfirmPassword("12345");
		dto.setUsername("billy_bob");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		
		when(userService.registerAccount(eq("Billy"), eq("Bob"), eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}

	}
	
	@Test(expected = RegistrationException.class)
	public void register_ConfirmPasswordIsDifferent() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		RegisterDTO dto = new RegisterDTO();
		dto.setFirstName("Billy");
		dto.setLastName("Bob");
		dto.setPassword("12345");
		dto.setConfirmPassword("123456");
		dto.setUsername("billy_bob");
		
		when(userService.getSHA(eq("12345"))).thenReturn(new byte[0]);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		
		when(userService.registerAccount(eq("Billy"), eq("Bob"), eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
	
	@Test(expected = RegistrationException.class)
	public void register_HashingException() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		when(userService.userLoggedIn(eq(session))).thenReturn(false);
		RegisterDTO dto = new RegisterDTO();
		dto.setFirstName("Billy");
		dto.setLastName("Bob");
		dto.setPassword("12345");
		dto.setConfirmPassword("12345");
		dto.setUsername("billy_bob");
		
		when(userService.getSHA(eq("12345"))).thenThrow(NoSuchAlgorithmException.class);
		when(userService.toHexString(any())).thenReturn("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
		
		when(userService.registerAccount(eq("Billy"), eq("Bob"), eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		when(userService.login(eq("billy_bob"), eq("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"), eq(session)))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		String jsonResult = new ObjectMapper()
				.writeValueAsString(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		try {
			this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResult));
		} catch (NestedServletException e) {
			throw (Exception) e.getCause();
		}
	}
}
