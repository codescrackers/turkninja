package com.yazilimokulu.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.services.BookChapterService;
import com.yazilimokulu.mvc.services.BookService;

@Controller
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	BookChapterService bookChapterService;
	
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String bookChapters(@RequestParam String bookId, Model model){
		
		BookDTO bookDTO=bookService.find(Long.parseLong(bookId) );
		List<BookChapterDTO> bookChapterDTOs=bookDTO.getChapters(); 
		model.addAttribute("bookChapters", bookChapterDTOs);
		model.addAttribute("book", bookDTO);
		return "book_chapter_list";
		
	}

}
