package com.revature.repositories;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.revature.models.Community;
import com.revature.util.HibernateUtility;

@Repository
public class CommunityDAO implements ICommunityDAO{

	@Override
	public Set<Community> findAll() {
		
		Session s = HibernateUtility.getSession();
		
		Set<Community> result = s.createQuery("FROM Community c", Community.class)
				.getResultStream()
				.collect(Collectors.toSet());
		
		s.close();
		return result;
	}

	@Override
	public Community findById(int id) {
		Session s = HibernateUtility.getSession();
		Community community = s.get(Community.class, id);
		
		s.close();
		return community;
	}

	@Override
	public Community findByName(String name) {
		Session s = HibernateUtility.getSession();
		
		Query q = s.createQuery("FROM Community c WHERE lower(c.name) LIKE lower(:community_name)");
		q.setParameter("community_name", name);
		
		try {
			Community result = (Community) q.getSingleResult();
			s.close();
			return result;
		} catch(NoResultException e) {
			s.close();
			return null;
		}
	}

	@Override
	public Community insertCommunity(Community c) {
		Session s = HibernateUtility.getSession();
		Transaction tx = s.beginTransaction();
		
		Integer id = (Integer) s.save(c);
		
		if(id != null && !(id == 0)) {
			tx.commit();
			s.close();
			return c;
		}
		
		tx.rollback();
		s.close();
		return null;
	}

	
}
