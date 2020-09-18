package com.revature.models;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private byte[] image;
	
	@Column(nullable = false)
	private String caption;
	
	@Column(nullable = false)
	private Community community;
	
	@Column(nullable = false)
	private int author;
	
	@Column(nullable = false)
	private Timestamp timePosted;
		

	public Post(int id, byte[] image, String caption, Community community, int author, Timestamp timePosted) {
		super();
		this.id = id;
		this.image = image;
		this.caption = caption;
		this.community = community;
		this.author = author;
		this.timePosted = timePosted;
	}


	
	
	
	
	
}
