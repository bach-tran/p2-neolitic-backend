package com.revature.util;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Community;
import com.revature.models.Post;
import com.revature.models.Role;
import com.revature.models.User;

public class DatabaseSetup {

	private static Logger log = Logger.getLogger(DatabaseSetup.class);
	
	public static void main(String[] args) {
		Session s = HibernateUtility.getSession();
		
//		Transaction tx = s.beginTransaction();
//		
//		s.saveOrUpdate(new Role(1, "admin"));
//		s.saveOrUpdate(new Role(2, "consumer"));
//		
//		tx.commit();
//		
//		s.clear();
//		
//		tx = s.beginTransaction();
//		
//		s.saveOrUpdate(new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
//		s.saveOrUpdate(new Community(0, "Gaming", "For video games"));
//		
//		tx.commit();
		
		
		Post post = s.get(Post.class, 2);
		log.info("Here is the post object: " + post.toString());
		byte[] bytes = post.getImage();
//		log.info("byte array: " + Arrays.toString(bytes));
//		System.out.println(new Byte[10]);
		
		
		HibernateUtility.closeSession();
	}
	
}
