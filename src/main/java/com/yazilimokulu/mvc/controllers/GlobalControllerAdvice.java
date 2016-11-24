package com.yazilimokulu.mvc.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yazilimokulu.mvc.data.validators.ProjectValidator;

@ControllerAdvice(annotations=Controller.class)
public class GlobalControllerAdvice {

	@ModelAttribute("currentDate")
	public Date getCurrentDate(){
		return new Date();
	}
	
	@InitBinder("project")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProjectValidator());
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handleError(HttpServletRequest request){
		return "controller_error";
	}

}
