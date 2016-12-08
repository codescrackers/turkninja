package com.yazilimokulu.mvc.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yazilimokulu.mvc.entities.BookQuestionChapter;
import com.yazilimokulu.mvc.services.BookQuestionChapterService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	BookQuestionChapterService bookQuestionChapterService;

	
	@RequestMapping(value="")
	public String adminHome( Model model){
		return "admin/admin_home";
	}

	@RequestMapping(value="/question-chapter/add")
	public String addBookQuestionChapter( Model model){
		
		BookQuestionChapter chapter = new BookQuestionChapter();
		model.addAttribute("chapter", chapter);
		return "admin/book_chapter_add";
		
	}
	
	@RequestMapping(value="/question-chapter/add", method = RequestMethod.POST)
	public String saveBookQuestionChapter( @Valid @ModelAttribute BookQuestionChapter chapter,
			Errors errors, RedirectAttributes attributes){
		if(errors.hasErrors()){
			return "admin/book_chapter_add";
		}
		chapter.setCreateDate(new Date());
		bookQuestionChapterService.saveOrUpdate(chapter);
		attributes.addFlashAttribute("chapter", chapter);
		return "redirect:/admin";
		
	}
}
