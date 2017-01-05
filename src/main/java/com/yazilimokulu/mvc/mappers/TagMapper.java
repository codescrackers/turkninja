package com.yazilimokulu.mvc.mappers;

import com.yazilimokulu.mvc.dto.TagDTO;
import com.yazilimokulu.mvc.entities.Tag;

public class TagMapper {
	
	public TagDTO tagToTagDTO (Tag tag) {
		
		TagDTO tagDTO = new TagDTO(tag.getName(), tag.getPosts().size());
		
		return tagDTO;
	}

}
