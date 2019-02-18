package com.elastic.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.elastic.IndexUnit;
import com.example.demo.elastic.MyTokenizer;
import com.example.demo.elastic.PDFHandler;
import com.example.demo.elastic.ResultRetriever;
import com.example.demo.elastic.ReviewerIndexUnit;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.FieldOfScience;
import com.model.Magazine;
import com.model.Paper;
import com.model.Reviewer;
import com.repositories.AuthorRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.CredentialsService;
import com.services.repo.MagazineService; 

 

@RestController
@RequestMapping(value = "/elastic/rev")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewerElasticController {
	@Autowired
	CredentialsService credService;
	 
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	AuthorRepository authorRepo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	ReviewerRepository revRepo;

	@Autowired
	ResultRetriever resultRetriever;
	
	//@Autowired
	private ElasticsearchTemplate elasticSearchTemplate;
	
	@Autowired
	MyTokenizer tokenizer;
	
	@GetMapping(path = "/all/{magazineIssn}", produces = "application/json")
	public @ResponseBody ResponseEntity all(@PathVariable String magazineIssn) {
		 
		
		List<Reviewer> ret = new ArrayList<Reviewer>();
		
		for(Reviewer rev : revRepo.findAll()){
			for(Magazine m : rev.getMagazines()){
				if(m.getIssn().equals(magazineIssn)){
					if(!ret.contains(rev))
						ret.add(rev);
				}
			}
		}
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@PostMapping(path = "/field/{issn}", produces = "application/json")
	public @ResponseBody ResponseEntity field(@RequestBody FieldOfScience field, @PathVariable String issn) {
		
		Magazine magazine = magazineService.findOneByIssn(issn);
		
		List<Reviewer> all = new ArrayList<Reviewer>();
		
		List<Reviewer> ret = new ArrayList<Reviewer>();
		
		for(Reviewer rev : revRepo.findAll()){
			for(Magazine m : rev.getMagazines()){
				if(m.getIssn().equals(magazine.getIssn())){
					if(!all.contains(rev))
						all.add(rev);
				}
			}
		}
		
		for(Reviewer rev : all){
			for(FieldOfScience f : rev.getFields()){
				if(f.getCode().equals(field.getCode())){
					if(!ret.contains(rev))
						ret.add(rev);
				}
			}
			 
		}
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	public List<IndexUnit> getResults(BoolQueryBuilder qb, List<String> fields){
		HighlightBuilder hb = new HighlightBuilder();
		
		for(String s : fields)
			hb.field(s);
		
		SearchResponse response = elasticSearchTemplate.getClient().prepareSearch("digitallibrary").highlighter(hb)
				.setQuery(qb).get();
		List<IndexUnit> results = new ArrayList<IndexUnit>();
		for (SearchHit o : response.getHits()) {
			ObjectMapper objectMapper = new ObjectMapper();
			IndexUnit result = null;

			try {
				result = objectMapper.readValue(o.getSourceAsString(), IndexUnit.class);

				System.out.println("keySet: " + o.getHighlightFields().keySet());
				for (String item : o.getHighlightFields().keySet()) {
					 
					if (item.equals("text"))
						result.setText(o.getHighlightFields().get("text").fragments()[0].string());
					
					if (item.equals("author"))
						result.setAuthor(o.getHighlightFields().get("author").fragments()[0].string());
					
					if (item.equals("magazine"))
						result.setMagazine(o.getHighlightFields().get("magazine").fragments()[0].string());
					
					if (item.equals("field"))
						result.setField(o.getHighlightFields().get("field").fragments()[0].string());
					
					if (item.equals("keywords"))
						result.setKeywords(o.getHighlightFields().get("keywords").fragments()[0].string());

					if (item.equals("title"))
						result.setTitle(o.getHighlightFields().get("title").fragments()[0].string());
					
				
					
				}

				results.add(result);

			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println("RESULTS: " + results.toString());
		return results;
	}
	
	public String[] processTokens(String text){
		String[] ret = null;
		
		return ret;
	}
	
	@PostMapping(path = "/similar", produces = "application/json")
	public @ResponseBody  ResponseEntity<List<IndexUnit>> similar(@RequestBody Paper paper) throws JsonParseException, JsonMappingException, IOException {
		List<IndexUnit> results = new ArrayList<IndexUnit>();

		String fields[] = { "text" };
		//String likeTexts[] = { "koridor" };
		
		//ucitaj tekst iz rada -> tokenizuj i popuni niz fields[] tim tokenima
		IndexUnit indexUnit = new PDFHandler().getIndexUnit(new File(paper.getFilename())); 
		System.out.println("***********************************");
		 System.out.println(indexUnit.getText());
		System.out.println("***********************************");
		
		String likeTexts[] = tokenizer.splitToTokens(indexUnit.getText());
		
		
		
		System.out.println("***********************************");
		
		IndexUnit result = null;
 
		//TODO id=2?
		MoreLikeThisQueryBuilder.Item[] likeItems = 
			{ new MoreLikeThisQueryBuilder.Item("digitallibrary","paper","2") };
		
		MoreLikeThisQueryBuilder mltQueryBuilder = 
				QueryBuilders.moreLikeThisQuery(fields, likeTexts, likeItems)
				.minTermFreq(10)
				.minDocFreq(1);
		
		SearchResponse response = elasticSearchTemplate.getClient().
				prepareSearch("digitallibrary").setQuery(mltQueryBuilder).get();	 
		
		System.out.println(response);
		 			
		for (SearchHit hit : response.getHits()) {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(hit.getSourceAsString(), IndexUnit.class);
			results.add(result);
		}
		
		 
		return new ResponseEntity<List<IndexUnit>>(results, HttpStatus.OK);
 	
		 
	}
	
	@GetMapping(path = "/region", produces = "application/json")
	public @ResponseBody ResponseEntity region() throws JsonParseException, JsonMappingException, IOException {
		List<ReviewerIndexUnit> results = new ArrayList<ReviewerIndexUnit>();
		ReviewerIndexUnit result = null;
 
		GeoDistanceQueryBuilder qb = new GeoDistanceQueryBuilder("geo_point");
		qb.point(45.297050,19.814990).distance(50, DistanceUnit.KILOMETERS); //NS - Milice Tomic 14
 
		SearchResponse response = elasticSearchTemplate.getClient().prepareSearch("revs").setQuery(qb).get();
		 
		for (SearchHit hit : response.getHits()) {
			ObjectMapper objectMapper = new ObjectMapper();
			
			result = objectMapper.readValue(hit.getSourceAsString(), ReviewerIndexUnit.class);
			results.add(result);
			
		}
		
		
		
		System.out.println("Found Revs:");
		for(ReviewerIndexUnit rev : results)
			System.out.println(rev.getUsername());
		
		return null;
	}

}
