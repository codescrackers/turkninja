package com.yazilimokulu.mvc.dto;

import java.io.Serializable;

public class TagDTO implements Serializable {

	private static final long serialVersionUID = -8604968772097860578L;
	
	
	private String tagName;
	private long postCount;
	
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public long getPostCount() {
		return postCount;
	}
	public void setPostCount(long postCount) {
		this.postCount = postCount;
	}
	
	public TagDTO( String tagName, long postCount) {
		this.tagName = tagName;
		Long obj = new Long(postCount);
		this.postCount = obj.intValue();
	}
	
}
