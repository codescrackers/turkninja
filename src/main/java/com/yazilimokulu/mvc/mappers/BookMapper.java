package com.yazilimokulu.mvc.mappers;

import java.util.Date;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.entities.Book;

public class BookMapper {

	public static Book convertBookDTOToBook (BookDTO bookDTO) {
		Book book = new Book();
		book.setCreateDate(new Date());
		book.setUpdateDate(new Date());
		book.setAuthor(bookDTO.getAuthorList());
		book.setEdition(bookDTO.getEdition());
		book.setIsDeleted(false);
		book.setName(bookDTO.getName());
		book.setPageNumber(bookDTO.getPageNumber());
		book.setPublisher(bookDTO.getPublisher());
		
		return book;
	}
	
}
