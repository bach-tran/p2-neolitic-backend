package com.revature.models;

public class Role {

	private int id;
	private String userRole;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(int id, String userRole) {
		super();
		this.id = id;
		this.userRole = userRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		if (id != other.id) {
			return false;
		}
		if (userRole == null) {
			if (other.userRole != null) {
				return false;
			}
		} else if (!userRole.equals(other.userRole)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", userRole=" + userRole + "]";
	}
	
	
	
}
