package com.elastic.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.elastic.Helper;
import com.lowagie.text.pdf.PdfReader;
import com.model.FieldOfScience;
import com.model.Magazine;
import com.model.Paper;
import com.model.Review;
import com.repositories.FieldRepository;
import com.repositories.MagazineRepository;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;



@RestController
@RequestMapping(value = "/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BooksController {
	
 
	@Autowired
	private FieldRepository fieldRepo;
	
	@Autowired
	private PaperRepository paperRepository;
	
	@Autowired
	private MagazineRepository magazineRepo;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	ReviewerRepository revRepo;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFields")
	public ResponseEntity<List<FieldOfScience>> getFields(){
		System.out.println("okkk");
		return new ResponseEntity<>(fieldRepo.findAll(), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getMagazines")
	public ResponseEntity<List<Magazine>> getMagazines(){
		System.out.println("okkk");
		return new ResponseEntity<>(magazineRepo.findAll(), HttpStatus.OK);
	}
	
	
	 

	 
 
 
	
	@PostMapping("/addPaper")
	public boolean addPaper(@RequestBody Paper paper){
		System.out.println("1 - Adding paper");
		System.out.println(paper.toString());
		try {
			  
	        Review review = new Review();
			paper = paperRepository.save(paper);
			review.setPaper(paper);
			review.setComment("Komentar o radu...");
			review.setCommentForEditor("Komentar o radu, za editora...");
			review.setSuggestion("Predlog recenzenta...");
			review.getReviewers().add(revRepo.findByUserUsername("rev1"));
			review.getReviewers().add(revRepo.findByUserUsername("rev2"));
			reviewRepo.save(review);
			
			helper.saveAndIndexNewPaper(paper);
			System.out.println("2 - Paper Saved");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return false;
		}
		return true;
	}
	
	@PostMapping("/addFile")
	public HashMap<String, String> addFile(@RequestParam("file") MultipartFile file) {
		PdfReader reader;
		try {
			reader = new PdfReader(file.getBytes());
			
			if (reader.getMetadata() == null) {
			      System.out.println("No XML Metadata.");
			      HashMap<String, String> mmap=new HashMap<String,String>();
			      mmap.put("NOXML", "NOXML");
			      String filename=helper.saveUploadedFile(file);
			      mmap.put("filename", filename);
			      return mmap;
			    } else {
			      //System.out.println("XML Metadata: " + new String(reader.getMetadata()));
			      @SuppressWarnings("unchecked")
			      HashMap<String, String> map=reader.getInfo();
			      //System.out.println(System.getProperty("user.dir"));
			      String filename=helper.saveUploadedFile(file);
			      map.put("filename", filename);
			      System.out.println(map.keySet());
			      return map;
			    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, String> mmap=new HashMap<String,String>();
	    mmap.put("NOXML", "NOXML");
	    return mmap;

		
	}
}
