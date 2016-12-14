package com.yazilimokulu.mvc.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.services.BookService;

@ControllerAdvice(annotations=Controller.class)
public class GlobalControllerAdvice {

	@Autowired
	BookService bookService;
	
	
	@ModelAttribute("books")
	public List<BookDTO> getQuestionChapter(){
		return bookService.findAll();
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
