package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
		if(session == null || !session.isOpen()) {
			session = sf.openSession();
		}
		
		return session;
	}
	
	public static void closeSession() {
		session.close();
	}
}
