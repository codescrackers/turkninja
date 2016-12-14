package com.yazilimokulu.mvc.controllers.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yazilimokulu.mvc.dto.BookChapterDTO;
import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.entities.BookChapter;
import com.yazilimokulu.mvc.services.BookChapterService;
import com.yazilimokulu.mvc.services.BookService;

@Controller
@RequestMapping(value = "/admin/book-chapter")
public class AdminBookChapterController {
	
	@Autowired
	BookChapterService bookQuestionChapterService;
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(value="")
	public String bookHome( Model model){
		return "admin/book/book_chapter_list";
	}

	@RequestMapping(value="/add")
	public String addBookQuestionChapter( Model model){
		
		List<BookDTO> books=bookService.findAll();
		Map<String,String> bookMap= new HashMap<>();
		for (BookDTO bookDTO : books) {
			bookMap.put(bookDTO.getId().toString(), bookDTO.getName());	
		}
		model.addAttribute("books", bookMap);
		BookChapterDTO chapter = new BookChapterDTO();
		model.addAttribute("chapter", chapter);
		return "admin/book/book_chapter_add";
		
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String saveBookQuestionChapter( @Valid @ModelAttribute BookChapterDTO bookChapterDTO,
			Errors errors, RedirectAttributes attributes ){
		if(errors.hasErrors()){
			return "admin/book/book_chapter_add";
		}
		bookQuestionChapterService.saveOrUpdate(bookChapterDTO);
		attributes.addFlashAttribute("chapter", bookChapterDTO);
		return "redirect:/admin";
		
	}
	
}
