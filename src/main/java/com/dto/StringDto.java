package com.dto;

public class StringDto {
	private String name;
	private String value;
	public StringDto(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public StringDto(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
}
