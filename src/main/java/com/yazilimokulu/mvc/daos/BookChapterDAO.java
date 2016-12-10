package com.yazilimokulu.mvc.daos;

import java.util.List;

import com.yazilimokulu.mvc.entities.BookQuestionChapter;

public interface BookChapterDAO {
	
	List<BookQuestionChapter> findAll();
	void saveOrUpdate(BookQuestionChapter chapter);

}
