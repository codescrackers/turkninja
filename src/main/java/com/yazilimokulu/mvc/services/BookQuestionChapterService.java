package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.entities.BookQuestionChapter;

public interface BookQuestionChapterService {
	
	void saveOrUpdate(BookQuestionChapter category);
	List<BookQuestionChapter> findAll();

}
