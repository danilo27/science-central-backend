package com.dto;

import java.util.ArrayList;
import java.util.List;

import com.model.UserDetails;

public class CoauthorsDto {
	private List<UserDetails> coauthors = new ArrayList<>();

	public List<UserDetails> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(List<UserDetails> coauthors) {
		this.coauthors = coauthors;
	}
	
	public CoauthorsDto(){}
}
