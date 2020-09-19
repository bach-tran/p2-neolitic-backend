package com.revature.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.PostException;
import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.IPostDAO;

@Service
public class PostService {

	@Autowired
	private IPostDAO postDAO;
	
	private static Logger log = Logger.getLogger(PostService.class);
	
	public PostService() {
		super();
	}

//	public Post addPost(byte[] image,  String caption, Community community, User author) throws PostException {
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		Post p = new Post(0, image, caption, community, author, timestamp);
//		
//		
//		Post insertedPost = postDAO.insertPost(p);
//		
//		if(insertedPost == null) {
//			log.error("Post was not successfully inserted");
//			throw new PostException("Post was not successfully inserted.");
//		}
//		
//		return insertedPost;
//	}
	
	public Set<Post> getPosts(int communityId) {
		Set<Post> posts = postDAO.findAllInCommunity(communityId);
		
		if (posts == null) {
			posts = new HashSet<Post>();
		}
		
		return posts;
	}
}
