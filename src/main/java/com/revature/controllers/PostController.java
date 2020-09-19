package com.revature.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.PostException;
import com.revature.models.Post;
import com.revature.models.User;
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
			post.getAuthor().setPassword("");
			postsDto.add(new SendPostDTO(post.getId(), post.getCaption(), post.getAuthor(), post.getTimePosted()));
		}
		
		return ResponseEntity.ok(postsDto);
	}
	
	@AuthorizedConsumer
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public /*ResponseEntity<Post>*/ void addPostToCommunity(@RequestParam("communityId") int communityId, @RequestParam("caption") String caption, 
			@RequestParam("file") MultipartFile file, HttpServletRequest req) throws PostException, AddPostException {
		
		log.info("addPostToCommunity method invoked");
		
		User user = (User) req.getSession().getAttribute("currentUser");
		
		if (user == null) {
			throw new AddPostException("Unable to associate post with a user");
		}
		
		Post post;
		try {
			/*post =*/ postService.addPost(communityId, caption, file, user);
		} catch (IOException e) {
			log.error(e);
			throw new AddPostException(e);
		}
		
//		return ResponseEntity.ok(post);
	}
	

}
