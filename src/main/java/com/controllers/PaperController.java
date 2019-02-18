	package com.controllers;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.data.Data;
import com.dto.Email;
import com.dto.RestDto;
import com.dto.StringDto;
import com.model.Magazine;
import com.model.Paper;
import com.model.Review;
import com.model.Reviewer;
import com.model.Paper.Status;
import com.repositories.CommentRepository;
import com.repositories.EmailRepo;
import com.repositories.IssueRepository;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.EditorService;
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/paper")
@CrossOrigin(origins = "http://localhost:4200")
public class PaperController {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
 
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	IssueRepository issueRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	ReviewerRepository revRepo;
	
	@Autowired
	EditorService editorService;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	EmailRepo emailRepo;
	
	@Bean
	public RestTemplate rest() {
	    return new RestTemplate();
	}
	
	@GetMapping(path = "/getReviewersFromMagazine/{username}/{issn}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Reviewer>> getReviewersFromMagazine(
    		@PathVariable String username,
    		@PathVariable String issn) {	 
		List<Reviewer> reviewers = new ArrayList<Reviewer>(); 
		for(Reviewer rev : revRepo.findAll()){
			for(Magazine m : rev.getMagazines()){
				if(m.getIssn().equals(issn)){
					if(!reviewers.contains(rev)){
						reviewers.add(rev);
					}
				}
			}
		}
        return new ResponseEntity<List<Reviewer>>(reviewers, HttpStatus.OK);
    }
	
	@GetMapping(path = "/getReviewersForEditor/{username}/{issn}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Reviewer>> getReviewersForEditor(
    		@PathVariable String username,
    		@PathVariable String issn) {	 
		List<Reviewer> reviewers = new ArrayList<Reviewer>(); 
		for(Reviewer rev : revRepo.findAll()){
			for(Magazine m : rev.getMagazines()){
				if(m.getIssn().equals(issn)){
					if(!reviewers.contains(rev)){
						reviewers.add(rev);
					}
				}
			}
		}
        return new ResponseEntity<List<Reviewer>>(reviewers, HttpStatus.OK);
    }
	
	@PostMapping(path = "/setRevs/{pid}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity  setRevs(
    		@PathVariable String pid,
    		@RequestBody List<String> revs,
    		@PathVariable String username) {	 
		runtimeService.setVariable(pid, "reviewers", revs);
		System.out.println("Assigned: " + revs);
		System.out.println("Logged in as: " + username);
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().taskAssignee(username).singleResult();
		if(task!=null){
			taskService.claim(task.getId(), username);
			taskService.complete(task.getId());
		 
		}
			return new ResponseEntity ( HttpStatus.OK);
    }
	
	
	
	@GetMapping(path = "/getComments/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Review>> getComments(
    		@PathVariable String username ) {	 
		List<Review> ret = new ArrayList<Review>();
		for(Review r : reviewRepo.findAll()){
			if(r.getPaper().getStatus() == Status.REVIEWED && r.getPaper().getAuthorCreds().getUsername().equals(username)){
				ret.add(r);
			}
		}
        return new ResponseEntity<List<Review>>(ret, HttpStatus.OK);
    }
	
	@PostMapping(path = "/modify/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity  modify(@RequestBody StringDto stringDto,
    		@PathVariable String username ) {	 
 
		runtimeService.setVariable(Data.pid, "content", stringDto.getValue());
		
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = rest().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
		List<RestDto> tasks = response.getBody();
		
		List<RestDto> retrievedTasksForAssignee = new ArrayList<>();
 
//		Paper paper = paperRepo.findById(Long.parseLong(paperId)).orElse(null);
//		paper.setStatus(Status.REVIEW);
//		paperRepo.save(paper);
 
		ResponseEntity responseClaim;
		ResponseEntity responseComplete;
		
		HttpEntity<String> entity;
 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		 
		
		for(RestDto dto : tasks){
			System.out.println("Task: " + dto.toString());
			if(dto.getAssignee().equals(username)){
				retrievedTasksForAssignee.add(dto); 
				url = "http://localhost:8080/rest/task/"+dto.getId()+"/claim";
				entity = new HttpEntity<String>("{\"userId\":\""+username+"\"}", headers);
				responseClaim = rest().postForEntity(url, entity, Void.class);  
				
				entity = new HttpEntity<String>("{}", headers);
				url = "http://localhost:8080/rest/task/"+dto.getId()+"/complete";
				responseComplete = rest().postForEntity(url, entity, Void.class); 
 
			}
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
