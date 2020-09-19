package com.revature.dto;

import java.sql.Timestamp;

import com.revature.models.User;

public class SendPostDTO {

	private int id;
	private String caption;
	private User author;
	private Timestamp timePosted;
	
	public SendPostDTO(int id, String caption, User author, Timestamp timePosted) {
		super();
		this.id = id;
		this.caption = caption;
		this.author = author;
		this.timePosted = timePosted;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Timestamp getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(Timestamp timePosted) {
		this.timePosted = timePosted;
	}
	
}
