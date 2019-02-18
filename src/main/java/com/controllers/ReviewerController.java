package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.FormSubmissionDto;
import com.model.Paper;
import com.model.Review;
import com.model.Reviewer;
import com.repositories.AuthorRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.CredentialsService;
import com.services.repo.MagazineService;

@RestController
@RequestMapping(value = "/rev")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewerController {
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
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
	
	@PostMapping(path = "/reviewed/{taskId}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity reviewed(@RequestBody List<FormSubmissionDto> dto, 
    		@PathVariable String taskId,
    		@PathVariable String username) {
		 
		
		System.out.println("reviewed");
		
		HashMap<String, Object> map = this.mapListToDto(dto);
 
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		 
		Paper paper = (Paper)runtimeService.getVariable(processInstanceId, "newPaper");
		Review review = new Review();
		
		for (FormSubmissionDto formField : dto) {
			System.out.println(dto.toString());
			if(formField.getFieldId().equals("comment")){
				review.setComment(formField.getFieldValue());
			} else if (formField.getFieldId().equals("suggestion")){
				review.setSuggestion(formField.getFieldValue());
			} else if (formField.getFieldId().equals("comment_for_chief")){
				review.setCommentForEditor(formField.getFieldValue());
			} 
			
		   	System.out.println("->"+formField.getFieldId()+"->"+formField.getFieldValue()); 
		}
		review.setReviewer(username);
		review.setPaper(paper); 
		
		reviewRepo.save(review);
 
		formService.submitTaskForm(taskId, map);
 
        return new ResponseEntity<>(HttpStatus.OK);
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
