package com.revature.dto;

import java.sql.Timestamp;

import com.revature.models.User;

public class SendCommentDTO {
	
	private int id;
	private String text;
	private User author;
	private Timestamp timePosted;
	
	public SendCommentDTO(int id, String text, User author, Timestamp timePosted) {
		super();
		this.id = id;
		this.text = text;
		this.author = author;
		this.timePosted = timePosted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
