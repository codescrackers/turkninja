package com.yazilimokulu.mvc.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.entities.User;
import com.yazilimokulu.mvc.services.PostService;
import com.yazilimokulu.mvc.services.UserService;

@ControllerAdvice(annotations=Controller.class)
public class GlobalControllerAdvice {

	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@ModelAttribute("currentUser")
	public User getCurrentUser(){
		return userService.currentUser();
	}
	
	
	@ModelAttribute("currentDate")
	public Date getCurrentDate(){
		return new Date();
	}
	
	@ModelAttribute("latestPosts")
	public List<Post> getLatestPosts(){
		return postService.getPostsList(0, 10);
	}
	
	@ModelAttribute("topPosts")
	public List<Post> getTopPosts(){
		return postService.getTopPostsList();
	}
	
	@ModelAttribute("latestUsers")
	public Page<User> getLatestUsers(){
		return userService.getLatestUsersList(0,8);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handleError(HttpServletRequest request){
		return "controller_error";
	}
	
	

}
