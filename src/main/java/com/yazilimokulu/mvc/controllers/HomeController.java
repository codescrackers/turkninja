package com.yazilimokulu.mvc.controllers;

import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.services.elastic.ElasticPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yazilimokulu.mvc.services.PostService;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    PostService postservice;

    @RequestMapping(value = "/")
    public String goHomeAgain(Model model) {
        model.addAttribute("mainPagePopularPosts", postservice.getMainpagePostList());
        model.addAttribute("post", new Post());
        return "home";
    }

}
