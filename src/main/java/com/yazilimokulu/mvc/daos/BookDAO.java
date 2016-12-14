package com.yazilimokulu.mvc.daos;

import java.util.List;

import com.yazilimokulu.mvc.entities.Book;

public interface BookDAO {

	void saveOrUpdate(Book book);
	Book find(Long id);
	List<Book> findAll();
	
	
}
