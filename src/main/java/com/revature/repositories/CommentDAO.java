package com.revature.repositories;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.util.HibernateUtility;

@Repository
public class CommentDAO implements ICommentDAO {

	@Override
	public Set<Comment> findAllInPost(int postId) {
		Session session = HibernateUtility.getSession();
		
		session.clear();
		
		Post p = session.get(Post.class, postId);
		
		Query q = session.createQuery("FROM Comment c WHERE c.post = :post");
		q.setParameter("post", p);
		
		Stream<Comment> stream = q.getResultStream();
		Set<Comment> comments = stream.collect(Collectors.toSet());
		
		session.close();
		return comments;
	}

	@Override
	public Comment insertComment(Comment c) {
		Session s = HibernateUtility.getSession();
		Transaction tx = s.beginTransaction();
		
		try {
			s.saveOrUpdate(c);
			tx.commit();
			s.close();
			return c;
		} catch (Exception e) {
			tx.rollback();
			s.close();
			return null;
		}
	}

}
