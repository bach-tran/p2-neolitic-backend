package com.revature.repositories;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.revature.exceptions.LoginException;
import com.revature.models.User;
import com.revature.util.HibernateUtility;
import com.sun.javafx.fxml.expression.BinaryExpression;
import com.sun.javafx.fxml.expression.Expression;

@Repository
public class UserDAO implements IUserDAO {
	
	private Session session = HibernateUtility.getSession();
	
	@Override
	public User login(String username, String hashedPassword) throws LoginException {
//		Transaction tx = session.beginTransaction();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		
		Root<User> root = cq.from(User.class);
		
		cq.select(root).where(cb.and(cb.equal(root.get("username"), username), cb.equal(root.get("password"), hashedPassword)));
		
		try {
			User user = session.createQuery(cq).getSingleResult();
			
//			tx.commit();
			return user;
		} catch (NoResultException e) {
			throw new LoginException("Unable to find a user that matches specified username and password");
		}
		
	}

	@Override
	public User getAllUsers() {
		return null;
	}

	@Override
	public User register(User u) {
		return null;
	}

}
