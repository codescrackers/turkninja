package com.yazilimokulu.mvc.daos;

import java.util.List;

import com.yazilimokulu.mvc.entities.BookChapter;

public interface BookChapterDAO {
	
	List<BookChapter> findAll();
	void saveOrUpdate(BookChapter chapter);
	BookChapter find(Long id);
	List<BookChapter> findByBookId(Long id);

}
