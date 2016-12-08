package com.yazilimokulu.mvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookChapterDAO;
import com.yazilimokulu.mvc.entities.BookQuestionChapter;

@Service
public class BookQuestionChapterServiceImpl implements BookQuestionChapterService{

	@Autowired
	BookChapterDAO bookCategoryDao;
	
	@Override
	public List<BookQuestionChapter> findAll() {
		return bookCategoryDao.findAll();
	}

	@Override
	public void saveOrUpdate(BookQuestionChapter category) {
		bookCategoryDao.saveOrUpdate(category);
		
	}

}
