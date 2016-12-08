package com.yazilimokulu.mvc.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yazilimokulu.mvc.entities.BookQuestionCategory;
import com.yazilimokulu.mvc.services.BookQuestionCategoryService;

@ControllerAdvice(annotations=Controller.class)
public class GlobalControllerAdvice {

	@Autowired
	BookQuestionCategoryService bookQuestionCategoryService;
	
	
	@ModelAttribute("questionCategories")
	public List<BookQuestionCategory> getQuestionCategories(){
		return bookQuestionCategoryService.findAll();
	}
	
	@ModelAttribute("currentDate")
	public Date getCurrentDate(){
		return new Date();
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handleError(HttpServletRequest request){
		return "controller_error";
	}

}
