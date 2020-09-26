package com.revature.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.exceptions.AddCommentException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.models.Comment;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.ICommentDAO;
import com.revature.repositories.IPostDAO;

public class CommentServiceTest {

	@InjectMocks
	private CommentService commentService;
	
	@Mock
	private ICommentDAO commentDao;
	
	@Mock
	private IPostDAO postDao;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getCommentsFromPost_success() throws PostDoesNotExist {
		when(postDao.findById(20)).thenReturn(new Post(20, "animated gif", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)));
		when(commentDao.findAllInPost(eq(20))).thenReturn(Sets.newSet(new Comment(1, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L))));
		
		Set<Comment> actual = commentService.getCommentsFromPost(20);
		Set<Comment> expected = Sets.newSet(new Comment(1, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L)));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getCommentsFromPost_null() throws PostDoesNotExist {
		when(postDao.findById(20)).thenReturn(new Post(20, "animated gif", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)));
		when(commentDao.findAllInPost(eq(20))).thenReturn(null);
		
		Set<Comment> actual = commentService.getCommentsFromPost(20);
		Set<Comment> expected = Sets.newSet();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = PostDoesNotExist.class)
	public void getCommentsFromPost_nullPost() throws PostDoesNotExist {
		when(postDao.findById(20)).thenReturn(null);
		
		Set<Comment> actual = commentService.getCommentsFromPost(20);
	}
	
	@Test
	public void addCommentToPost_successful() throws PostDoesNotExist, AddCommentException {
		when(postDao.findById(20)).thenReturn(new Post(20, "animated gif", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)));
		
		when(commentDao.insertComment((new Comment(0, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), any())))).thenReturn(new Comment(1, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L)));
		
		Comment expected = new Comment(1, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L));
		
		Comment actual = commentService.addCommentToPost(20, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")));
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = PostDoesNotExist.class)
	public void addCommentToPost_nullPost() throws PostDoesNotExist, AddCommentException {
		when(postDao.findById(20)).thenReturn(null);
				
		Comment actual = commentService.addCommentToPost(20, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")));
	}
	
	@Test(expected = AddCommentException.class)
	public void addCommentToPost_unsuccessfulInsertion() throws PostDoesNotExist, AddCommentException {
		when(postDao.findById(20)).thenReturn(new Post(20, "animated gif", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)));
		
		when(commentDao.insertComment(eq(new Comment(0, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L))))).thenReturn(null);
		
		Comment expected = new Comment(1, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
				new Post(20, "animated gif", 
						new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")), 
						new Community(1, "Gaming", "For Video Games"), new Timestamp(0L)), new Timestamp(0L));
		
		Comment actual = commentService.addCommentToPost(20, "Woah that's so cool! Nice one!", 
				new User(6, "bach_tran", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Bach", "Tran", new Role(1, "admin")));
		
		assertEquals(expected, actual);
	}

}
