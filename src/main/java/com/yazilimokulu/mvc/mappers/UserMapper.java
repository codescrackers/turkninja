package com.yazilimokulu.mvc.mappers;

import com.yazilimokulu.mvc.dto.UserDTO;
import com.yazilimokulu.mvc.entities.User;

public class UserMapper {
	
public UserDTO userToUserDTO (User user) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setBigAvatarLink(user.getBigAvatarLink());
		userDTO.setRegisterDate(user.getDateStr());
		userDTO.setWebsite(user.getWebsiteLink());
		userDTO.setGravatarUrl(user.getGravatarUrl());
		return userDTO;
	}

}
