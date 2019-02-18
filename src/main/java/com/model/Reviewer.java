package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REVIEWER") 
public class Reviewer implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    //Svi recenzenti kao i urednici moraju biti registrovani korisnici sistema.
    @OneToOne(optional = false)
    @JoinColumn(name = "USERNAME", unique = true)
    private Credentials user;

    @Column(name = "TITLE", length = 40, nullable = false)
    private String title;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "REVIEWER_MAGAZINE", joinColumns = @JoinColumn(name = "REVIEWER"), inverseJoinColumns = @JoinColumn(name = "MAGAZINE"))
    //Recenzenti mogu istovremeno biti angažovani u različitim časopisima, urednici ne.
    private Set<Magazine> magazines = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "REVIEWER_FIELD", joinColumns = @JoinColumn(name = "REVIEWER"), inverseJoinColumns = @JoinColumn(name="FIELD"))
    private Set<FieldOfScience> fields = new HashSet<>();
    
    @OneToOne(optional = false)
    @JoinColumn(name = "USER_DETAILS", unique = true)
    private UserDetails userDetails;
   
    
    public Reviewer(){}
    
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Credentials getUser() {
		return user;
	}

	public void setUser(Credentials user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(Set<Magazine> magazines) {
		this.magazines = magazines;
	}

	public Set<FieldOfScience> getFields() {
		return fields;
	}

	public void setFields(Set<FieldOfScience> fields) {
		this.fields = fields;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "Reviewer [id=" + id + ", user=" + user + ", title=" + title + ", magazines=" + magazines + ", fields="
				+ fields + ", userDetails=" + userDetails + "]";
	}
    
    
}
