package com.revature.dto;

import com.revature.models.Community;
import com.revature.models.User;

public class AddPostDTO {
	
	private byte[] image;
	private String caption;
	private Community c;
	private User author;
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Community getC() {
		return c;
	}
	public void setC(Community c) {
		this.c = c;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	

}
