package com.revature.aspects;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revature.models.Role;
import com.revature.models.User;

public class AuthorizationAspectTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	private AuthorizationAspect aspect;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, response));
		aspect = new AuthorizationAspect();
	}
	
	@Test
	public void authenticateAdmin_nullSession() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(null);
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		assertNull(obj);
		
		verify(request, times(1)).getSession(eq(false));
	}
	
	@Test
	public void authenticateAdmin_validSession_nullUser_validResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		assertNull(obj);
		
		verify(response, times(1)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}
	
	@Test
	public void authenticateAdmin_validSession_nullUser_nullResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		/* new User(1, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")) */
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, null));
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		assertNull(obj);
		
		verify(response, times(0)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}

	@Test
	public void authenticateAdmin_validSessionAndUse_validResponseObject_isAdmin() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(new 
				User(1, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")));
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		verify(pjp, times(1)).proceed(any());
	}
	
	@Test
	public void authenticateAdmin_validSessionAndUse_validResponseObject_notAdmin() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(new 
				User(1, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(2, "consumer")));
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		verify(pjp, times(0)).proceed(any());
		verify(response, times(1)).sendError(eq(401), eq("Only admins can access this resource"));
	}
	
	@Test
	public void authenticateAdmin_validSessionAndUse_nullResponseObject_notAdmin() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(new 
				User(1, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(2, "consumer")));
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, null));
		
		Object obj = aspect.authenticateAdmin(pjp);
		
		verify(pjp, times(0)).proceed(any());
		verify(response, times(0)).sendError(eq(401), eq("Only admins can access this resource"));
	}
	
	@Test
	public void authenticateConsumer_nullSession_validResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(null);
		
		Object obj = aspect.authenticateConsumer(pjp);
		verify(response, times(1)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}
	
	@Test
	public void authenticateConsumer_nullSession_nullResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(null);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, null));
		
		Object obj = aspect.authenticateConsumer(pjp);
		verify(response, times(0)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}
	
	@Test
	public void authenticateConsumer_validSession_nullUser_validResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		
		Object obj = aspect.authenticateConsumer(pjp);
		verify(response, times(1)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}
	
	@Test
	public void authenticateConsumer_validSession_nullUser_nullResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, null));
		
		Object obj = aspect.authenticateConsumer(pjp);
		verify(response, times(0)).sendError(eq(401), eq("You must be logged in to access the resource"));
	}
	
	@Test
	public void authenticateConsumer_validSession_validUser_validResponseObject() throws Throwable {
		HttpSession session = mock(HttpSession.class);
		ProceedingJoinPoint pjp = mock(ProceedingJoinPoint.class);
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(new 
				User(1, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(2, "consumer")));
		
		Object obj = aspect.authenticateConsumer(pjp);
		verify(response, times(0)).sendError(eq(401), eq("You must be logged in to access the resource"));
		verify(pjp, times(1)).proceed(any());
	}
		
}
