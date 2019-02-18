package com.example.demo.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
 
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.model.FieldOfScience;

@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class IndexUnit {

	public static final String INDEX_NAME = "digitallibrary";
	public static final String TYPE_NAME = "paper";
	
	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String text;

	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String title;

	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String keywords;
	
	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String author;

	@Field(type = FieldType.Text,analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String field; 
	
	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String magazine; 
 
	@Id
	@Field(type = FieldType.Text, index = false, store = true)
	private String filename;
	
	@GeoPointField
	@JsonProperty
	private GeoPoint geo_point;
	
	public GeoPoint getGeo_point() {
		return geo_point;
	}
	public void setGeo_point(GeoPoint geo_point) {
		this.geo_point = geo_point;
	}
	//	@Id
//	private Long id;
// 
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMagazine() {
		return magazine;
	}
	public void setMagazine(String magazine) {
		this.magazine = magazine;
	}
	@Override
	public String toString() {
		return "IndexUnit [text=" + text + ", title=" + title + ", keywords=" + keywords + ", filename=" + filename
				+  "]";
	}
	 
}