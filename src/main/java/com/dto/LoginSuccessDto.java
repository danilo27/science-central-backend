package com.dto;

import java.util.List;

public class LoginSuccessDto {
	private String role;
	private List<?> data;
	private String username;
	
	public LoginSuccessDto(){}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
