package com.yazilimokulu.mvc.dto;

import java.util.List;

public class ResponseDTO<T> {

	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
