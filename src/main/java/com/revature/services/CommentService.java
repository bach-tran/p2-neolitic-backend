package com.revature.services;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.AddCommentException;
import com.revature.exceptions.PostDoesNotExist;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.ICommentDAO;
import com.revature.repositories.IPostDAO;

@Service
public class CommentService {
	
	@Autowired
	private ICommentDAO commentDao;
	
	@Autowired
	private IPostDAO postDao;
	
	private Logger log = Logger.getLogger(CommentService.class);

	public Set<Comment> getCommentsFromPost(int postId) throws PostDoesNotExist {
		
		Post post = postDao.findById(postId);
		if (post == null) {
			log.error("Attempted to get comments from postID " + postId + ", but it does not appear to exist");
			throw new PostDoesNotExist("Attempted to get comments from postID " + postId + ", but it does not appear to exist");
		}
		
		Set<Comment> comments = commentDao.findAllInPost(postId);
		
		if (comments == null) {
			comments = new HashSet<Comment>();
		}
		
		return comments;
	}
	
	public Comment addCommentToPost(int postId, String text, User user) throws PostDoesNotExist, AddCommentException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Post post = postDao.findById(postId);
		if (post == null) {
			log.error("Attempted to add comment to postID " + postId + ", but it does not appear to exist");
			throw new PostDoesNotExist("Attempted to add comment to postID " + postId + ", but it does not appear to exist");
		}
		
		Comment c = new Comment(0, text, user, post);
		
		Comment insertedComment = commentDao.insertComment(c);
		if (insertedComment == null) {
			log.error("Comment was not successfully inserted");
			throw new AddCommentException("Comment was not successfully inserted");
		}
		
		return insertedComment;
	}

}
