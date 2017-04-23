package com.yazilimokulu.mvc.controllers;

import com.yazilimokulu.mvc.entities.Post;
import com.yazilimokulu.mvc.services.elastic.ElasticPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by edsoft on 08.04.2017.
 */
@Controller
public class SearchController {

    @Autowired
    private ElasticPostService elasticPostService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView getPostByTitle(@ModelAttribute("post")Post post) {
        List<Post> list = elasticPostService.findPostByTitlePosts(post.getTitle());
        return new ModelAndView("search", "listPosts", list);
    }
}
