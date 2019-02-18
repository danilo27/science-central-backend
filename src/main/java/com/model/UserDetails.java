package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long userId;

    @Column(name = "FIRST_NAME", length = 40, nullable = true)
    private String firstName;

    @Column(name = "LAST_NAME", length = 40, nullable = true)
    private String lastName;

    @Column(name = "CITY", length = 40, nullable = true)
    private String city;

    @Column(name = "COUNTRY", length = 40, nullable = true)
    private String country;

    @Column(name = "EMAIL", length = 80, nullable = true)
    private String email;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public UserDetails(){}
    
	public UserDetails(Long userId, String firstName, String lastName, String city, String country, String email) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.country = country;
		this.email = email;
	}
    
}
