package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CREDENTIALS")
public class Credentials implements Serializable{
	@Id
    @Column(name = "USERNAME", length = 40)
    private String username;

    @Column(name = "PASSWORD", length = 60, nullable = false)
    private String password;
    
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING) 
    private Role role;
 
    public enum Role {
    	AUTHOR, CHIEF_EDITOR, EDITOR, REVIEWER
    }
    
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Credentials(){}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Credentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + "]";
	}
    
    
    
}
