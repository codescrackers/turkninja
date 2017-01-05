package com.yazilimokulu.mvc.controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazilimokulu.mvc.dto.ResponseDTO;
import com.yazilimokulu.mvc.dto.TagDTO;
import com.yazilimokulu.mvc.services.TagService;

@Controller
@RequestMapping(value = "/tag")
public class TagController {
	
	@Autowired
	TagService tagService;
	
	private static final Logger logger = LogManager.getLogger(TagController.class.getName());
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public @ResponseBody ResponseDTO<TagDTO> getAllTag(){
		
		logger.info("tag name all list run");
		List<TagDTO> tagList = null;
		
		try {
			tagList = tagService.getAllTag();
		} catch (Exception e) {
			logger.error("Tüm Tag listesi getirilirken hata oluştu. /tag/all" + e.getMessage());
		}
		
		ResponseDTO<TagDTO> response = new ResponseDTO<>();
		response.setData(tagList);
		
		return  response;
		
	}

}
