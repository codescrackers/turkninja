package com.yazilimokulu.mvc.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin/book-questions")
public class AdminBookQuestionController {
	
	
	@RequestMapping(value="")
	public String goHomeAgain(@PathVariable String type, Model model){
		
		
		return "book_questions";
	}

}
