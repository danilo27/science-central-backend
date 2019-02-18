package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
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
import com.dto.ChooseReviewersDto;
import com.dto.FormFieldsDto;
import com.dto.FormSubmissionDto;
import com.dto.RestDto;
import com.dto.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Editor;
import com.model.Magazine;
import com.model.Paper;
import com.model.Paper.Status;
import com.model.Review;
import com.model.Reviewer;
import com.repositories.CommentRepository;
import com.repositories.IssueRepository;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.EditorService;
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/editor")
@CrossOrigin(origins = "http://localhost:4200")
public class EditorController {
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
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@GetMapping(path = "/getReviewers/{username}/{issn}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Reviewer>> getReviewers(
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
	
	@GetMapping(path = "/getPapersToAssign/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Paper>> getPapersToAssign(
    		@PathVariable String username) {	 
	 
		ChooseReviewersDto dto = new ChooseReviewersDto();
		Editor editor = null;
		for(Editor e : editorService.findAll()){
			if(e.getCredentials().getUsername().equals(username)){
				editor = e;
				break;
			}
		}
		
		List<Paper> for_field_editor = new ArrayList<Paper>();
		
		if(editor!=null){
			for(Paper paper : paperRepo.findAll()){
				if(paper.getStatus() == Status.FOR_FIELD_EDITOR){
					if(paper.getField().getCode().equals(editor.getFieldOfWork().getCode())){
						for_field_editor.add(paper);
					}
				}
			}
		}
		if(for_field_editor.size()>0)
			return new ResponseEntity<List<Paper>>(for_field_editor, HttpStatus.OK);
		else 
			return null;
	}
	
	
	@GetMapping(path = "/getPapersToAssignChief/{code}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Paper>> getPapersToAssignChief(
    		@PathVariable String code) {	 
	 
		 
		
		List<Paper> for_field_editor = new ArrayList<Paper>();
		
	 
		for(Paper paper : paperRepo.findAll()){
			if(paper.getStatus() == Status.FOR_FIELD_EDITOR){
				if(paper.getField().getCode().equals(code)){
					for_field_editor.add(paper);
				}
			}
		}
		 
		if(for_field_editor.size()>0)
			return new ResponseEntity<List<Paper>>(for_field_editor, HttpStatus.OK);
		else 
			return null;
	}
	
	@PostMapping(path = "/chooseReviewers/{pid}/{username}/{paperId}", produces = "application/json")
    public @ResponseBody ResponseEntity chooseReviewers(
    		@PathVariable String pid, 
    		@PathVariable String username, 
    		@PathVariable String paperId, 
    		@RequestBody List<String> reviewers)
	{
 
		runtimeService.setVariable(pid, "reviewers", reviewers);
		Paper paper = paperRepo.findById(Long.parseLong(paperId)).orElse(null);
		paper.setStatus(Status.REVIEW);
		paperRepo.save(paper);

		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		taskService.complete(task.getId());  

		return new ResponseEntity<>(HttpStatus.OK);
 
    }
	
	@GetMapping(path = "/getReviews/{pid}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Review>> getReviews(@PathVariable String pid,
    		@PathVariable String username) {	 
 
		Editor editor = null;
		for(Editor e : editorService.findAll()){
			if(e.getCredentials().getUsername().equals(username)){
				editor = e;
				break;
			}
		}
		Paper newPaper = (Paper)runtimeService.getVariable(pid, "newPaper");
		System.out.println(newPaper.toString());
		//rad se vraca istom onom uredniku koji je rad poslao na recenziju
		if(newPaper.getStatus()==Status.REVIEWED && newPaper.getEditor().equals(username)){
			List<Review> reviews = new ArrayList<Review>();
			
			if(editor!=null){
				for(Review review : reviewRepo.findAll()){
					if(review.getPaper().getId().equals(newPaper.getId())){
						reviews.add(review);
					}
				}
			}
			if(reviews.size()>0)
				return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
			else 
				return null;
		} else {
			return null;
		}
	}
	
	@GetMapping(path = "/getActions/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> getActions(
    		@PathVariable String username) {	 
		List<RestDto> tasks = getTaskForAssignee(username);
		RestDto task = null;
		
		if(tasks.size()>0)
			task = tasks.get(0);
		
		if(task!=null)
			System.out.println("[Active Task] - For: " + task.getAssignee() + " - " + task.getName()+", and Logged in as: " + username);
		
		List<TaskDto> dtos = new ArrayList<TaskDto>(); //form dtos
 
		if(task!=null){
			if(task.getAssignee().equals(username)){
				TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
				TaskFormData tfd = formService.getTaskFormData(task.getId());
				List<FormField> properties = tfd.getFormFields();
				FormFieldsDto fields = new FormFieldsDto(task.getId(), Data.pid, properties);
				t.setForm(fields);
				dtos.add(t);
				System.out.println(t.toString());
 
			}
		}
		return new ResponseEntity(dtos,  HttpStatus.OK);
		 
	}
	
	@PostMapping(path = "/action/{actionName}/{pid}/{username}/{paperId}", produces = "application/json")
    public @ResponseBody ResponseEntity accept(@PathVariable String actionName, @PathVariable String pid,
    		@PathVariable String username, @PathVariable String paperId) {	 
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = restTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
		List<RestDto> tasks = response.getBody();
		
		List<RestDto> retrievedTasksForAssignee = new ArrayList<>();
		
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
				responseClaim = restTemplate().postForEntity(url, entity, Void.class); 
				
				runtimeService.setVariable(Data.pid, "decission", actionName);
				
				entity = new HttpEntity<String>("{}", headers);
				url = "http://localhost:8080/rest/task/"+dto.getId()+"/complete";
				responseComplete = restTemplate().postForEntity(url, entity, Void.class); 
 
			}
		}
		
	 	
		
		return null;
	}
	
 
	@PostMapping(path = "/additional/{rev}/{pid}/{username}/{paperId}", produces = "application/json")
    public @ResponseBody ResponseEntity additional(@PathVariable String rev, @PathVariable String pid,
    		@PathVariable String username, @PathVariable String paperId) {	 
 
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = restTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
		List<RestDto> tasks = response.getBody();
		
		List<RestDto> retrievedTasksForAssignee = new ArrayList<>();
 
		Paper paper = paperRepo.findById(Long.parseLong(paperId)).orElse(null);
		paper.setStatus(Status.REVIEW);
		paperRepo.save(paper);
		
		runtimeService.setVariable(Data.pid, "reviewer", rev);
		runtimeService.setVariable(Data.pid, "newPaper", paper);
		
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
				responseClaim = restTemplate().postForEntity(url, entity, Void.class); 
				
				runtimeService.setVariable(Data.pid, "decission", "additional");
				
				entity = new HttpEntity<String>("{}", headers);
				url = "http://localhost:8080/rest/task/"+dto.getId()+"/complete";
				responseComplete = restTemplate().postForEntity(url, entity, Void.class); 
 
			}
		}
		
	 	
		
		return null;
	}
	
	@PostMapping(path = "/submit/{taskId}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity submit(@RequestBody List<FormSubmissionDto> formDto, 
    		@PathVariable String taskId,
    		@PathVariable String username) {
		 
		System.out.println("submitting form...");
		 System.out.println("=>" + formDto.get(0).getFieldId()+ "-"+formDto.get(0).getFieldValue());
 
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = restTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
		List<RestDto> tasks = response.getBody();
		
		List<RestDto> retrievedTasksForAssignee = new ArrayList<>();
		
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
				responseClaim = restTemplate().postForEntity(url, entity, Void.class); 
																	//finalDecission
				runtimeService.setVariable(Data.pid, formDto.get(0).getFieldId(), formDto.get(0).getFieldValue());
				
				if(formDto.get(0).getFieldId().equals("manje_ispravke"))
					runtimeService.setVariable(Data.pid, "timerDuration", "PT1H");
					 
				entity = new HttpEntity<String>("{}", headers);
				url = "http://localhost:8080/rest/task/"+dto.getId()+"/complete";
				responseComplete = restTemplate().postForEntity(url, entity, Void.class); 
 
			}
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
 
	public List<RestDto> getTaskForAssignee(String username){
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = restTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
		List<RestDto> tasks = response.getBody();
		
		List<RestDto> retrievedTasksForAssignee = new ArrayList<>(); //for assignee
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		for(RestDto dto : tasks){
			System.out.println("Task: " + dto.toString());
			if(dto.getAssignee().equals(username)){
				retrievedTasksForAssignee.add(dto);			
			}
		}
		
		return retrievedTasksForAssignee;
	}
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
}
