package com.dto;

import com.model.Paper;
import com.model.Reviewer;

public class PaperReviewerDto {
	private Paper paper;
	private Reviewer reviewer;
	public PaperReviewerDto(){}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public Reviewer getReviewer() {
		return reviewer;
	}
	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}
	
}
