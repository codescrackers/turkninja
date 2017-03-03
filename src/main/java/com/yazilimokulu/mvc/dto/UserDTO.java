package com.yazilimokulu.mvc.dto;

import java.io.Serializable;

public class UserDTO implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String email;

	private String bigAvatarLink;
	
	private String registerDate;
	
	private String website;
	
	private String gravatarUrl;
	
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

	public String getBigAvatarLink() {
		return bigAvatarLink;
	}

	public void setBigAvatarLink(String bigAvatarLink) {
		this.bigAvatarLink = bigAvatarLink;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getGravatarUrl() {
		return gravatarUrl;
	}

	public void setGravatarUrl(String gravatarUrl) {
		this.gravatarUrl = gravatarUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
