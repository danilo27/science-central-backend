package com.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAPER")
public class Paper implements Serializable{
	
	@Id
	@GeneratedValue
    @Column(name = "ID")
    private Long id;
	
    @Column(name = "DOI", nullable = true, unique = true)
    private String doi;

    @Column(name = "TITLE", length = 240, nullable = false)
    private String title;

    @Column(name = "ABSTRACT", length = 1500, nullable = true)
    private String paperAbstract;

    @Column(name = "KEYWORDS", length = 1500, nullable = false)
    private String keywords;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)  
    @JoinColumn(name = "AUTHOR_CREDS")  
    private Credentials authorCreds;
    
    private String author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "COAUTHORS", joinColumns = @JoinColumn(name = "PAPER"), inverseJoinColumns = @JoinColumn(name = "AUTHOR"))
    private Set<UserDetails> coauthors = new HashSet<>();
 
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "FIELD")
    private FieldOfScience field;  

    @Column(name = "FILE", nullable = true)
    private Byte[] file;

    @Column(name = "PRICE", nullable = true)
    private Double price;
    
    @Column(name = "FILENAME", nullable = true ,unique = true) 
	private String filename;
    
    @Column(name = "EDITOR", nullable = true) 
	private String editor;
 
    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING) 
    private Status status;
    
    public Credentials getAuthorCreds() {
		return authorCreds;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public void setAuthorCreds(Credentials authorCreds) {
		this.authorCreds = authorCreds;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WANTED", nullable = true)
    private String wantedMagazine;
    
	public enum Status{
    	NEW, MODIFY, MODIFIED, ACCEPTED, REJECTED, FOR_FIELD_EDITOR, REVIEW, REVIEWED
    }
    
    public String getWantedMagazine() {
		return wantedMagazine;
	}

	public void setWantedMagazine(String wantedMagazine) {
		this.wantedMagazine = wantedMagazine;
	}
    
//	public Issue getIssue() {
//		return issue;
//	}
//
//	public void setIssue(Issue issue) {
//		this.issue = issue;
//	}

	public Status getStatus() {
		return status;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaperAbstract() {
		return paperAbstract;
	}

	public void setPaperAbstract(String paperAbstract) {
		this.paperAbstract = paperAbstract;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	 

	public Set<UserDetails> getCoauthors() {
		return coauthors;
	}

	public void setCoauthors(Set<UserDetails> coauthors) {
		this.coauthors = coauthors;
	}

	public FieldOfScience getField() {
		return field;
	}

	public void setField(FieldOfScience field) {
		this.field = field;
	}

	public Byte[] getFile() {
		return file;
	}

	public void setFile(Byte[] file) {
		this.file = file;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
    
    public Paper(){}

	@Override
	public String toString() {
		return "Paper [id=" + id + ", doi=" + doi + ", title=" + title + ", paperAbstract=" + paperAbstract
				+ ", keywords=" + keywords + ", authorCreds=" + authorCreds + ", author=" + author + ", coauthors="
				+ coauthors + ", field=" + field + ", file=" + Arrays.toString(file) + ", price=" + price
				+ ", filename=" + filename + ", editor=" + editor + ", status=" + status + ", wantedMagazine="
				+ wantedMagazine + "]";
	}
  
    
}
