package com.yazilimokulu.mvc.dto;

import java.io.Serializable;

public class TagDTO implements Serializable {

	private static final long serialVersionUID = -8604968772097860578L;
	
	
	private String tagName;
	private short postCount;
	
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public short getPostCount() {
		return postCount;
	}
	public void setPostCount(short postCount) {
		this.postCount = postCount;
	}
	
	public TagDTO( String tagName, int postCount) {
		this.tagName = tagName;
		Integer obj = new Integer(postCount);
		this.postCount = obj.shortValue();
	}
	
}
