package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.dto.BookDTO;


public interface BookService {

	void saveOrUpdateBook(BookDTO bookDTO);
	
	List<BookDTO> findAll();
	
	BookDTO find(Long id);
	
}
