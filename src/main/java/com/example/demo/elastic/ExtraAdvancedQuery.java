package com.example.demo.elastic;

public class ExtraAdvancedQuery {
	private String field;
	private String value;
	private String type;
	private String operation;
	org.elasticsearch.index.query.QueryBuilder query;
	public ExtraAdvancedQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	public org.elasticsearch.index.query.QueryBuilder getQuery() {
		return query;
	}

	public void setQuery(org.elasticsearch.index.query.QueryBuilder query) {
		this.query = query;
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
		return "ExtraAdvancedQuery [field=" + field + ", value=" + value + ", type=" + type + ", operation=" + operation
				+ ", query=" + query + "]";
	}
	 
	
	
}
