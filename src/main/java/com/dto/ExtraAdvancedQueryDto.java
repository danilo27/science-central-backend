package com.dto;

public class ExtraAdvancedQueryDto {
	private String field;
	private String value;
	private String type;
	private String operation;
	 
	public ExtraAdvancedQueryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}



	@Override
	public String toString() {
		return "ExtraAdvancedQueryDto [field=" + field + ", value=" + value + ", type=" + type + ", operation="
				+ operation + "]";
	}
	 
	
	
}
