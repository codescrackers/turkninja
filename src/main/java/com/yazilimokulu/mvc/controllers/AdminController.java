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

import com.yazilimokulu.mvc.entities.BookQuestionCategory;
import com.yazilimokulu.mvc.services.BookQuestionCategoryService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	BookQuestionCategoryService bookQuestionCategoryService;

	
	@RequestMapping(value="/")
	public String adminHome( Model model){
		return "admin_home";
	}

	@RequestMapping(value="/question-category/add")
	public String addBookQuestionCategory( Model model){
		
		BookQuestionCategory category = new BookQuestionCategory();
		model.addAttribute("category", category);
		return "book_category_add";
		
	}
	
	@RequestMapping(value="/question-category/add", method = RequestMethod.POST)
	public String saveBookQuestionCategory( @Valid @ModelAttribute BookQuestionCategory category,
			Errors errors, RedirectAttributes attributes){
		if(errors.hasErrors()){
			return "book_category_add";
		}
		category.setCreateDate(new Date());
		bookQuestionCategoryService.saveOrUpdate(category);
		attributes.addFlashAttribute("category", category);
		return "redirect:/admin";
		
	}
}
