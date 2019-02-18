package com.dto;

import java.util.ArrayList;
import java.util.List;

public class RestTaskDto {
	String taskId;
	String name;
	String assignee;
	String pid;
	List<RestTaskField> fields = new ArrayList<>();
	
	public RestTaskDto(){}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<RestTaskField> getFields() {
		return fields;
	}

	public void setFields(List<RestTaskField> fields) {
		this.fields = fields;
	}
	
	
}
