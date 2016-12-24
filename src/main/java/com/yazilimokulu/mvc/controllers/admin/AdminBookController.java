package com.yazilimokulu.mvc.controllers.admin;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.services.BookService;



@Controller
@RequestMapping(value = "/admin/book")
public class AdminBookController {
	
	@Autowired
	BookService bookService;
	
	private static final Logger logger = LogManager.getLogger(AdminBookController.class.getName());


	/*
	 * this resource contain book list
	 */
	@RequestMapping(value="")
	public String bookHome( Model model){
		BookDTO bookDTO = new BookDTO();
		model.addAttribute("bookDTO", bookDTO);
		return "admin/book/book_list";
	}
	
	/*
	 * this resource add book from book page
	 */
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String bookAdd( @Valid @ModelAttribute BookDTO bookDTO, BindingResult result){
		System.out.println(bookDTO.getAuthor());
		
		if (result.hasErrors()) {
			logger.error("user could not entry valid value");
			logger.error(result.getFieldErrorCount());
		} else {
			bookService.saveOrUpdateBook(bookDTO);
		}
		return "admin/book/book_add";
		
	}
	
	/*
	 * this resource contain add book page
	 */
	@RequestMapping(value="/add")
	public String addPage( Model model){
		BookDTO bookDTO = new BookDTO();
		model.addAttribute("bookDTO", bookDTO);
		return "admin/book/book_add";
	}
	
}