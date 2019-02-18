package com.example.demo.elastic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResultRetriever {
	
	//@Autowired
	private PaperElasticRepository repository;
	
	public ResultRetriever(){
	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
        
		for(IndexUnit iu : repository.findAll()){
			System.out.println("->"+iu.toString());
		}
        for (IndexUnit indexUnit : repository.search(query)) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(),"",""));
		}
        
		
		return results;
	}
	
}
