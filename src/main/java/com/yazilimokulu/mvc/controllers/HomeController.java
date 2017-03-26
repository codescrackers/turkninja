package com.yazilimokulu.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yazilimokulu.mvc.services.PostService;

@Controller
public class HomeController {
	
	@Autowired
	PostService postservice;
	
	@RequestMapping(value="/")
	public String goHomeAgain(Model model){
		model.addAttribute("mainPagePopularPosts", postservice.getMainpagePostList());
		return "home";
	}
	
}
