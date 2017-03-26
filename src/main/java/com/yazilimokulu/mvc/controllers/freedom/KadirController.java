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
	
	
	
	@RequestMapping(value="/util/test")
	public String freeKadir( Model model){
		return "admin/freedom/kadir/kadir_page";
	}
	
	@RequestMapping(value="/books")
	public String getKadirBook( Model model){
		return "admin/freedom/kadir/kadirs_book";
	}
	

}
