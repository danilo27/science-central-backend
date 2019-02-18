package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "EDITOR")
public class Editor implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    //Svi recenzenti kao i urednici moraju biti registrovani korisnici sistema.
    @OneToOne(optional = false)
    @JoinColumn(name = "USERNAME", unique = true)
    private Credentials credentials;
 
    @OneToOne(optional = false)
    @JoinColumn(name = "USER_DETAILS", unique = true)
    private UserDetails userDetails;
    
    @ManyToOne //vise razlicitih editora za jedan casopis
    @JoinColumn(name = "MAGAZINE")
    //Recenzenti mogu istovremeno biti angažovani u različitim časopisima, urednici ne. U casopisu je lista editora SET!!!
    private Magazine magazine;

    @Column(name = "TITLE", length = 40, nullable = false)
    private String title;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "FIELD")
    private FieldOfScience fieldOfWork;
    
    @Column(name = "editorType", nullable = true)
    @Enumerated(EnumType.STRING) 
    private EditorType editorType;
    
    public enum EditorType {
    	CHIEF, FIELD
    }
    
    public Editor(){}
 
	 
	public Editor(Long id, Credentials credentials, UserDetails userDetails, Magazine magazine,
			FieldOfScience fieldOfWork) {
		super();
		this.id = id;
		this.credentials = credentials;
		this.userDetails = userDetails;
		this.magazine = magazine;
		this.fieldOfWork = fieldOfWork;
	}

	public EditorType getEditorType() {
		return editorType;
	}


	public void setEditorType(EditorType editorType) {
		this.editorType = editorType;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public FieldOfScience getFieldOfWork() {
		return fieldOfWork;
	}

	public void setFieldOfWork(FieldOfScience fieldOfWork) {
		this.fieldOfWork = fieldOfWork;
	}


	public UserDetails getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	
    
}