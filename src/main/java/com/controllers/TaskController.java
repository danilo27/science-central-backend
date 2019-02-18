package com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.TaskDto; 
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RepositoryService repositoryService;
 
	@GetMapping(path = "/startProcess", produces = "application/json")
	  public @ResponseBody ResponseEntity<TaskDto> startProcess() {
		 
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_1");
		
		Task task = taskService.createTaskQuery().
				processInstanceId(pi.getId()).active().singleResult();
		 
		

		TaskDto dto = new TaskDto();
		dto.setTaskId(task.getId());
		dto.setPid(pi.getId());
		
	 
		
		return new ResponseEntity(dto,  HttpStatus.OK);
    }
	
	@GetMapping(path = "/startRegistrationSubprocess", produces = "application/json")
    public @ResponseBody ResponseEntity<TaskDto> startRegistrationSubprocess() {
		 
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Reg_Subprocess");
 
		Task task = taskService.createTaskQuery().
				processInstanceId(pi.getId()).active().singleResult();
		 
		

		TaskDto dto = new TaskDto();
		dto.setTaskId(task.getId());
		dto.setPid(pi.getId());
		
 
		 
		return new ResponseEntity(dto,  HttpStatus.OK);
    }
	
	public Map<String, String> getAllActiveActivities(String processInstanceId) {
	    // get engine services
	     

	    // get the process instance
	    ProcessInstance processInstance =
	        runtimeService.createProcessInstanceQuery()
	            .processInstanceId(processInstanceId)
	            .singleResult();

	    HashMap<String, String> activityNameByActivityId = new HashMap<String, String>();

	    // get all active activities of the process instance
	    List<String> activeActivityIds =
	        runtimeService.getActiveActivityIds(processInstance.getId());
	    System.out.println(")****************************************");
	    for(String s : activeActivityIds){
	    	System.out.println(s);
	    }
	    System.out.println(")****************************************");
	    // get bpmn model of the process instance
	    BpmnModelInstance bpmnModelInstance =
	        repositoryService.getBpmnModelInstance(processInstance.getProcessDefinitionId());

	    for (String activeActivityId : activeActivityIds) {
	      // get the speaking name of each activity in the diagram
	      ModelElementInstance modelElementById =
	          bpmnModelInstance.getModelElementById(activeActivityId);
	      String activityName = modelElementById.getAttributeValue("name");

	      activityNameByActivityId.put(activeActivityId, activityName);
	    }

	    // map contains now all active activities
	    return activityNameByActivityId;
	  }
 
	
	@GetMapping("/getTasks")
	public List<Task> getTasks(HttpServletRequest request){
		List<Task> lista=taskService.createTaskQuery().active().taskAssignee(request.getHeader("username")).list();
		return lista;
		//return taskService.createTaskQuery().taskAssignee("localhost").list();
	}
	
	
	
	
	
	
}
