package com.yazilimokulu.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class BookDTO implements Serializable {

	private static final long serialVersionUID = 6764166886762499254L;

	@NotEmpty(message = "Boş bırakılamaz")
	private String name;
	
	@NotNull(message = "Boş bırakılamaz")
	@Range(min = 1, max = 1500, message = " Lütfen 1-1500 arasında değer giriniz")
	private Long pageNumber;
	
	/*
	 * author data must parse by ","
	 */
	@NotEmpty(message = "Boş bırakılamaz")
	private String author;
	
	@NotEmpty(message = "Boş bırakılamaz")
	private String publisher;
	
	@NotEmpty(message = "Boş bırakılamaz")
	private String edition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Collection<String> getAuthorList() {
		Collection<String> authorList = new ArrayList<>();
		String[] authors = this.author.split(",");
		
		for (String author : authors) {
			authorList.add(author);
		}
		
		return authorList;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}
	
}
