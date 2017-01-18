package com.yazilimokulu.mvc.dto;

import java.util.List;

public class ResponsePageDTO<T> {
	
	private int pageNumber;
	
	private int totalPageNumber;
	
	private boolean isFirst;
	
	private boolean isLast;

	private boolean hasNext;
	
	private boolean hasPrevious;
	
	private List<T> data;
	

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	
}
