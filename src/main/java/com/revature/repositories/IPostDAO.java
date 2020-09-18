package com.revature.repositories;

import java.util.List;

import com.revature.models.Community;
import com.revature.models.Post;


public interface IPostDAO {

public List<Post> findAll();
public List<Post> findAllInCommunity(Community c);

	public Post findById(int id);
	
	public Post findByAuthorId(int id);
	
	public Post insertPost(Post p);
	
}
