package com.yazilimokulu.mvc.daos;


import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.BookQuestionChapter;

@Repository
public class BookChapterDAOImpl extends AbstractDAO<BookQuestionChapter> implements BookChapterDAO  {

	@Override
	public List<BookQuestionChapter> findAll() {
		return super.findAll(BookQuestionChapter.class);
	}

}
