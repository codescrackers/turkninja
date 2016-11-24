package com.yazilimokulu.mvc.controllers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.yazilimokulu.mvc.data.entities.Resource;
import com.yazilimokulu.mvc.data.services.ResourceService;

@Controller
@RequestMapping("/resource")
@SessionAttributes("resource")
public class ResourceController {

	@Autowired
	private ResourceService service;

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody String handleUpload(@RequestParam("file") MultipartFile file){
		if(!file.isEmpty()){
			return "The file size is " + file.getSize();
		}else{
			return "There was a problem";
		}
		
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		return "resource_add";
	}

	@RequestMapping("/{resourceId}")
	@ResponseBody
	public Resource findResource(@PathVariable("resourceId") Resource resource){
		return resource;
	}
	
	@RequestMapping("/find")
	public String find(Model model){
		model.addAttribute("resources", service.findAll());
		return "resources";
	}
	
	@RequestMapping("/request")
	@ResponseBody
	public String request(@RequestBody Resource resource){
		System.out.println(resource);
		//Send out an email for request
		return "The request has been sent for approval";
	}
	
	@RequestMapping("/review")
	public String review(@ModelAttribute Resource resource){
		System.out.println("Invoking review()");
		return "resource_review";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute Resource resource, SessionStatus status) {
		System.out.println("Invoking save()");
		System.out.println(resource);
		status.setComplete();
		return "redirect:/resource/add";
	}

	@ModelAttribute("resource")
	public Resource getResource(){
		System.out.println("Adding a new resource to the model");
		return new Resource();
	}
	
	@ModelAttribute("typeOptions")
	public List<String> getTypes(){
		return new LinkedList<>(Arrays.asList(new String[] {
				"Material", "Other", "Staff", "Technical Equipment" }));
	}
	
	@ModelAttribute("radioOptions")
	public List<String> getRadios(){
		return new LinkedList<>(Arrays.asList(new String[]{
				"Hours", "Piece", "Tons"	
			}));
	}
	
	@ModelAttribute("checkOptions")
	public List<String> getChecks(){
		return new LinkedList<>(Arrays.asList(new String[]{
				"Lead Time", "Special Rate", "Requires Approval"	
			}));
	}
	
}
