package com.revature.models;

public class Comment {

	private int id;
	private String text;
	private int parentComment;
	private int post;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, String text, int parentComment, int post) {
		super();
		this.id = id;
		this.text = text;
		this.parentComment = parentComment;
		this.post = post;
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

	public int getParentComment() {
		return parentComment;
	}

	public void setParentComment(int parentComment) {
		this.parentComment = parentComment;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + parentComment;
		result = prime * result + post;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Comment)) {
			return false;
		}
		Comment other = (Comment) obj;
		if (id != other.id) {
			return false;
		}
		if (parentComment != other.parentComment) {
			return false;
		}
		if (post != other.post) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", parentComment=" + parentComment + ", post=" + post + "]";
	}
	
	
	
}
