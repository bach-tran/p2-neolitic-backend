package com.revature.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.AddPostDTO;
import com.revature.exceptions.PostException;
import com.revature.models.Post;
import com.revature.services.PostService;

public class PostController {
	
	@Autowired
	private PostService postService;
	
	private static Logger log = Logger.getLogger(PostController.class);
	
	@AuthorizedConsumer
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> getPostsInCommunity(@RequestBody AddPostDTO dto) throws PostException {
		log.info("getPosts method invoked");
		
		
		List<Post> posts = postService.getPosts(dto.getC());
		
		return ResponseEntity.ok(posts);
	}
	
//	@AuthorizedConsumer
//	@RequestMapping(value = "/post", method = RequestMethod.GET)
//	public ResponseEntity<Post> addPostToCommunity(@RequestBody AddPostDTO dto) throws PostException {
//		log.info("getPosts method invoked");
//		
//		
//		Post post = postService.addPost(dto.getImage(), dto.getCaption(), dto.getC(), dto.getAuthor());
//		
//		return ResponseEntity.ok(post);
//	}
	

}
