package com.revature.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;

import com.revature.exceptions.CommunityException;
import com.revature.models.Community;
import com.revature.repositories.ICommunityDAO;

public class CommunityServiceTest {

	@InjectMocks
	private CommunityService communityService;

	@Mock
	private ICommunityDAO communityDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getCommunities_successHasResults() {
		when(communityDao.findAll())
			.thenReturn(Sets.newSet(new Community(1, "Gaming", "For video games"), new Community(2, "Cats", "Funny Cat photos here"), 
					new Community(3, "Dogs", "Funny dog photos here")));
		
		Set<Community> actual = communityService.getCommunities();
		
		Set<Community> expected = Sets.newSet(new Community(1, "Gaming", "For video games"), new Community(2, "Cats", "Funny Cat photos here"), 
				new Community(3, "Dogs", "Funny dog photos here"));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getCommunities_successNoResults() {
		when(communityDao.findAll())
			.thenReturn(null);
		
		Set<Community> actual = communityService.getCommunities();
		
		Set<Community> expected = Sets.newSet();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addCommunity_success() throws CommunityException {
		
		when(communityDao.findByName("eSports")).thenReturn(null);
		when(communityDao.insertCommunity(any(Community.class))).thenReturn(new Community(1, "eSports", "Post competitive video game pictures here"));
		
		Community actual = communityService.addCommunity("eSports", "Post competitive video game pictures here");
		Community expected = new Community(1, "eSports", "Post competitive video game pictures here");
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = CommunityException.class)
	public void addCommunity_previouslyExistingSameCase() throws CommunityException {
		when(communityDao.findByName("eSports")).thenReturn(new Community(1, "eSports", "Post competitive video game pictures here"));
		
		when(communityDao.insertCommunity(any(Community.class))).thenReturn(new Community(1, "eSports", "Post competitive video game pictures here"));
		
		Community actual = communityService.addCommunity("eSports", "Post competitive video game pictures here");
	}
	
	@Test(expected = CommunityException.class)
	public void addCommunity_previouslyExistingDifferentCase() throws CommunityException {
		when(communityDao.findByName("eSports")).thenReturn(new Community(1, "ESports", "Post competitive video game pictures here"));
		
		Community actual = communityService.addCommunity("eSports", "Post competitive video game pictures here");
	}
	
	@Test(expected = CommunityException.class)
	public void addCommunity_nullInsert() throws CommunityException {
		
		when(communityDao.findByName("eSports")).thenReturn(null);
		when(communityDao.insertCommunity(any(Community.class))).thenReturn(null);
		
		Community actual = communityService.addCommunity("eSports", "Post competitive video game pictures here");
	}
}
