package com.yazilimokulu.mvc.dto;

import java.io.Serializable;

import com.yazilimokulu.mvc.entities.Book;

public class BookChapterDTO implements Serializable {
	
	private static final long serialVersionUID = 3647494136989090412L;

	private Long id;
	
	private String name;

	private String urlUniqueName;
	
	private Book book;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlUniqueName() {
		return urlUniqueName;
	}

	public void setUrlUniqueName(String urlUniqueName) {
		this.urlUniqueName = urlUniqueName;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
}
