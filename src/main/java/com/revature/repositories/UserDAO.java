package com.revature.repositories;

import java.util.List;

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
import com.revature.models.User;
import com.revature.util.HibernateUtility;

@Repository
public class UserDAO implements IUserDAO {
	
	private Session session = HibernateUtility.getSession();
	
	@Override
	public User login(String username, String hashedPassword) throws LoginException {
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		
		Root<User> root = cq.from(User.class);
		
		cq.select(root).where(cb.and(cb.equal(root.get("username"), username), cb.equal(root.get("password"), hashedPassword)));
		
		try {
			User user = session.createQuery(cq).getSingleResult();
			return user;
		} catch (NoResultException e) {
			throw new LoginException("Unable to find a user that matches specified username and password");
		} 
		
	}
	
	@Override
	public User register(User u) throws RegistrationException {
		Transaction tx = session.beginTransaction();
		
		List<User> prexistingUser;
		
		Query query = session.createQuery("FROM User u WHERE u.username = :username");
		query.setParameter("username", u.getUsername());
		prexistingUser = query.getResultList();
		if (prexistingUser.size() == 1) {
			tx.rollback();
			throw new RegistrationException("User already exists");
		}
		
		try {
			session.persist(u);
			tx.commit();
		} catch(EntityExistsException e) {
			tx.rollback();
			throw new RegistrationException("User already exists");
		} catch (Exception e) {
			tx.rollback();
			throw(e);
		}
		
		return u;
	}

	@Override
	public User getAllUsers() {
		return null;
	}

}
