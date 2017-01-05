package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.BookRepository;
import com.yazilimokulu.mvc.daos.TagRepository;
import com.yazilimokulu.mvc.dto.BookDTO;
import com.yazilimokulu.mvc.dto.TagDTO;
import com.yazilimokulu.mvc.entities.Book;
import com.yazilimokulu.mvc.entities.Tag;
import com.yazilimokulu.mvc.mappers.BookMapper;
import com.yazilimokulu.mvc.mappers.TagMapper;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagRepository tagRepository;

	@Override
	public List<String> getAllTagName() {
		List<Tag> tagList = tagRepository.findAll();
		List<String> tags = new ArrayList<>();
		for (Tag tag : tagList) {
			tags.add(tag.getName());
		}
		return tags;
	}

	@Override
	public List<TagDTO> getAllTag() {
		List<Tag> tagList = tagRepository.findAll();
		TagMapper mapper = new TagMapper();
		List<TagDTO> tagDTOList = new ArrayList<>();
		
		for (Tag tag : tagList) {
			TagDTO tagDTO = mapper.tagToTagDTO(tag);
			tagDTOList.add(tagDTO);
		}
		
		return tagDTOList;
	}

}
