package com.revature.models;

import java.io.Serializable;
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

@Entity
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Post implements Serializable {

	private static final long serialVersionUID = 895275161634396778L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@Column(nullable = false)
	private String caption;
	
	@ManyToOne
	@JoinColumn(name = "community_id")
	private Community community;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
		
	@Column(nullable = false)
	private Timestamp timePosted;
	
	public Post(int id, String caption, User author, Community community, Timestamp timePosted) {
		super();
		this.id = id;
		this.caption = caption;
		this.author = author;
		this.community = community;
		this.timePosted = timePosted;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", caption=" + caption + ", community=" + community + ", author=" + author
				+ ", timePosted=" + timePosted + "]";
	}
	
}
