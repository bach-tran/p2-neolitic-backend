package com.revature.models;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private byte[] image;
	
	@Column(nullable = false)
	private String caption;
	
	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
//	@OneToMany(mappedBy = "")
//	private Set<Comment> comments;
	
	@Column(nullable = false)
	private Timestamp timePosted;
		

	public Post(int id, Blob image, String caption, Community community, User author, Timestamp timePosted) {
		super();
		this.id = id;
//		this.image = image;
		this.caption = caption;
		this.community = community;
		this.author = author;
		this.timePosted = timePosted;
	}


	@Override
	public String toString() {
		return "Post [id=" + id + ", caption=" + caption + ", community=" + community + ", author=" + author
				+ ", timePosted=" + timePosted + "]";
	}
	
}
