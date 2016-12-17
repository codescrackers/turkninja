package com.yazilimokulu.mvc.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.BookChapter;

public class BookMapper {

	/*
	 * this method convert BookDTO to Book Entity Object
	 */
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
	
	/*
	 * this method convert Book to BookDTO Object
	 */
	public static BookDTO convertBookToBookDTO (Book book){
		BookDTO bookDTO= new BookDTO();
		bookDTO.setId(book.getId());
		bookDTO.setName(book.getName());
		List<BookChapterDTO> chapters= new ArrayList<>();
		bookDTO.setUrlUniqueName(book.getUrlUniqueName());
		for (BookChapter chapter : book.getChapters()) {
			chapters.add(BookChapterMapper.convertBookChapterToBookChapterDTO(chapter));
		}
		bookDTO.setChapters(chapters);
		return bookDTO;
	}
	
}
