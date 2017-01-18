package com.yazilimokulu.mvc.mappers;

import com.yazilimokulu.mvc.dto.UserDTO;
import com.yazilimokulu.mvc.entities.User;

public class UserMapper {
	
public UserDTO userToUserDTO (User user) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUsername());
		userDTO.setEmail(user.getEmail());	
		return userDTO;
	}

}
