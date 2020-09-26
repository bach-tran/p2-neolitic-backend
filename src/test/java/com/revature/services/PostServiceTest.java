package com.revature.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.springframework.web.multipart.MultipartFile;

import com.revature.exceptions.AddPostException;
import com.revature.exceptions.CommunityDoesNotExist;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.ICommunityDAO;
import com.revature.repositories.IPostDAO;
import com.revature.util.AmazonClient;

public class PostServiceTest {
	
	@InjectMocks
	private PostService postService;
	
	@Mock
	private AmazonClient amazonS3Service;
	
	@Mock
	private IPostDAO postDao;
	@Mock
	private ICommunityDAO communityDao;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = CommunityDoesNotExist.class)
	public void addPost_CommunityDoesNotExist() throws PostException, IOException, AddPostException, CommunityDoesNotExist {
		when(communityDao.findById(eq(1))).thenReturn(null);
		
		MultipartFile file = mock(MultipartFile.class);
		
		postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
	}
	
	@Test(expected = AddPostException.class)
	public void addPost_notAcceptedFormat() throws IOException, AddPostException, CommunityDoesNotExist {
		when(communityDao.findById(1)).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("application/json");
		
		postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
	}
	
	@Test
	public void addPost_bmpFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/bmp");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addPost_jpgFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/jpg");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addPost_jpegFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/jpg");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addPost_pngFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		
		when(file.getContentType()).thenReturn("image/png");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addPost_gifFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/png");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void addPost_tiffFormat() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/tiff");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
						new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		Post actual = postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
		Post expected = new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L));
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = AddPostException.class)
	public void addPost_nullInsert() throws IOException, PostException, AddPostException, CommunityDoesNotExist {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(new byte[10]);
		when(file.getContentType()).thenReturn("image/tiff");
		
		when(communityDao.findById(eq(1))).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.insertPost(any())).thenReturn(null);
		
		postService.addPost(1, "This is a test post", file, new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")));
	}
	
	@Test
	public void getPosts_success() throws PostException, CommunityDoesNotExist {
		when(communityDao.findById(1)).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.findAllInCommunity(eq(1))).thenReturn(Sets.newSet(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L))));
		
		Set<Post> actual = postService.getPosts(1);
		
		Set<Post> expected = Sets.newSet(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getPosts_nullResults() throws PostException, CommunityDoesNotExist {
		when(communityDao.findById(1)).thenReturn(new Community(1, "eSports", "Post competitive gaming photos here!"));
		when(postDao.findAllInCommunity(eq(1))).thenReturn(null);
		
		Set<Post> actual = postService.getPosts(1);
		
		Set<Post> expected = Sets.newSet();
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = CommunityDoesNotExist.class)
	public void getPosts_nullCommunity() throws PostException, CommunityDoesNotExist {
		when(communityDao.findById(1)).thenReturn(null);
		
		Set<Post> actual = postService.getPosts(1);
	}
	
	@Test
	public void getImage_success() throws GetImageException, PostDoesNotExist, IOException {
		when(postDao.findById(eq(1))).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		when(amazonS3Service.getFileFromS3Bucket(eq("post_1_image"))).thenReturn(new byte[10]);
		
		byte[] actual = postService.getImage(1);
		
		byte[] expected = new byte[10];
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test(expected = PostDoesNotExist.class)
	public void getImage_noSuchPost() throws GetImageException, PostDoesNotExist {
		when(postDao.findById(eq(1))).thenReturn(null);
		
		byte[] actual = postService.getImage(100);
		
		byte[] expected = new byte[10];
		
		assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void deletePost_success() throws PostDoesNotExist {
		when(postDao.findById(eq(1))).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		when(postDao.deletePost(eq(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L))))).thenReturn(true);
		
		assertTrue(postService.deletePost(1));
	}
	
	@Test(expected = PostDoesNotExist.class)
	public void deletePost_nullPost() throws PostDoesNotExist {
		when(postDao.findById(eq(1))).thenReturn(null);
		
		postService.deletePost(1);
	}
	
	@Test
	public void deletePost_DaoException() throws PostDoesNotExist {
		when(postDao.findById(eq(1))).thenReturn(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L)));
		
		when(postDao.deletePost(eq(new Post(1, "This is a test post", 
				new User(1, "billy_bob", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Billy", "Bob", new Role(2, "consumer")), 
				new Community(1, "eSports", "Post competitive gaming photos here!"), new Timestamp(0L))))).thenReturn(false);
		
		assertFalse(postService.deletePost(1));
	}
}
