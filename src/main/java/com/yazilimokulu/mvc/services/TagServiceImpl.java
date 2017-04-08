package com.yazilimokulu.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yazilimokulu.mvc.daos.TagRepository;
import com.yazilimokulu.mvc.dto.ResponsePageDTO;
import com.yazilimokulu.mvc.dto.TagDTO;
import com.yazilimokulu.mvc.entities.Tag;
import com.yazilimokulu.mvc.entities.TagWithPostCount;
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

	@Override
	public ResponsePageDTO<TagDTO> findTagsPage(Integer pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		Page<TagWithPostCount> tags =tagRepository.findAllWithPostCount(pageRequest);
		List<TagDTO> tagDtos=new ArrayList<>();
		for(TagWithPostCount withCount : tags){
			TagDTO tagDto= new TagDTO(withCount.getTag().getName(),withCount.getPostCount());
			tagDtos.add(tagDto);
		}
		
		
		ResponsePageDTO<TagDTO> response= new ResponsePageDTO<>();
		response.setData(tagDtos);
		response.setPageNumber(tags.getNumber());
		response.setFirst(tags.isFirst());
		response.setLast(tags.isLast());
		response.setTotalPageNumber(tags.getTotalPages());
		return response;
	}

}
