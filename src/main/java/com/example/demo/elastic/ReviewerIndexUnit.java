package com.example.demo.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(indexName = ReviewerIndexUnit.INDEX_NAME, type = ReviewerIndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class ReviewerIndexUnit {
	public static final String INDEX_NAME = "revs";
	public static final String TYPE_NAME = "rev";
	
	@Id
	@Field(type = FieldType.Text, analyzer ="serbian-analyzer", searchAnalyzer="serbian-analyzer", index=true, store=true)
	private String username;
	
	
	@GeoPointField
	@JsonProperty
	private GeoPoint geo_point;
	
	public ReviewerIndexUnit(){}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public GeoPoint getGeo_point() {
		return geo_point;
	}

	public void setGeo_point(GeoPoint geo_point) {
		this.geo_point = geo_point;
	}
}
