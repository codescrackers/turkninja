package com.yazilimokulu.mvc.services;

import com.yazilimokulu.mvc.dto.BookDTO;

public interface BookService {

	void saveOrUpdateBook(BookDTO bookDTO);
	
}
