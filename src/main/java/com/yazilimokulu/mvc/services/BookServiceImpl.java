package com.yazilimokulu.mvc.services;

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
	
	@Override
	public void saveOrUpdateBook(BookDTO bookDTO) {
		Book book = BookMapper.convertBookDTOToBook(bookDTO);
		bookDAO.saveOrUpdate(book);
	}

}
