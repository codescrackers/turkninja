package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookChapterDAO;
import com.yazilimokulu.mvc.daos.BookDAO;
import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.BookChapter;
import com.yazilimokulu.mvc.mappers.BookChapterMapper;

@Service
public class BookChapterServiceImpl implements BookChapterService{

	@Autowired
	BookChapterDAO bookChapterDao;
	
	@Autowired
	BookDAO bookDAO;
	
	@Override
	public List<BookChapterDTO> findAll() {
		List<BookChapterDTO> chapters=new ArrayList<>();
		
		for (BookChapter chapter : bookChapterDao.findAll()) {
			chapters.add(BookChapterMapper.convertBookChapterToBookChapterDTO(chapter));
		} ;
		return chapters;
	}

	@Override
	public void saveOrUpdate(BookChapterDTO bookChapterDTO) {
		Book book = bookDAO.find(bookChapterDTO.getBook().getId());
		bookChapterDTO.setBook(book);
		bookChapterDao.saveOrUpdate(BookChapterMapper.convertBookChapterDTOToBookChapter(bookChapterDTO));
	}

	@Override
	public BookChapterDTO find(Long id) {
		return (BookChapterMapper.convertBookChapterToBookChapterDTO(bookChapterDao.find(id)));
	}

	@Override
	public List<BookChapterDTO> findByBookId(Long bookId) {
		List<BookChapterDTO> chapters= new ArrayList<>();
		for (BookChapter chapter : bookChapterDao.findByBookId(bookId)) {
			chapters.add(BookChapterMapper.convertBookChapterToBookChapterDTO(chapter));
		}
		return chapters;
	}
	
	
	
	

}
