package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.IUserDAO;

public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private IUserDAO userDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testRegisterAccount_CapitalizedFirstandLastName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("Bach", "Tran", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test
	public void testRegisterAccount_CapitalizedFirstNameLowercaseLastName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("Bach", "tran", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test
	public void testRegisterAccount_LowercaseFirstNameCapitalizedLastName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("bach", "Tran", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test
	public void testRegisterAccount_LowercaseFirstNameandLowercaseLastName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("bach", "tran", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test(expected=RegistrationException.class)
	public void testRegisterAccount_EmptyFirstName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("", "tran", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test(expected=RegistrationException.class)
	public void testRegisterAccount_EmptyLastName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("bach", "", "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test(expected=RegistrationException.class)
	public void testRegisterAccount_EmptyUserName() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("bach", "tran", "", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test(expected=RegistrationException.class)
	public void testRegisterAccount_EmptyPassword() throws RegistrationException {
		User user = new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer"));
		when(userDao.register(eq(user))).thenReturn(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		
		User u = userService.registerAccount("bach", "tran", "bach_tran", ""); // abc password
		assertEquals(new User(1, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")), u);
	}
	
	@Test
	public void testLoginSuccessful() throws LoginException {
		
		HttpSession session = mock(HttpSession.class);
		
		when(userDao.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"))
				.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
				"Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", session);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
		verify(session, times(1)).setAttribute(eq("currentUser"), eq(expected));
	}
	
	@Test(expected=LoginException.class)
	public void testLoginFailed_NullSession() throws LoginException {
		
		HttpSession session = mock(HttpSession.class);
		
		when(userDao.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"))
				.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
				"Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", null);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
	}	
	
	@Test(expected=LoginException.class)
	public void testLoginFailed_wrongPassword() throws LoginException {
		
		HttpSession session = mock(HttpSession.class);
		
		when(userDao.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"))
				.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
				"Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc6", session);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
	}	
	
	@Test(expected=LoginException.class)
	public void testLoginFailed_wrongUsername() throws LoginException {
		
		HttpSession session = mock(HttpSession.class);
		
		when(userDao.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"))
				.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
				"Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.login("billy_bobsssss", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", session);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
	}	
	
	@Test(expected=LoginException.class)
	public void testLoginFailed_wrongUsernameAndPassword() throws LoginException {
		
		HttpSession session = mock(HttpSession.class);
		
		when(userDao.login("billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"))
				.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
				"Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.login("billy_bobsssss", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc6", session);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
	}	
	
	@Test
	public void testUserLoggedIn_validSessionNoUser() {
		HttpSession session = mock(HttpSession.class);
		
		boolean result = userService.userLoggedIn(session);
		
		assertFalse(result);
	}
	
	@Test
	public void testUserLoggedIn_nullSession() {
		HttpSession session = mock(HttpSession.class);
		
		boolean result = userService.userLoggedIn(null);
		
		assertFalse(result);
		verify(session, times(0)).getAttribute(eq("currentUser"));
	}
	
	@Test
	public void testUserLoggedIn_validSessionCurrentUser() {
		HttpSession session = mock(HttpSession.class);
		
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		boolean result = userService.userLoggedIn(session);
		
		assertTrue(result);
	}
	
	@Test
	public void getCurrentUser_validSession() {
		HttpSession session = mock(HttpSession.class);
				
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		
		User actual = userService.getCurrentUser(session);
		User expected = new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer"));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getCurrentUser_validSession_noUser() {
		HttpSession session = mock(HttpSession.class);
		
		when(session.getAttribute(eq("currentUser")))
			.thenReturn(null);
		
		User actual = userService.getCurrentUser(session);
		assertEquals(null, actual);
	}
	
	@Test
	public void getCurrentUser_invalidSession() {
		HttpSession session = mock(HttpSession.class);
		
		User actual = userService.getCurrentUser(session);
		
		assertEquals(null, actual);
	}
	
	@Test
	public void getSHA() throws NoSuchAlgorithmException {
		String hexString = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"; // 12345
		byte[] expected = DatatypeConverter.parseHexBinary(hexString);
		
		byte[] actual = userService.getSHA("12345");
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void toHexString() {
		String hexString = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"; // 12345
		byte[] byteArray = DatatypeConverter.parseHexBinary(hexString);
		
		String actual = userService.toHexString(byteArray);
		
		assertEquals(hexString, actual);
	}
}
