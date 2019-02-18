package com.example.demo.elastic;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PaperElasticRepository extends ElasticsearchRepository<IndexUnit, String> {
	 @Query("{\"bool\": {\"must\": {\"term\": {\"text\": \"horgoš\"}}}}")
	 List<IndexUnit> sea();
//	 
//	 @Query("?0")
//	 List<IndexUnit> customSearch(String query);
}
