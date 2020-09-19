package com.revature.repositories;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.util.HibernateUtility;

@Repository
public class PostDAO implements IPostDAO{

	@Override
	public List<Post> findAll() {
		Session s = HibernateUtility.getSession();
		
		List<Post> result = s.createQuery("FROM Post p", Post.class)
				.getResultStream()
				.collect(Collectors.toList());
		
		return result;
	}

	
	@Override
	public Post findById(int id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		return s.get(Post.class, id);
	}

	@Override
	public Post findByAuthorId(int id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		
		Query q = s.createQuery("FROM Post p WHERE p.author = :author_id");
		q.setParameter("author_id", id);
		
		Post result = (Post) q.getSingleResult();
		
		return result;
	}

	@Override
	public Post insertPost(Post p) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		Transaction tx = s.beginTransaction();
		
		Integer id = (Integer) s.save(p);
		
		if(id != null && !(id == 0)) {
			tx.commit();
			return p;
		}
		
		tx.rollback();
		return null;
	}

	@Override
	public Set<Post> findAllInCommunity(int communityId) {
		
		Session s = HibernateUtility.getSession();
		
		s.clear();
		
		Set<Post> posts = s.get(Community.class, communityId).getPosts();
		
		return posts;
	}

}
