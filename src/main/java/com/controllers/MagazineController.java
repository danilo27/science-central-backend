package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.dto.FormFieldsDto;
import com.dto.FormSubmissionDto;
import com.dto.RegisterDto;
import com.dto.TaskDto;
import com.model.Paper;
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/magazine")
@CrossOrigin(origins = "http://localhost:4200")
public class MagazineController {
	
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
	MagazineService magazineService;
	
	@GetMapping(path = "/chooseMagazine/{issn}/{processInstanceId}", produces = "application/json")
    public FormFieldsDto  chooseMagazine(
    		@PathVariable String issn,
    		@PathVariable String processInstanceId) {
 
		System.out.println("pogodio");
		Task task = taskService.createTaskQuery().
				processInstanceId(processInstanceId).active().singleResult();
		 Map<String, Object> variables =(Map<String, Object>) runtimeService.getVariables(processInstanceId);
		System.out.println("u kont je : " + magazineService.findOneByIssn(issn));
		 variables.put("magazine", magazineService.findOneByIssn(issn));
		runtimeService.setVariables(processInstanceId, variables);
		taskService.complete(task.getId());  
 
		
		Task taskAfterCheckingType = taskService.createTaskQuery().
				processInstanceId(processInstanceId).active().singleResult();
		 
		TaskFormData tfd = formService.getTaskFormData(taskAfterCheckingType.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(taskAfterCheckingType.getId(), processInstanceId, properties);
		
		 
    }
	
	@PostMapping(path = "/submitPaper/{taskId}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity register(@RequestBody List<FormSubmissionDto> dto, 
    		@PathVariable String taskId,
    		@PathVariable String username) {
		 
		
		System.out.println("submitPaper");
		
		HashMap<String, Object> map = this.mapListToDto(dto);
 
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "submitPaperDto", dto);
		 
		map.put("author", username);
		
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
