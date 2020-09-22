package com.revature.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.CommunityDoesNotExist;
import com.revature.exceptions.GetImageException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;
import com.revature.util.HibernateUtility;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private Tika tika;
	
	private static Logger log = Logger.getLogger(PostController.class);
		
	@AuthorizedConsumer
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ResponseEntity<Set<SendPostDTO>> getPostsInCommunity(@RequestParam int communityId) throws CommunityDoesNotExist {
		log.info("getPosts method invoked");
		
		Set<Post> posts = postService.getPosts(communityId);
		
		Set<SendPostDTO> postsDto = new HashSet<>();
		for (Post post : posts) {
			User blankPasswordAuthor = new User(post.getAuthor().getId(), post.getAuthor().getUsername(), "", post.getAuthor().getFirstName(), post.getAuthor().getLastName(),
					post.getAuthor().getRole());
			postsDto.add(new SendPostDTO(post.getId(), post.getCaption(), blankPasswordAuthor, post.getTimePosted()));
		}
		
		HibernateUtility.closeSession();
		
		return ResponseEntity.ok(postsDto);
	}
	
	@AuthorizedConsumer
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<SendPostDTO> addPostToCommunity(@RequestParam("communityId") int communityId, @RequestParam("caption") String caption, 
			@RequestParam("file") MultipartFile file, HttpServletRequest req) throws AddPostException, CommunityDoesNotExist {
		
		log.info("addPostToCommunity method invoked");
		
		User user = (User) req.getSession().getAttribute("currentUser");
		
		if (user == null) {
			throw new AddPostException("Unable to associate post with a user");
		}
		
		Post post;
		SendPostDTO dto;
		try {
			post = postService.addPost(communityId, caption, file, user);
		} catch (IOException e) {
			log.error(e);
			throw new AddPostException(e);
		}
		
		dto = new SendPostDTO(post.getId(), post.getCaption(), post.getAuthor(), post.getTimePosted());
		
		HibernateUtility.closeSession();
		
		return ResponseEntity.ok(dto);
	}
	
//	@AuthorizedConsumer
	@RequestMapping(value = "/post/image/{ID}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable(value="ID") int postId, HttpServletResponse resp) throws PostDoesNotExist, GetImageException {
		
		byte[] image = postService.getImage(postId);
		
		String contentType = tika.detect(image);
		
		MediaType type = MediaType.parseMediaType(contentType);
		
		HibernateUtility.closeSession();
		
		return ResponseEntity.ok().contentType(type).body(image);
	}

}
