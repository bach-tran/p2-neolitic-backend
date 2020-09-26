package com.revature.repositories;

import java.util.List;
import java.util.Set;

import com.revature.models.Community;
import com.revature.models.Post;


public interface IPostDAO {

	public List<Post> findAll();
	public Set<Post> findAllInCommunity(int communityId);

	public Post findById(int id);
	
	public Set<Post> findByAuthorId(int id);
	
	public Post insertPost(Post p);
	
	public boolean deletePost(Post p);
	
}
