package com.yazilimokulu.mvc.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK_QUESTION")
public class BookQuestion extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1479634973428504940L;

	private Long id;
	
	private String name;

	private String urlUniqueName;

	private BookChapter bookChapter;

	private String answer;

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

	@ManyToOne
	@JoinColumn(name = "BOOK_CHAPTER_ID")
	public BookChapter getBookChapter() {
		return bookChapter;
	}

	public void setBookChapter(BookChapter bookChapter) {
		this.bookChapter = bookChapter;
	}

	@Column(name = "ANSWER")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOOK_QUESTION_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
