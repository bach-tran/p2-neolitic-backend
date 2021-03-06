package com.revature.repositories;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.revature.exceptions.LoginException;
import com.revature.exceptions.RegistrationException;
import com.revature.models.Community;
import com.revature.models.User;
import com.revature.util.HibernateUtility;

@Repository
public class UserDAO implements IUserDAO {
	
	@Override
	public User login(String username, String hashedPassword) throws LoginException {	
		Session session = HibernateUtility.getSession();
		session.clear();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		
		Root<User> root = cq.from(User.class);
		
		cq.select(root).where(cb.and(cb.equal(root.get("username"), username), cb.equal(root.get("password"), hashedPassword)));
		
		Query query = session.createQuery(cq);
		query.setMaxResults(1);
		
		List<User> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			session.close();
			return null;
		}
		
		session.close();
		return list.get(0);
	}
	
	@Override
	public User register(User u) throws RegistrationException {
		Session session = HibernateUtility.getSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("FROM User u WHERE u.username = :username");
		query.setParameter("username", u.getUsername());
		List<User> prexistingUser = query.getResultList();
		if (prexistingUser.size() == 1) {
			tx.rollback();
			session.close();
			throw new RegistrationException("User already exists");
		}
		
		try {
			session.persist(u);
			tx.commit();
		} catch(EntityExistsException e) {
			tx.rollback();
			session.close();
			throw new RegistrationException("User already exists");
		} catch (Exception e) {
			tx.rollback();
			session.close();
			throw(e);
		}
		
		session.close();
		return u;
	}

	@Override
	public Set<User> getAllUsers() {
		Session s = HibernateUtility.getSession();
		
		Set<User> result = s.createQuery("FROM User u", User.class)
				.getResultStream()
				.collect(Collectors.toSet());
		
		s.close();
		
		return result;
	}

	@Override
	public User getUserById(int userId) {
		Session s = HibernateUtility.getSession();
		User user = s.get(User.class, userId);
		
		s.close();
		return user;
	}

}
