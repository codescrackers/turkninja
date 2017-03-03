package com.yazilimokulu.mvc.services;

import java.util.List;

import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.dto.ResponsePageDTO;
import com.yazilimokulu.mvc.dto.TagDTO;
import com.yazilimokulu.mvc.dto.UserDTO;
import com.yazilimokulu.mvc.entities.Tag;


public interface TagService {
	
	List<String> getAllTagName();
	
	List<TagDTO> getAllTag();

	ResponsePageDTO<TagDTO> findTagsPage(Integer pageNumber, int pageSize);
	
}
