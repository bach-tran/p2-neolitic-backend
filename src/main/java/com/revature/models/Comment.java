package com.revature.models;

import java.sql.Timestamp;

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
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	private Timestamp timePosted;

	public Comment(int id, String text, User author, Post post, Timestamp timePosted) {
		super();
		this.id = id;
		this.text = text;
		this.author = author;
		this.post = post;
		this.timePosted = timePosted;
	}
	
}
