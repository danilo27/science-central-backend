package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @Column(name = "ID")
    private Long id;
 
	@OneToOne(optional = false)
    @JoinColumn(name = "PAPER", unique = true)
    private Paper paper;
	
	@Column(name = "Comment")
    private String comment;

	@Column(name = "Deadline", nullable = true)
	private String deadline;
	
	public Comment(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
