package com.revature.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.revature.annotations.AuthorizedConsumer;
import com.revature.dto.AddCommentDTO;
import com.revature.dto.SendCommentDTO;
import com.revature.dto.SendPostDTO;
import com.revature.exceptions.AddCommentException;
import com.revature.exceptions.AddPostException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.exceptions.PostException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.CommentService;
import com.revature.util.HibernateUtility;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private HttpServletRequest req;

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
			commentDto.add(new SendCommentDTO(comment.getId(), comment.getText(), blankPasswordAuthor, comment.getTimePosted()));
		}
		
		return ResponseEntity.ok(commentDto);
	}
	
	@AuthorizedConsumer
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ResponseEntity<SendCommentDTO> addCommentToPost(@RequestBody AddCommentDTO dto) throws PostException, AddPostException, PostDoesNotExist, AddCommentException {
		
		log.info("addCommentToPost method invoked");
		
		User user = (User) req.getSession().getAttribute("currentUser");
		
		if (user == null) {
			throw new AddPostException("Unable to associate comment with a user");
		}
		
		Comment comment;
		
		comment = commentService.addCommentToPost(dto.getPostId(), dto.getText(), user);
		
		SendCommentDTO sendDto = new SendCommentDTO(comment.getId(), comment.getText(), comment.getAuthor(), comment.getTimePosted());
		
		return ResponseEntity.ok(sendDto);
	}

}
