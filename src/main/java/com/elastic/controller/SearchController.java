package com.elastic.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EntityUtils;
import org.apache.lucene.queries.CommonTermsQuery;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.elastic.AdvancedQuery;
import com.example.demo.elastic.CyrillicLatinConverter;
import com.example.demo.elastic.ExtraAdvancedQuery;
import com.example.demo.elastic.IndexUnit;
import com.example.demo.elastic.PaperElasticRepository;
import com.example.demo.elastic.QueryBuilder;
import com.example.demo.elastic.RequiredHighlight;
import com.example.demo.elastic.ResultData;
import com.example.demo.elastic.ResultRetriever;
import com.example.demo.elastic.SearchType;
import com.example.demo.elastic.SimpleQuery;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper; 

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RestController
@RequestMapping(value = "/search")
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

		@Autowired
		private ResultRetriever resultRetriever;
		
		@Autowired
		private PaperElasticRepository repo;
		
		//@Autowired
		private ElasticsearchTemplate elasticSearchTemplate;
		
		@PostMapping(value="/term", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
					SearchType.regular, simpleQuery.getField(), obradi(simpleQuery.getValue()));
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<ResultData> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/fuzzy", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
					SearchType.fuzzy, simpleQuery.getField(), obradi(simpleQuery.getValue()));
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<ResultData> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/prefix", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
					SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<ResultData> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/range", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
					SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<ResultData> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/phrase", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
					SearchType.phrase, simpleQuery.getField(), obradi(simpleQuery.getValue()));
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
			List<ResultData> results = resultRetriever.getResults(query, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/boolean", consumes="application/json")
		public ResponseEntity<List<ResultData>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(
					SearchType.regular, advancedQuery.getField1(), obradi(advancedQuery.getValue1()));
			org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(
					SearchType.regular, advancedQuery.getField2(), obradi(advancedQuery.getValue2()));
			
			BoolQueryBuilder builder = QueryBuilders.boolQuery();
			if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
				builder.must(query1);
				builder.must(query2);
			}else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
				builder.should(query1);
				builder.should(query2);
			}else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
				builder.must(query1);
				builder.mustNot(query2);
			}
			
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			rh.add(new RequiredHighlight(advancedQuery.getField1(), obradi(advancedQuery.getValue1())));
			rh.add(new RequiredHighlight(advancedQuery.getField2(), obradi(advancedQuery.getValue2())));
			List<ResultData> results = resultRetriever.getResults(builder, rh);			
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		@PostMapping(value="/queryParser", consumes="application/json")
		public ResponseEntity<List<ResultData>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
			org.elasticsearch.index.query.QueryBuilder query=QueryBuilders.queryStringQuery(simpleQuery.getValue());			
			List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
			List<ResultData> results = resultRetriever.getResults(query, rh);
			System.out.println(results.size());
			return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		}
		
		/*
		private static String DATA_DIR_PATH;
		
		static {
			ResourceBundle rb=ResourceBundle.getBundle("application");
			DATA_DIR_PATH=rb.getString("dataDir");
		}
		
		@Autowired
		private Indexer indexer;
		
		@GetMapping("/reindex")
		public ResponseEntity<String> index() throws IOException {
			File dataDir = getResourceFilePath(DATA_DIR_PATH);
			long start = new Date().getTime();
			int numIndexed = indexer.index(dataDir);
			long end = new Date().getTime();
			String text = "Indexing " + numIndexed + " files took "
					+ (end - start) + " milliseconds";
			return new ResponseEntity<String>(text, HttpStatus.OK);
		}
		
		private File getResourceFilePath(String path) {
			System.out.println(path);
		    URL url = this.getClass().getClassLoader().getResource(path);
		    System.out.println(url);
		    File file = null;
		    try {
		        file = new File(url.toURI());
		    } catch (URISyntaxException e) {
		        file = new File(url.getPath());
		    }   
		    return file;
		}*/
	
		private String obradi(String s) {
			String x=s.toLowerCase();
			x=CyrillicLatinConverter.cir2lat(x);
			return x;
		}
		
		
		
		
		
//		@Autowired
//		ElasticsearchOperations temp;
//		
//		@Autowired
//		RestClient restClient;
		
		@Autowired
		RestHighLevelClient restHighLevelClient;
		 
		public void highRest() throws IOException{
			SearchRequest searchRequest = new SearchRequest("digitallibrary"); 
			searchRequest.types("paper");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(QueryBuilders.termQuery("text", "horgoš"));
			searchRequest.source(sourceBuilder); 
			
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				String sourceAsString = hit.getSourceAsString();
				System.out.println("::"+sourceAsString);
			}
			
			
			//Obican Rest Client, nema mapiranja u objekte itd
//			Map<String, String> paramMap = new HashMap<String, String>();
//			paramMap.put("q", "text:horgoš");
//			paramMap.put("pretty", "true");
//			                               
//			Response response = restClient.performRequest("GET", "/digitallibrary/_search",
//			                                                           paramMap);
//			System.out.println(EntityUtils.toString(response.getEntity()));
//			System.out.println("Host -" + response.getHost() );
//			System.out.println("RequestLine -"+ response.getRequestLine() );
			
		}
		
		
		
		
		
		
		
//		
//		public ObjectMapper getHighlights(){
//			HighlightBuilder hb = new HighlightBuilder();
//			
//			SearchResponse response = elasticSearchTemplate.getClient().prepareSearch("scientificpaper").highlighter(hb)
//					.setQuery(qb).get();
//
//			for (SearchHit o : response.getHits()) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				ScientificPaperDTO result = null;
//
//				try {
//					result = objectMapper.readValue(o.getSourceAsString(), ScientificPaperDTO.class);
//
//					// Dinamicki sazetak - Highlighter
//					for (String item : o.getHighlightFields().keySet()) {
//						if (item.equals("pdfText"))
//							result.setPdfText(o.getHighlightFields().get("pdfText").fragments()[0].string());
//
//						if (item.equals("keywords"))
//							result.setKeywords(o.getHighlightFields().get("keywords").fragments()[0].string());
//
//						if (item.equals("title"))
//							result.setTitle(o.getHighlightFields().get("title").fragments()[0].string());
//					}
//
//					resultsList.add(result);
//				}
//			}
//			return objectMapper;
//		}
//		
		
		//Sve radi osim Highlights
//		@PostMapping(value="/advancedBoolean", consumes="application/json")
//		public ResponseEntity<List<ResultData>> advancedBoolean(@RequestBody List<ExtraAdvancedQuery> advancedQueries) throws Exception {
//			
//			List<ResultData> results = new ArrayList<ResultData>();
//			
//			for(ExtraAdvancedQuery e : advancedQueries){
//				System.out.println(e.toString());	
//			}		
//			
//			// 1 polje 
//			if(advancedQueries.size()==1){
//				if(advancedQueries.get(0).getType().equals("optional")){ //optional -> match query (ili common terms query)
//					org.elasticsearch.index.query.QueryBuilder query=
//							QueryBuilders.commonTermsQuery(advancedQueries.get(0).getField(), advancedQueries.get(0).getValue());
//				 
//					List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//					rh.add(new RequiredHighlight(advancedQueries.get(0).getField(), advancedQueries.get(0).getValue()));
//					results = resultRetriever.getResults(query, rh);		
//					System.out.println("[OPTIONAL - BY 1 FIELD] results: " + results.toString());
//					return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//					
//				} else if(advancedQueries.get(0).getType().equals("phrase")){
//					org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(
//							SearchType.phrase, advancedQueries.get(0).getField(), obradi(advancedQueries.get(0).getValue()));
//					List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//					rh.add(new RequiredHighlight(advancedQueries.get(0).getField(), advancedQueries.get(0).getValue()));
//					results = resultRetriever.getResults(query, rh);		
//					System.out.println("[PHRASE - BY 1 FIELD] results: " + results.toString());
//					return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//				}
//				
//			} else {
//				for(ExtraAdvancedQuery advancedQuery : advancedQueries){
//					//za svaki query setujem tip (match ili phrase)
//					if(advancedQuery.getType().equals("optional")){
//						advancedQuery.setQuery(QueryBuilders.matchQuery(advancedQuery.getField(), advancedQuery.getValue()));	 
//					} else if(advancedQuery.getType().equals("phrase")){
//						advancedQuery.setQuery(QueryBuilders.matchPhraseQuery(advancedQuery.getField(), advancedQuery.getValue())); 
//					}					
//				}
//				
//				//ako ne razlikujem match i phrase
////				for(ExtraAdvancedQuery advancedQuery : advancedQueries){
////					advancedQuery.setQuery(QueryBuilders.commonTermsQuery(advancedQuery.getField(), advancedQuery.getValue()));
////				}
//				
//				BoolQueryBuilder builder = QueryBuilders.boolQuery();
//				for(ExtraAdvancedQuery advancedQuery : advancedQueries){
//					if(advancedQuery.getOperation().equals("")){
//						builder.must(advancedQuery.getQuery());
//					} else if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
//						builder.must(advancedQuery.getQuery());
//					} else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
//						builder.should(advancedQuery.getQuery());
//					} else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
//						builder.mustNot(advancedQuery.getQuery());
//					}
//				}
//		
//				List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
//				for(ExtraAdvancedQuery advancedQuery : advancedQueries){
//					rh.add(new RequiredHighlight(advancedQuery.getField(), advancedQuery.getValue()));
//				}
//				results = resultRetriever.getResults(builder, rh);			
//				System.out.println("results are: " + results.toString());
//				return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
//			}
//			 
//
//			return null;
//		}
		
		
		@PostMapping(value="/advancedBoolean", consumes="application/json")
		public ResponseEntity<List<IndexUnit>> advancedBoolean(@RequestBody List<ExtraAdvancedQuery> advancedQueries) throws Exception {
			
			List<IndexUnit> results = new ArrayList<IndexUnit>();
			List<String> fields = new ArrayList<String>();
			for(ExtraAdvancedQuery e : advancedQueries){
				//dodaj u polja za dinamicki sazetak samo ako nije NOT
				if(!e.getOperation().equals("NOT")){
					fields.add(e.getField());
				}
				 
				System.out.println(e.toString());	
			}		
			
			for(ExtraAdvancedQuery advancedQuery : advancedQueries){
				//za svaki query setujem tip (match ili phrase)
				if(advancedQuery.getType().equals("optional")){
					advancedQuery.setQuery(QueryBuilders.matchQuery(advancedQuery.getField(), advancedQuery.getValue()));	 
				} else if(advancedQuery.getType().equals("phrase")){
					advancedQuery.setQuery(QueryBuilders.matchPhraseQuery(advancedQuery.getField(), advancedQuery.getValue())); 
				}					
			}
			
			BoolQueryBuilder builder = QueryBuilders.boolQuery();
			for(ExtraAdvancedQuery advancedQuery : advancedQueries){
				if(advancedQuery.getOperation().equals("")){
					builder.must(advancedQuery.getQuery());
				} else if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
					builder.must(advancedQuery.getQuery());
				} else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
					builder.should(advancedQuery.getQuery());
				} else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
					builder.mustNot(advancedQuery.getQuery());
				}
			}
	
			 
			results = getResults(builder, fields);
	 
			return new ResponseEntity<List<IndexUnit>>(results, HttpStatus.OK);
			 
			 
 
		}
		
	 
		public List<IndexUnit> getResults(BoolQueryBuilder qb, List<String> fields) throws JsonParseException, JsonMappingException, IOException{
			HighlightBuilder hb = new HighlightBuilder();
			IndexUnit result = null;
			
			for(String s : fields)
				hb.field(s);
			
			SearchResponse response = elasticSearchTemplate.getClient().prepareSearch("digitallibrary").highlighter(hb)
					.setQuery(qb).get();
			List<IndexUnit> results = new ArrayList<IndexUnit>();
			for (SearchHit hit : response.getHits()) {
				ObjectMapper objectMapper = new ObjectMapper();
				result = objectMapper.readValue(hit.getSourceAsString(), IndexUnit.class);

				System.out.println("keySet: " + hit.getHighlightFields().keySet());
				for (String item : hit.getHighlightFields().keySet()) {
					 
					if (item.equals("text"))
						result.setText(hit.getHighlightFields().get("text").fragments()[0].string());
					
					if (item.equals("author"))
						result.setAuthor(hit.getHighlightFields().get("author").fragments()[0].string());
					
					if (item.equals("magazine"))
						result.setMagazine(hit.getHighlightFields().get("magazine").fragments()[0].string());
					
					if (item.equals("field"))
						result.setField(hit.getHighlightFields().get("field").fragments()[0].string());
					
					if (item.equals("keywords"))
						result.setKeywords(hit.getHighlightFields().get("keywords").fragments()[0].string());

					if (item.equals("title"))
						result.setTitle(hit.getHighlightFields().get("title").fragments()[0].string());
						
					
						
					}

					results.add(result);

			 
			}
			System.out.println("RESULTS: " + results.toString());
			return results;
		}
		
		
		
		
		
		
		
}
