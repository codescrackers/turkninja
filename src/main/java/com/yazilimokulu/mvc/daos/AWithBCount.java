package com.yazilimokulu.mvc.daos;

import org.springframework.beans.factory.annotation.Autowired;

import com.yazilimokulu.mvc.entities.Tag;

public class AWithBCount {
	private Long postCount;
	@Autowired
	private Tag tag;

	public AWithBCount(Long postCount, Tag tag) {
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