package com.yazilimokulu.mvc.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.yazilimokulu.mvc.entities.Tag;

public class TagWithPostCount {
	private Long postCount;
	@Autowired
	private Tag tag;

	public TagWithPostCount(Long postCount, Tag tag) {
		this.postCount = postCount;
		this.tag = tag;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Long getPostCount() {
		return postCount;
	}

	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}

}