//package com.elastic.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.elastic.AdvancedQuery;
//import com.elastic.QueryBuilder;
//import com.elastic.RequiredHighlight;
//import com.elastic.ResultData;
//import com.elastic.ResultRetriever;
//import com.elastic.SearchType;
//import com.elastic.SimpleQuery;
//import com.elastic.CyrillicLatinConverter;
//import com.repositories.PaperRepository;
//import com.services.repo.MagazineService;
//
//@RestController
//@RequestMapping("/search")
//public class ElasticController {
//	@Autowired
//	MagazineService magazineService;
//	
//	@Autowired
//	PaperRepository paperRepo;
//	
//	@Autowired
//	private ResultRetriever resultRetriever;
//	
//
//	@PostMapping(value="/term", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//				SearchType.regular, simpleQuery.getField(), obradi(simpleQuery.getValue()));
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
//		List<ResultData> results = resultRetriever.getResults(query, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/fuzzy", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//				SearchType.fuzzy, simpleQuery.getField(), obradi(simpleQuery.getValue()));
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
//		List<ResultData> results = resultRetriever.getResults(query, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/prefix", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//				SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
//		List<ResultData> results = resultRetriever.getResults(query, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/range", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//				SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
//		List<ResultData> results = resultRetriever.getResults(query, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/phrase", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//				SearchType.phrase, simpleQuery.getField(), obradi(simpleQuery.getValue()));
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
//		List<ResultData> results = resultRetriever.getResults(query, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/boolean", consumes="application/json")
//	public ResponseEntity<List<ResultData>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(
//				SearchType.regular, advancedQuery.getField1(), obradi(advancedQuery.getValue1()));
//		org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(
//				SearchType.regular, advancedQuery.getField2(), obradi(advancedQuery.getValue2()));
//		
//		BoolQueryBuilder builder = QueryBuilders.boolQuery();
//		if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
//			builder.must(query1);
//			builder.must(query2);
//		}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
//			builder.should(query1);
//			builder.should(query2);
//		}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
//			builder.must(query1);
//			builder.mustNot(query2);
//		}
//		
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		rh.add(new RequiredHighlight(advancedQuery.getField1(), obradi(advancedQuery.getValue1())));
//		rh.add(new RequiredHighlight(advancedQuery.getField2(), obradi(advancedQuery.getValue2())));
//		List<ResultData> results = resultRetriever.getResults(builder, rh);			
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	@PostMapping(value="/queryParser", consumes="application/json")
//	public ResponseEntity<List<ResultData>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
//		org.elasticsearch.index.query.QueryBuilder query=QueryBuilders.queryStringQuery(simpleQuery.getValue());			
//		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//		List<ResultData> results = resultRetriever.getResults(query, rh);
//		System.out.println(results.size());
//		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//	}
//	
//	private String obradi(String s) {
//		String x=s.toLowerCase();
//		x=CyrillicLatinConverter.cir2lat(x);
//		return x;
//	}
//}
