package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.entities.BookQuestionCategory;

public interface BookQuestionCategoryService {
	
	void saveOrUpdate(BookQuestionCategory category);
	List<BookQuestionCategory> findAll();

}
