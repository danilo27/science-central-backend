package com.dto;

public class RestDto {
	private String id;
	private String name;
	private String assignee;
	private String executionId;
	private String processDefinitionId;
	private String processInstanceId;
	
	public RestDto(){}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Override
	public String toString() {
		return "RestDto [id=" + id + ", name=" + name + ", assignee=" + assignee + ", executionId=" + executionId
				+ ", processDefinitionId=" + processDefinitionId + ", processInstanceId=" + processInstanceId + "]";
	}
	
}
