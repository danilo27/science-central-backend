package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "FIELD_OF_SCIENCE")
public class FieldOfScience implements Serializable{
    @Id
    @Column(name = "CODE", length = 3)
    private String code;

    @Column(name = "FIELD_NAME", nullable = false, length = 64)
    private String name;
    
    public FieldOfScience(){}

	public FieldOfScience(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FieldOfScience [code=" + code + ", name=" + name + "]";
	}
    
    
	    
	    
}
