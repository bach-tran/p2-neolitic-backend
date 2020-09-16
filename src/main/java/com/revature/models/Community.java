package com.revature.models;

public class Community {

	private int id;
	private String name;
	private String description;
	private int admin;
	
	
	public Community() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Community(int id, String name, String description, int admin) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.admin = admin;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getAdmin() {
		return admin;
	}


	public void setAdmin(int admin) {
		this.admin = admin;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + admin;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Community)) {
			return false;
		}
		Community other = (Community) obj;
		if (admin != other.admin) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "Community [id=" + id + ", name=" + name + ", description=" + description + ", admin=" + admin + "]";
	}
	
	

}