package com.revature.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
public class Community {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private int admin;
	
//	@OneToMany(mappedBy="post")
//	Set<Post> posts;

	public Community(int id, String name, String description, int admin) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.admin = admin;
	}


	
	

}