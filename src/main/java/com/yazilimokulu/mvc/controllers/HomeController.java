package com.yazilimokulu.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yazilimokulu.mvc.data.entities.Project;

@Controller
public class HomeController {
	
	@RequestMapping(value="/")
	public String goHomeAgain(Model model, @ModelAttribute("project") Project project){
		model.addAttribute("currentProject", project);
		return "index";
	}
/*	
	@RequestMapping("/")
	public String goHome(Model model){
		
		Project project = new Project();
		project.setName("First Project");
		project.setSponsor(new Sponsor("NASA", "555-555-5555", "nasa@nasa.com"));
		project.setDescription("This is a simple project sponsored by NASA");
		
		model.addAttribute("currentProject", project);
		
		return "welcome";
	}
	*/
}
