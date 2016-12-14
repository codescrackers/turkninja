package com.yazilimokulu.mvc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class Book extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5542605971086015454L;

	private Long id;

	private String name;

	private String urlUniqueName;

	private Long pageNumber;

	private Collection<String> author = new ArrayList<>();

	private String publisher;

	private String edition;

	private Boolean isDeleted;

	List<BookChapter> chapters = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "URL_NAME")
	public String getUrlUniqueName() {
		return name.toLowerCase().replace(" ", "-");
	}

	public void setUrlUniqueName(String urlUniqueName) {
		this.urlUniqueName = urlUniqueName;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	public List<BookChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<BookChapter> chapters) {
		this.chapters = chapters;
	}

	@Column(name = "PAGE_NUMBER")
	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Column(name = "PUBLISHER")
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "EDITION")
	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	@Column(name = "DELETED")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@ElementCollection
	@CollectionTable(name = "BOOK_AUTHOR", joinColumns = @JoinColumn(name = "BOOK_ID"))
	@Column(name = "AUTHOR_NAME")
	public Collection<String> getAuthor() {
		return author;
	}

	public void setAuthor(Collection<String> author) {
		this.author = author;
	}

}
