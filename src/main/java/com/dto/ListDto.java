package com.dto;

import java.util.ArrayList;
import java.util.List;

public class ListDto {
	private List<String> list = new ArrayList<String>();
	public ListDto(){}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
}
