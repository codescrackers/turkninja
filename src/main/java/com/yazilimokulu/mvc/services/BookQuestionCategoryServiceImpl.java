package com.yazilimokulu.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookCategoryDAO;
import com.yazilimokulu.mvc.entities.BookQuestionCategory;

@Service
public class BookQuestionCategoryServiceImpl implements BookQuestionCategoryService{

	@Autowired
	BookCategoryDAO bookCategoryDao;
	
	@Override
	public List<BookQuestionCategory> findAll() {
		return bookCategoryDao.findAll();
	}

	@Override
	public void saveOrUpdate(BookQuestionCategory category) {
		bookCategoryDao.saveOrUpdate(category);
		
	}

}
