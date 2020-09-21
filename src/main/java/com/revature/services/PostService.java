package com.revature.services;

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

import com.revature.exceptions.AddPostException;
import com.revature.exceptions.CommunityDoesNotExist;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.ICommunityDAO;
import com.revature.repositories.IPostDAO;

@Service
public class PostService {

	@Autowired
	private IPostDAO postDAO;
	
	@Autowired
	private ICommunityDAO communityDAO;
	
	private static Logger log = Logger.getLogger(PostService.class);
	
	public PostService() {
		super();
	}

	public Post addPost(int communityId, String caption, MultipartFile file, User user) throws AddPostException, CommunityDoesNotExist, IOException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Community c = communityDAO.findById(communityId);
		if (c == null) {
			log.error("Community attempting to add post to does not exist");
			throw new CommunityDoesNotExist("Community attempting to add post to does not exist");
		}
		
		byte[] image = file.getBytes();
		String contentType = file.getContentType();
		MediaType mt = MediaType.parseMediaType(contentType);
		
		String subtype = mt.getSubtype();
		
		if (!(subtype.equals("jpeg") || subtype.equals("jpg") || subtype.equals("bmp") || subtype.equals("gif") || subtype.equals("png") || subtype.equals("tiff"))) {
			throw new AddPostException("File upload does not correspond to jpeg, bmp, gif, png, or tiff");
		}
		
		Post p = new Post(0, image, caption, user, c, timestamp);
		
		Post insertedPost = postDAO.insertPost(p);
		
		if(insertedPost == null) {
			log.error("Post was not successfully inserted");
			throw new AddPostException("Post was not successfully inserted.");
		}
		
		return insertedPost;
	}
	
	public Set<Post> getPosts(int communityId) throws CommunityDoesNotExist {
		
		Community c = communityDAO.findById(communityId);
		if (c == null) {
			log.error("Community attempting to find posts from does not exist");
			throw new CommunityDoesNotExist("Community attempting to find posts from does not exist");
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
			throw new PostDoesNotExist("No such post ID exists");
		} 
		
		byte[] image = post.getImage();
		return image;
		
	}
}
