package com.yazilimokulu.mvc.daos;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.Book;

@Repository
public class BookDAOImpl extends AbstractDAO<Book> implements BookDAO {

	public Book find(Long id) {
		return find(Book.class, id);
	}
	
}
