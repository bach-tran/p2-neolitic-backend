package com.revature.models;

import java.sql.Timestamp;
import java.util.Arrays;

public class Postcard {

	private int id;
	private byte[] image;
	private String caption;
	private Community community;
	private int author;
	private Timestamp timePosted;
		
	public Postcard() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Postcard(int id, byte[] image, String caption, Community community, int author, Timestamp timePosted) {
		super();
		this.id = id;
		this.image = image;
		this.caption = caption;
		this.community = community;
		this.author = author;
		this.timePosted = timePosted;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


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


	public Community getCommunity() {
		return community;
	}


	public void setCommunity(Community community) {
		this.community = community;
	}


	public int getAuthor() {
		return author;
	}


	public void setAuthor(int author) {
		this.author = author;
	}


	public Timestamp getTimePosted() {
		return timePosted;
	}


	public void setTimePosted(Timestamp timePosted) {
		this.timePosted = timePosted;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + author;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((community == null) ? 0 : community.hashCode());
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((timePosted == null) ? 0 : timePosted.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Postcard)) {
			return false;
		}
		Postcard other = (Postcard) obj;
		if (author != other.author) {
			return false;
		}
		if (caption == null) {
			if (other.caption != null) {
				return false;
			}
		} else if (!caption.equals(other.caption)) {
			return false;
		}
		if (community == null) {
			if (other.community != null) {
				return false;
			}
		} else if (!community.equals(other.community)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!Arrays.equals(image, other.image)) {
			return false;
		}
		if (timePosted == null) {
			if (other.timePosted != null) {
				return false;
			}
		} else if (!timePosted.equals(other.timePosted)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "Postcard [id=" + id + ", image=" + Arrays.toString(image) + ", caption=" + caption + ", community="
				+ community + ", author=" + author + ", timePosted=" + timePosted + "]";
	}

	
	
	
	
}
