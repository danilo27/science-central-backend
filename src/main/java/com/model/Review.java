package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW")
public class Review implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @Column(name = "ID")
    private Long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "PAPER")
    private Paper paper;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "REVIEW_REVIEWER")
	private List<Reviewer> reviewers = new ArrayList<Reviewer>();
	 
	private String comment;
	private String suggestion;
	private String commentForEditor;
	private String filename;
	private String reviewer;
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCommentForEditor() {
		return commentForEditor;
	}

	public void setCommentForEditor(String commentForEditor) {
		this.commentForEditor = commentForEditor;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Review(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", paper=" + paper + ", reviewers=" + reviewers + "]";
	}
	
	
}
