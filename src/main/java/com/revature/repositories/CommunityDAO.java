package com.revature.repositories;

import java.util.Set;
import java.util.stream.Collectors;

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
		Transaction tx = s.beginTransaction();
		
		Set<Community> result = s.createQuery("FROM Community c", Community.class)
				.getResultStream()
				.collect(Collectors.toSet());
		
		tx.commit();
		
		return result;
	}

	@Override
	public Community findById(int id) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		return s.get(Community.class, id);
	}

	@Override
	public Community findByName(String name) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		return s.get(Community.class, name);
	}

	@Override
	public boolean insertCommunity(Community c) {
		// TODO Auto-generated method stub
		Session s = HibernateUtility.getSession();
		Transaction tx = s.beginTransaction();
		
		String id = (String) s.save(c);
		
		if(id != null && !id.equals("")) {
			tx.commit();
			return true;
		}
		
		tx.rollback();
		return false;
	}

}
