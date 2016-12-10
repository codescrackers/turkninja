package com.yazilimokulu.mvc.controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.services.BookService;

@Controller
@RequestMapping(value = "/admin/book")
public class BookController {
	
	@Autowired
	BookService bookService;

	@RequestMapping(value="")
	public String adminHome( Model model){
		BookDTO bookDTO = new BookDTO();
		model.addAttribute("bookDTO", bookDTO);
		return "book/book";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String bookAdd( @Valid @ModelAttribute BookDTO bookDTO){
		bookService.saveOrUpdateBook(bookDTO);
		return "book/book";
	}
	
}
