package com.revature.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.SendCommentDTO;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	private static Logger log = Logger.getLogger(CommentController.class);
	
	@AuthorizedConsumer
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ResponseEntity<Set<SendCommentDTO>> getComments(@RequestParam int postId) throws PostException, PostDoesNotExist {
		log.info("getComments method invoked");
		
		Set<Comment> comments = commentService.getCommentsFromPost(postId);
		
		Set<SendCommentDTO> commentDto = new HashSet<>();
		for (Comment comment : comments) {
			User blankPasswordAuthor = new User(comment.getAuthor().getId(), comment.getAuthor().getUsername(), "", comment.getAuthor().getFirstName(), comment.getAuthor().getLastName(),
					comment.getAuthor().getRole());
			commentDto.add(new SendCommentDTO(comment.getId(), comment.getText(), blankPasswordAuthor));
		}
		
		return ResponseEntity.ok(commentDto);
	}
	
//	@AuthorizedConsumer
//	@RequestMapping(value = "/post", method = RequestMethod.POST)
//	public ResponseEntity<SendPostDTO> addPostToCommunity(@RequestParam("communityId") int communityId, @RequestParam("caption") String caption, 
//			@RequestParam("file") MultipartFile file, HttpServletRequest req) throws PostException, AddPostException {
//		
//		log.info("addPostToCommunity method invoked");
//		
//		User user = (User) req.getSession().getAttribute("currentUser");
//		
//		if (user == null) {
//			throw new AddPostException("Unable to associate post with a user");
//		}
//		
//		Post post;
//		SendPostDTO dto;
//		try {
//			post = postService.addPost(communityId, caption, file, user);
//		} catch (IOException e) {
//			log.error(e);
//			throw new AddPostException(e);
//		}
//		
//		dto = new SendPostDTO(post.getId(), post.getCaption(), post.getAuthor(), post.getTimePosted());
//		
//		return ResponseEntity.ok(dto);
//	}

}
