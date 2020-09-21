package com.revature.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
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
	
	public static void main(String[] args) throws IOException {
		Session s = HibernateUtility.getSession();
		
		Transaction tx = s.beginTransaction();
		
		s.saveOrUpdate(new Role(1, "admin"));
		s.saveOrUpdate(new Role(2, "consumer"));
		
		tx.commit();
		
		s.clear();
		
		tx = s.beginTransaction();
		
		s.saveOrUpdate(new User(0, "bach_tran", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", "Bach", "Tran", new Role(2, "consumer")));
		s.saveOrUpdate(new Community(0, "Gaming", "For video games"));
		
		tx.commit();
		
		tx = s.beginTransaction();
		
//		Post newPost = new Post(0, new byte[10], "This is a fake picture", s.get(User.class, 1), s.get(Community.class, 1), new Timestamp(System.currentTimeMillis()));
//		s.persist(newPost);
//		s.get(Community.class, 1).getPosts().add(newPost);
//		
//		
		tx.commit();
		
//		System.out.println(s.get(Community.class, 1).getPosts());
		
//		tx.commit();
//		Post post = s.get(Post.class, 2);
//		System.out.println(post);
//		byte[] bytes = post.getImage();
////		log.info("byte array: " + Arrays.toString(bytes));
//		
//		// FILE WRITING STUFF
//		File file = new File("C:\\Users\\Bach_\\Desktop\\Project2\\neolitic-backend\\neolitic\\src\\main\\resources\\image.png");
//		OutputStream os = new FileOutputStream(file);
//		os.write(bytes); 
//        System.out.println("Successfully"
//                           + " byte inserted"); 
//
//        // Close the file 
//        os.close(); 
//		
		HibernateUtility.closeSession();
	}
	
}
