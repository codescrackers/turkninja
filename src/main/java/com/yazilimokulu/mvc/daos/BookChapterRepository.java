package com.yazilimokulu.mvc.daos;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.BookChapter;

@Repository
public interface BookChapterRepository  extends BaseRepository<BookChapter, Long> {
	
}
