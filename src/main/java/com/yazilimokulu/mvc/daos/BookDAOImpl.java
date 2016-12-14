package com.yazilimokulu.mvc.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yazilimokulu.mvc.entities.Book;

@Repository
public class BookDAOImpl extends AbstractDAO<Book> implements BookDAO {

	@Override
	public Book find(Long id) {
		return find(Book.class, id);
	}

	@Override
	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return super.findAll(Book.class);
	}
	
	
}
