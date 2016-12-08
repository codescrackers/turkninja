package com.yazilimokulu.mvc.daos;


import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.BookQuestionCategory;

@Repository
public class BookCategoryDAOImpl extends AbstractDAO<BookQuestionCategory> implements BookCategoryDAO  {

	@Override
	public List<BookQuestionCategory> findAll() {
		return super.findAll(BookQuestionCategory.class);
	}

}
