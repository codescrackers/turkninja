package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.dto.BookChapterDTO;

public interface BookChapterService {
	
	void saveOrUpdate(BookChapterDTO chapterDTO);
	List<BookChapterDTO> findAll();
	BookChapterDTO find(Long id);
	

}
