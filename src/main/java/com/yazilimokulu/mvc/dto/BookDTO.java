package com.yazilimokulu.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.yazilimokulu.mvc.entities.BookChapter;

public class BookDTO implements Serializable {

	private static final long serialVersionUID = 6764166886762499254L;

	private Long id;

	@NotEmpty(message = "{error.required}")
	private String name;

	@NotNull(message = "{error.required}")
	@Range(min = 1, max = 1500, message = " Lütfen 1-1500 arasında değer giriniz")
	private Long pageNumber;

	/*
	 * author data must parse by ","
	 */
	
	private List<String> authors;

	@NotEmpty(message = "{error.required}")
	private String publisher;

	@NotEmpty(message = "{error.required}")
	private String edition;

	private String urlUniqueName;
	
	private List<BookChapterDTO> chapters;
	
	@NotEmpty(message = "{error.required}")
	private String author;

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

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	

	public String getAuthors() {
		String authorList="";
		for (int i = 0; i < authors.size(); i++) {
			if(i !=authors.size()-1){
			authorList +=authors.get(i)+", ";
			}else{
				authorList +=authors.get(i);
			}
		}
		return authorList;
	}

	
	public List<String> getAuthorList() {
		List<String> authorList = new ArrayList<>();
		String[] authors = this.author.split(",");

		for (String author : authors) {
			authorList.add(author);
		}

		return authorList;
	}
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BookChapterDTO> getChapters() {
		return chapters;
	}

	public void setChapters(List<BookChapterDTO> chapters) {
		this.chapters = chapters;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	

}
