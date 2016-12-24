package com.yazilimokulu.mvc.daos;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.Book;

@Repository
public interface BookRepository  extends BaseRepository<Book, Long>{

	
}
