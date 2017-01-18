package com.yazilimokulu.mvc.dto;

import java.io.Serializable;

public class UserDTO implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
