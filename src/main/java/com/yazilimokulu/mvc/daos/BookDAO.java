package com.yazilimokulu.mvc.daos;

import com.yazilimokulu.mvc.entities.Book;

public interface BookDAO {

	void saveOrUpdate(Book book);
	
}
