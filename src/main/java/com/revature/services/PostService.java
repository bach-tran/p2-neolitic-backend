package com.revature.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.CommunityDoesNotExist;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.exceptions.UserDoesNotExist;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.ICommunityDAO;
import com.revature.repositories.IPostDAO;
import com.revature.repositories.IUserDAO;
import com.revature.util.AmazonClient;

@Service
public class PostService {

	@Autowired
	private IPostDAO postDAO;
	
	@Autowired
	private AmazonClient amazonS3Service;
	
	@Autowired
	private ICommunityDAO communityDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	private static Logger log = Logger.getLogger(PostService.class);
	
	public PostService() {
		super();
	}

	public Post addPost(int communityId, String caption, MultipartFile file, User user) throws AddPostException, CommunityDoesNotExist, IOException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Community c = communityDAO.findById(communityId);
		if (c == null) {
			log.error("Community attempting to add post to does not exist: " + communityId);
			throw new CommunityDoesNotExist("Community attempting to add post to does not exist" + communityId);
		}
		
		String contentType = file.getContentType();
		MediaType mt = MediaType.parseMediaType(contentType);
		
		String subtype = mt.getSubtype();
		
		if (!(subtype.equals("jpeg") || subtype.equals("jpg") || subtype.equals("bmp") || subtype.equals("gif") || subtype.equals("png") || subtype.equals("tiff"))) {
			throw new AddPostException("File upload does not correspond to jpeg, bmp, gif, png, or tiff");
		}
		
		Post p = new Post(0, caption, user, c, timestamp);
		
		Post insertedPost = postDAO.insertPost(p);
				
		if(insertedPost == null) {
			log.error("Post was not successfully inserted");
			throw new AddPostException("Post was not successfully inserted.");
		}
		
		File s3Image = amazonS3Service.convertMultipartToFile(file);
		String filename = "post_" + insertedPost.getId() + "_image";
		PutObjectResult result = amazonS3Service.uploadFileToS3Bucket(filename, s3Image);
		
		return insertedPost;
	}
	
	public Set<Post> getPosts(int communityId) throws CommunityDoesNotExist {
		
		Community c = communityDAO.findById(communityId);
		if (c == null) {
			log.error("Community attempting to find posts from does not exist " + communityId);
			throw new CommunityDoesNotExist("Community attempting to find posts from does not exist" + communityId);
		}
		
		Set<Post> posts = postDAO.findAllInCommunity(communityId);
		
		if (posts == null) {
			posts = new HashSet<Post>();
		}
		
		return posts;
	}

	public byte[] getImage(int postId) throws GetImageException, PostDoesNotExist {
		
		Post post = postDAO.findById(postId);
		
		if (post == null) {
			throw new PostDoesNotExist("No such post ID exists: " + postId);
		} 
//		
//		byte[] image = post.getImage();
		String filename = "post_" + postId + "_image";
		byte[] image = null;
		try {
			image = this.amazonS3Service.getFileFromS3Bucket(filename);
		} catch (IOException e) {
			log.error(e);
			throw new GetImageException("Unable to get image from S3");
		}
		
		return image;
	}
	
	public boolean deletePost(int postId) throws PostDoesNotExist {
		Post post = postDAO.findById(postId);
		
		if (post == null) {
			throw new PostDoesNotExist("No such post ID exists to delete: " + postId);
		}
		
		return postDAO.deletePost(post);
	}

	public Set<Post> getPostsByUserId(int userId) throws UserDoesNotExist {
		User u = userDAO.getUserById(userId);
		if (u == null) {
			log.error("User attempting to find posts from does not exist " + userId);
			throw new UserDoesNotExist("User attempting to find posts from does not exist" + userId);
		}
		
		Set<Post> posts = postDAO.findByAuthorId(u.getId());
		if (posts == null) {
			posts = new HashSet<>();
		}
		
		return posts;
	}
}
