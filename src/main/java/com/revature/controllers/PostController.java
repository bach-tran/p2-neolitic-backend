package com.revature.controllers;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.PostException;
import com.revature.models.Post;
import com.revature.services.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	private static Logger log = Logger.getLogger(PostController.class);
	
	@AuthorizedConsumer
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ResponseEntity<Set<SendPostDTO>> getPostsInCommunity(@RequestParam int communityId) throws PostException {
		log.info("getPosts method invoked");
		
		Set<Post> posts = postService.getPosts(communityId);
		
		Set<SendPostDTO> postsDto = new HashSet<>();
		for (Post post : posts) {
			postsDto.add(new SendPostDTO(post.getId(), post.getCaption(), post.getAuthor(), post.getTimePosted()));
		}
		
		return ResponseEntity.ok(postsDto);
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
