package com.revature.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	
}
