package com.yazilimokulu.mvc.mappers;

import java.util.Date;

import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.entities.BookChapter;

public class BookChapterMapper {
	
	public static BookChapter convertBookChapterDTOToBookChapter (BookChapterDTO bookChapterDTO) {
		BookChapter bookChapter = new BookChapter();
		bookChapter.setCreateDate(new Date());
		bookChapter.setUpdateDate(new Date());
		bookChapter.setName(bookChapterDTO.getName());
		bookChapter.setBook(bookChapterDTO.getBook());
		return bookChapter;
	}
	
	public static BookChapterDTO convertBookChapterToBookChapterDTO (BookChapter bookChapter){
		BookChapterDTO bookChapterDTO= new BookChapterDTO();
		bookChapterDTO.setId(bookChapter.getId());
		bookChapterDTO.setName(bookChapter.getName());
		bookChapterDTO.setUrlUniqueName(bookChapter.getUrlUniqueName());
		return bookChapterDTO;
	}

}
