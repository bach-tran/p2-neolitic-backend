package com.revature.repositories;

import java.util.Set;

import com.revature.models.Comment;

public interface ICommentDAO {
	
	public Set<Comment> findAllInPost(int postId);
	
	public Comment insertComment(Comment c);

}
