//package com.elastic;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
//public class IndexUnit {
//
//	public static final String INDEX_NAME = "digitallibrary";
//	public static final String TYPE_NAME = "paper"; 
//
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String text;
//
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String title;
//
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String keywords;
//	
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String authorFirstName;
//
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String authorLastName;
//
//	@Field(type = FieldType.Text, index = true, store = true)
//	private String language;
//	
//	
//	@Id
//	@Field(type = FieldType.Text, index = false, store = true)
//	private String filename;
// 
//
//	
//	public String getText() {
//
//		return text;
//
//	}
//	public void setText(String text) {
//		this.text = text;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getKeywords() {
//		return keywords;
//	}
//	public void setKeywords(String keywords) {
//		this.keywords = keywords;
//	}
//	public String getFilename() {
//		return filename;
//	}
//	public void setFilename(String filename) {
//		this.filename = filename;
//	}
//	 
//	public String getAuthorFirstName() {
//		return authorFirstName;
//	}
//	public void setAuthorFirstName(String authorFirstName) {
//		this.authorFirstName = authorFirstName;
//	}
//	public String getAuthorLastName() {
//		return authorLastName;
//	}
//	public void setAuthorLastName(String authorLastName) {
//		this.authorLastName = authorLastName;
//	}
//	public String getLanguage() {
//		return language;
//	}
//	public void setLanguage(String language) {
//		this.language = language;
//	}
//}