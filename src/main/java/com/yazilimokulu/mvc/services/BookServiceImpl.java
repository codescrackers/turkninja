package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookDAO;
import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.mappers.BookMapper;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookDAO bookDAO;
	
	/**
	 * this method save or update book to database.
	 */
	@Override
	public void saveOrUpdateBook(BookDTO bookDTO) {
		Book book = BookMapper.convertBookDTOToBook(bookDTO);
		bookDAO.saveOrUpdate(book);
	}

	@Override
	public List<BookDTO> findAll() {
		// TODO Auto-generated method stub
		List<Book> books=bookDAO.findAll();
		List<BookDTO> bookDTOs=new ArrayList<>();
		for (Book book : books) {
			bookDTOs.add(BookMapper.convertBookToBookDTO(book));
		}
		return bookDTOs;
	}

	@Override
	public BookDTO find(Long id) {
		return BookMapper.convertBookToBookDTO(bookDAO.find(id));
	}
	

}
