package com.example.demo.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReviewerElasticRepository  extends ElasticsearchRepository<ReviewerIndexUnit, String> {

}
