package com.yazilimokulu.mvc.daos;

import java.util.List;

import com.yazilimokulu.mvc.entities.BookQuestionCategory;

public interface BookCategoryDAO {
	
	List<BookQuestionCategory> findAll();
	void saveOrUpdate(BookQuestionCategory category);

}
