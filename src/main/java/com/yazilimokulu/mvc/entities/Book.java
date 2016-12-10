package com.yazilimokulu.mvc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table( name = "BOOK")
public class Book extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5542605971086015454L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PAGE_NUMBER")
	private Long pageNumber;
	
	@ElementCollection
	@CollectionTable(name = "BOOK_AUTHOR", joinColumns = @JoinColumn(name = "BOOK_ID"))
	@Column(name = "AUTHOR_NAME")
	private Collection<String> author = new ArrayList<>();
	
	@Column(name = "PUBLISHER")
	private String publisher;
	
	@Column(name = "EDITION")
	private String edition;
	
	@Column(name = "DELETED")
	private Boolean isDeleted;

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

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Collection<String> getAuthor() {
		return author;
	}

	public void setAuthor(Collection<String> author) {
		this.author = author;
	}
	
	
}
