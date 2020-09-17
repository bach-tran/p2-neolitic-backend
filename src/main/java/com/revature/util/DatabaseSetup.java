package com.revature.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Role;

public class DatabaseSetup {

	public static void main(String[] args) {
		Session s = HibernateUtility.getSession();
		Transaction tx = s.beginTransaction();
		
		// Add roles
		s.saveOrUpdate(new Role(1, "admin"));
		s.saveOrUpdate(new Role(2, "consumer"));
		
		tx.commit();
		
		HibernateUtility.closeSession();
	}
	
}
