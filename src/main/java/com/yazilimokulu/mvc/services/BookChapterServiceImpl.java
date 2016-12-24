package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookChapterRepository;
import com.yazilimokulu.mvc.daos.BookRepository;
import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.BookChapter;
import com.yazilimokulu.mvc.mappers.BookChapterMapper;

@Service
public class BookChapterServiceImpl implements BookChapterService{

	@Autowired
	BookChapterRepository bookChapterRepository;
	
	@Autowired
	BookRepository bookDAO;
	
	@Override
	public List<BookChapterDTO> findAll() {
		List<BookChapterDTO> chapters=new ArrayList<>();
		
		for (BookChapter chapter : bookChapterRepository.findAll()) {
			chapters.add(BookChapterMapper.convertBookChapterToBookChapterDTO(chapter));
		} ;
		return chapters;
		
	}

	@Override
	public void saveOrUpdate(BookChapterDTO bookChapterDTO) {
		Book book = bookDAO.findOne(bookChapterDTO.getBook().getId());
		bookChapterDTO.setBook(book);
		bookChapterRepository.save(BookChapterMapper.convertBookChapterDTOToBookChapter(bookChapterDTO));
	}

	@Override
	public BookChapterDTO find(Long id) {
		return (BookChapterMapper.convertBookChapterToBookChapterDTO(bookChapterRepository.findOne(id)));
	}

	
	
	
	

}
