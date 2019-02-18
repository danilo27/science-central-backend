package com.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "SUBSCRIPTION")
public class Subscription implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long subId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) //vise pretplata na jednog usera moze
    @JoinColumn(name = "USER")
    private Credentials user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "MAGAZINE")
    private Magazine magazine;

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public Credentials getUser() {
		return user;
	}

	public void setUser(Credentials user) {
		this.user = user;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}
	
	public Subscription(){}
	
	public Subscription(Long subId, Credentials user, Magazine magazine) {
		super();
		this.subId = subId;
		this.user = user;
		this.magazine = magazine;
	}
    
    
    
}
