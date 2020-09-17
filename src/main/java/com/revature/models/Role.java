package com.revature.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode @ToString
public class Role implements Serializable {

	private static final long serialVersionUID = 8733207848447836636L;
	
	@Id
	private int id;
	
	@Column(nullable = false)
	private String userRole;
	
}
