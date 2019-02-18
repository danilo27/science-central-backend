package com.dto;

import java.util.List;

public class AuthorTaskDto {
	private String hasReviews;
	private List<?> data;
	public AuthorTaskDto(){}
	 
	public String getHasReviews() {
		return hasReviews;
	}

	public void setHasReviews(String hasReviews) {
		this.hasReviews = hasReviews;
	}

	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
	
	
}
