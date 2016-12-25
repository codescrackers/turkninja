package com.yazilimokulu.mvc.controllers.freedom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.utils.StringUtil;

@Controller
@RequestMapping(value = "/kadir")
public class KadirController {
	
	@Autowired
	StringUtil util;
	
	
	@RequestMapping(value="/util/test")
	public String bookHome( Model model){
		System.out.println(util.turkishToEnglishChars("ali"));
		BookDTO bookDTO = new BookDTO();
		model.addAttribute("bookDTO", bookDTO);
		return "admin/book/book_list";
	}
	

}
