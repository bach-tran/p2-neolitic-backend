package com.revature.repositories;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
		try {
			s.saveOrUpdate(p);
			tx.commit();
			return p;
		} catch (Exception e) {
			tx.rollback();
			return null;
		}
	
	}

	@Override
	public Set<Post> findAllInCommunity(int communityId) {
		
		Session s = HibernateUtility.getSession();
		
		s.clear();
		
		Community c = s.get(Community.class, communityId);
		
		Query q = s.createQuery("FROM Post p WHERE p.community = :community");
		q.setParameter("community", c);
		
		Stream<Post> stream = q.getResultStream();
		Set<Post> posts = stream.collect(Collectors.toSet());
		
		return posts;
	}

}
