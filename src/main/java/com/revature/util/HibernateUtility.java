package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.revature.models.Role;

public class HibernateUtility {
	
	private static Session session;
	
	// Static initializer
	// Will run when the class is loaded initially
//	static {
//		
//	}
	
	private static SessionFactory sf =
			new Configuration().configure("hibernate.cfg.xml")
				.buildSessionFactory();
	
	private HibernateUtility() {
		super();
	}
	
	public static Session getSession() {
//		if(session == null || !session.isOpen()) {
//			session = sf.openSession();
//		}
		
		return sf.openSession();
	}
	
//	public static void closeSession() {
//		session.close();
//	}
	
}
