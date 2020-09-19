package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @Setter @EqualsAndHashCode
public class Post implements Serializable {

	private static final long serialVersionUID = 895275161634396778L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private byte[] image;
	
	@Column(nullable = false)
	private String caption;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User author;
	
//	@OneToMany(mappedBy = "")
//	private Set<Comment> comments;
	
	@Column(nullable = false)
	private Timestamp timePosted;
	
	

	public Post(int id, byte[] image, String caption, User author, Timestamp timePosted) {
		super();
		this.id = id;
		this.image = image;
		this.caption = caption;
		this.author = author;
		this.timePosted = timePosted;
	}


	public int getId() {
		return id;
	}



	public byte[] getImage() {
		return image;
	}

	public String getCaption() {
		return caption;
	}

	public User getAuthor() {
		return author;
	}

	public Timestamp getTimePosted() {
		return timePosted;
	}
	
}
