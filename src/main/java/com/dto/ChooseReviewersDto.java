package com.dto;

import java.util.ArrayList;
import java.util.List;

import com.model.Paper;
import com.model.Reviewer;

public class ChooseReviewersDto {
	private Paper paper;
	private List<Reviewer> all;
	private List<Reviewer> chosen = new ArrayList<Reviewer>();
	
	public ChooseReviewersDto(){}
	
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public List<Reviewer> getAll() {
		return all;
	}
	public void setAll(List<Reviewer> all) {
		this.all = all;
	}
	public List<Reviewer> getChosen() {
		return chosen;
	}
	public void setChosen(List<Reviewer> chosen) {
		this.chosen = chosen;
	}
	
}
