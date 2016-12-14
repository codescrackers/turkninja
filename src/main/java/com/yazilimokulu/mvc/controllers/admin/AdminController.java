package com.yazilimokulu.mvc.controllers.admin;

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

import com.yazilimokulu.mvc.entities.BookChapter;
import com.yazilimokulu.mvc.services.BookChapterService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	

	
	@RequestMapping(value="")
	public String adminHome( Model model){
		return "admin/admin_home";
	}

	
}
