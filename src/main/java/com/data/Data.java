package com.data;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.elastic.Helper;
import com.example.demo.elastic.IndexUnit;
import com.example.demo.elastic.ReviewerElasticRepository;
import com.example.demo.elastic.ReviewerIndexUnit;
import com.model.Author;
import com.model.Credentials;
import com.model.Credentials.Role;
import com.model.Editor;
import com.model.Editor.EditorType;
import com.model.FieldOfScience;
import com.model.Magazine;
import com.model.Reviewer;
import com.model.UserDetails;
import com.model.enums.MagazinePaymentType;
import com.repositories.AuthorRepository;
import com.repositories.PaperRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.CredentialsService;
import com.services.repo.EditorService;
import com.services.repo.FieldService;
import com.services.repo.MagazineService;
import com.services.repo.UserDetailsService;

 

@Component
public class Data {
	
	@Autowired 
	MagazineService magazineService;
	
	@Autowired
	FieldService fieldService;
	
	@Autowired
	EditorService editorService;
	
	@Autowired
	CredentialsService credService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthorRepository authorRepo;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	ReviewerRepository revRepo;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	Helper helper;
	
//	@Autowired
//	private ElasticsearchTemplate est;
	
//	@Autowired
	private ReviewerElasticRepository elasticRevRepository;
	
	public static String pid;
	
	 
	
	@PostConstruct
	public void init() throws IOException{
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_1");
		pid = pi.getId();
		
		System.out.println("Pid: " + pid);
		
		magazineService.deleteAll();
		fieldService.deleteAll();
		editorService.deleteAll();
		credService.deleteAll();
		userDetailsService.deleteAll();
		authorRepo.deleteAll();
		paperRepo.deleteAll();
		editorService.deleteAll();
		revRepo.deleteAll();
		
		FieldOfScience f1 = new FieldOfScience("ICT", "Information Communication Technologies");
		FieldOfScience f2 = new FieldOfScience("MAT", "Mathematics");
		FieldOfScience f3 = new FieldOfScience("GEO", "Geography");

		fieldService.save(f1);
		fieldService.save(f2);
		fieldService.save(f3);
		
		Credentials author = new Credentials("author","pas");
		author.setRole(Role.AUTHOR);
		Credentials chief = new Credentials("chief","pas");
		chief.setRole(Role.CHIEF_EDITOR);
		Credentials editor = new Credentials("editor","pas");
		editor.setRole(Role.EDITOR);
		Credentials reviewer1 = new Credentials("rev1","pas");
		reviewer1.setRole(Role.REVIEWER);
		Credentials reviewer2 = new Credentials("rev2","pas");
		reviewer2.setRole(Role.REVIEWER);
		Credentials reviewer3 = new Credentials("rev3","pas");
		reviewer3.setRole(Role.REVIEWER);
		Credentials reviewer4 = new Credentials("rev4","pas");
		reviewer4.setRole(Role.REVIEWER);
		Credentials reviewer5 = new Credentials("rev5","pas");
		reviewer5.setRole(Role.REVIEWER);
		Credentials reviewer6 = new Credentials("rev6","pas");
		reviewer6.setRole(Role.REVIEWER);
		Credentials reviewer7 = new Credentials("rev7","pas");
		reviewer7.setRole(Role.REVIEWER);
		
		credService.save(author);
		credService.save(chief);
		credService.save(editor);
		credService.save(reviewer1);
		credService.save(reviewer2);
		credService.save(reviewer3);
		credService.save(reviewer4);
		credService.save(reviewer5);
		credService.save(reviewer6);
		credService.save(reviewer7);
		
		UserDetails author_details = new UserDetails();
		author_details.setCity("Novi Sad");
		author_details.setCountry("Serbia");
		author_details.setEmail("author1@mail.com");
		author_details.setFirstName("Author 1");
		author_details.setLastName("Wick");
		
		UserDetails chief_details = new UserDetails();
		chief_details.setCity("Belgrade");
		chief_details.setCountry("Serbia");
		chief_details.setEmail("chief1@mail.com");
		chief_details.setFirstName("Chief Editor 1");
		chief_details.setLastName("Barbara M");
		
		UserDetails editor_details = new UserDetails();
		editor_details.setCity("Los Angeles");
		editor_details.setCountry("USA");
		editor_details.setEmail("editor1@mail.com");
		editor_details.setFirstName("Editor 1");
		editor_details.setLastName("Simic");
		
		UserDetails reviewer_details = new UserDetails();
		reviewer_details.setCity("San Francisco");
		reviewer_details.setCountry("USA");
		reviewer_details.setEmail("reviewer1@mail.com");
		reviewer_details.setFirstName("Eddie");
		reviewer_details.setLastName("Bravo");
		
		UserDetails reviewer_details2 = new UserDetails();
		reviewer_details2.setCity("San Francisco");
		reviewer_details2.setCountry("Italy");
		reviewer_details2.setEmail("reviewer1@mail.com");
		reviewer_details2.setFirstName("Tony");
		reviewer_details2.setLastName("Ferguson");
		
		UserDetails reviewer_details3 = new UserDetails();
		reviewer_details3.setCity("San Francisco");
		reviewer_details3.setCountry("Canada");
		reviewer_details3.setEmail("reviewer1@mail.com");
		reviewer_details3.setFirstName("Jordan");
		reviewer_details3.setLastName("Petterson");
		
		UserDetails reviewer_details4 = new UserDetails();
		reviewer_details4.setCity("San Francisco");
		reviewer_details4.setCountry("USA");
		reviewer_details4.setEmail("reviewer1@mail.com");
		reviewer_details4.setFirstName("reviewer 1");
		reviewer_details4.setLastName("Stosic");
		
		UserDetails reviewer_details5 = new UserDetails();
		reviewer_details5.setCity("San Francisco");
		reviewer_details5.setCountry("USA");
		reviewer_details5.setEmail("reviewer1@mail.com");
		reviewer_details5.setFirstName("reviewer 1");
		reviewer_details5.setLastName("Stosic");
		
		UserDetails reviewer_details6 = new UserDetails();
		reviewer_details6.setCity("San Francisco");
		reviewer_details6.setCountry("USA");
		reviewer_details6.setEmail("reviewer1@mail.com");
		reviewer_details6.setFirstName("reviewer 1");
		reviewer_details6.setLastName("Stosic");
		
		UserDetails reviewer_details7 = new UserDetails();
		reviewer_details7.setCity("San Francisco");
		reviewer_details7.setCountry("USA");
		reviewer_details7.setEmail("reviewer1@mail.com");
		reviewer_details7.setFirstName("reviewer 1");
		reviewer_details7.setLastName("Stosic");
		
		
		userDetailsService.save(author_details);
		userDetailsService.save(chief_details);
		userDetailsService.save(editor_details);
		userDetailsService.save(reviewer_details);
		userDetailsService.save(reviewer_details2);
		userDetailsService.save(reviewer_details3);
		userDetailsService.save(reviewer_details4);
		userDetailsService.save(reviewer_details5);
		userDetailsService.save(reviewer_details6);
		userDetailsService.save(reviewer_details7);
		
		Author a1 = new Author();
		a1.setCredentials(author);
		a1.setUserDetails(author_details);
		
		authorRepo.save(a1);
		
		Editor e1 = new Editor();
		e1.setFieldOfWork(f1);
		e1.setMagazine(null);
		e1.setCredentials(chief);
		e1.setUserDetails(chief_details);
		e1.setTitle("Neka Titula 1");
		e1.setEditorType(EditorType.CHIEF);
		
		Editor e2 = new Editor();
		e2.setFieldOfWork(f1);
		e2.setMagazine(null);
		e2.setCredentials(editor);
		e2.setUserDetails(editor_details);
		e2.setTitle("Neka titula 2");
		e1.setEditorType(EditorType.FIELD); //TODO ovde promeniti
		
		editorService.save(e1);
		editorService.save(e2);
 
		Magazine m1 = new Magazine();
		m1.setEditor(e1);
		Set<FieldOfScience> fields1 = new HashSet<>();
		fields1.add(f1);
		fields1.add(f2);
		m1.setFields(fields1);
		m1.setIssn("12345678");
		m1.setMembershipPrice(500.00);
		m1.setName("Computer Science Magazine");
		m1.setOptions(null);
		m1.setPaymentType(MagazinePaymentType.PAID_ACCESS);
		
		
		Magazine m2 = new Magazine();
		m2.setEditor(e2);
		Set<FieldOfScience> fields2 = new HashSet<>();
		fields2.add(f3);
		fields2.add(f2);
		m2.setFields(fields2);
		m2.setIssn("87654321");
		m2.setMembershipPrice(750.00);
		m2.setName("Geopraphy Magazine");
		m2.setOptions(null);
		m2.setPaymentType(MagazinePaymentType.OPEN_ACCESS);
		
		magazineService.save(m1);
		magazineService.save(m2);
		
		Set<Magazine> h1 = new HashSet<Magazine>();
		h1.add(m1);
		
		Reviewer r1 = new Reviewer();
		r1.setTitle("Titula recenzenta 1");
		r1.setUser(reviewer1);
		r1.setUserDetails(reviewer_details);
		r1.setMagazines(h1);
		r1.getFields().add(f1);
		//r1.setGeo_point(new GeoPoint(44.786568,20.448921)); //BG
		
		Reviewer r2 = new Reviewer();
		r2.setTitle("Titula recenzenta 2");
		r2.getFields().add(f1);
		r2.setUser(reviewer2);
		r2.setUserDetails(reviewer_details2);
		r2.setMagazines(h1);
		//r2.setGeo_point(new GeoPoint(43.316872,21.894501)); //NIS
		
		Reviewer r3 = new Reviewer();
		r3.setTitle("Titula recenzenta 3");
		r3.getFields().add(f3);
		r3.setUser(reviewer3);
		r3.setUserDetails(reviewer_details3);
		r3.setMagazines(h1); 
		//r3.setGeo_point(new GeoPoint(45.252640,19.873249)); //Petrovaradin
//		
		revRepo.save(r1);
		revRepo.save(r2);
		revRepo.save(r3);
//		
//		ReviewerIndexUnit revIndexUnit1 = new ReviewerIndexUnit();
//		revIndexUnit1.setUsername("rev1");
//		revIndexUnit1.setGeo_point(new GeoPoint(44.786568,20.448921)); //BG
//		
//		ReviewerIndexUnit revIndexUnit2 = new ReviewerIndexUnit();
//		revIndexUnit2.setUsername("rev2");
//		revIndexUnit2.setGeo_point(new GeoPoint(43.316872,21.894501)); //NIS
//		
//		ReviewerIndexUnit revIndexUnit3 = new ReviewerIndexUnit();
//		revIndexUnit3.setUsername("rev3");
//		revIndexUnit3.setGeo_point(new GeoPoint(45.252640,19.873249)); //Petrovaradin
//		 
//		
//		est.getClient().prepareIndex("revs", "rev", "1")
//		   .setSource(revIndexUnit1, XContentType.JSON)
//		   .get();
//		
//		est.getClient().prepareIndex("revs", "rev", "2")
//		   .setSource(revIndexUnit2, XContentType.JSON)
//		   .get();
//		
//		est.getClient().prepareIndex("revs", "rev", "3")
//		   .setSource(revIndexUnit3, XContentType.JSON)
//		   .get();
//		
//		elasticRevRepository.save(revIndexUnit1);
//		elasticRevRepository.save(revIndexUnit2);
//		elasticRevRepository.save(revIndexUnit3); //ovog ne treba da nadje (rev3)
//		
 
	}
}
