package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
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
import com.dto.AuthorTaskDto;
import com.dto.FormFieldsDto;
import com.dto.FormSubmissionDto;
import com.dto.LoginDto;
import com.dto.LoginSuccessDto;
import com.dto.RegisterDto;
import com.dto.RestDto;
import com.dto.StringDto;
import com.dto.TaskDto;
import com.model.Credentials;
import com.model.Credentials.Role;
import com.model.Magazine;
import com.repositories.AuthorRepository;
import com.services.repo.CredentialsService;
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/welcome")
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {
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
	
	@Bean
	public RestTemplate restApiTemplate() {
	    return new RestTemplate();
	}
 
	@GetMapping(path = "/get/{pid}", produces = "application/json")
    public @ResponseBody FormFieldsDto getForm(@PathVariable String pid) {
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
        return new FormFieldsDto(task.getId(), pid, properties);
    }
	
	@GetMapping(path = "/get/tasks/{processInstanceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		
        return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	@PostMapping(path = "/login", produces = "application/json")
	public @ResponseBody ResponseEntity<Credentials> log(@RequestBody LoginDto dto){
		System.out.println("LoggedInAs: " + dto.getUsername());
		for(Credentials user : credService.findAll()){
			if(user.getUsername().equals(dto.getUsername())){
				if(user.getPassword().equals(dto.getPassword())){
					runtimeService.setVariable(Data.pid, "userFromSession", user);
					runtimeService.setVariable(Data.pid, "role", user.getRole().toString());
					runtimeService.setVariable(Data.pid, "loginSuccess", true);
					if(user.getRole() == Role.AUTHOR)
						runtimeService.setVariable(Data.pid, "author", user.getUsername());
					else if(user.getRole() == Role.EDITOR)
						runtimeService.setVariable(Data.pid, "editor", user.getUsername());
					else if(user.getRole() == Role.CHIEF_EDITOR)
						runtimeService.setVariable(Data.pid, "chief", user.getUsername());
					else if(user.getRole() == Role.REVIEWER)
						runtimeService.setVariable(Data.pid, "reviewer", user.getUsername());
					System.out.println("Logged in as: " + user.toString());
					return new ResponseEntity<Credentials>(user,HttpStatus.OK);
					
				}
			}
		}
		return null;
	}
	
	@GetMapping(path = "/getPid", produces = "application/json")
	public @ResponseBody ResponseEntity<StringDto> getPid(){
		return new ResponseEntity<StringDto>(new StringDto("pid",Data.pid),HttpStatus.OK);
	}
	
	@PostMapping(path = "/register/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity register(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		System.out.println("Submit for register");
		HashMap<String, Object> map = this.mapListToDto(dto);
 
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);
		
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/login/{taskId}", produces = "application/json")
    public LoginSuccessDto login(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		System.out.println("Submit for login");
		HashMap<String, Object> map = this.mapListToDto(dto);
		 
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "login", dto);
		formService.submitTaskForm(taskId, map);  
		
		LoginSuccessDto ret = new LoginSuccessDto();
	    if((boolean)runtimeService.getVariable(processInstanceId,"loginSuccess")==true){
			ret.setRole((String)runtimeService.getVariable(processInstanceId, "role"));
			ret.setData((List<?>)runtimeService.getVariable(processInstanceId, "data"));
			Credentials u = (Credentials)runtimeService.getVariable(processInstanceId, "userFromSession");
			ret.setUsername((String)runtimeService.getVariable(processInstanceId, u.getUsername()));
 
		}
 
        return ret;
    }
 
	@PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(taskId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String taskId) {
		Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.complete(taskId);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
        return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    }
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	@PostMapping(path = "/register", produces = "application/json")
    public @ResponseBody ResponseEntity register(@RequestBody RegisterDto dto) { 
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
	@GetMapping(path = "/getAllMagazines/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Magazine>> getAllMagazines(@PathVariable String taskId) {
        return new ResponseEntity<List<Magazine>>(magazineService.findAll(), HttpStatus.OK);
    }
	
	@GetMapping(path = "/getTask/{pid}", produces = "application/json")
    public @ResponseBody ResponseEntity<AuthorTaskDto> getTask(@PathVariable String pid) {
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		AuthorTaskDto dto = new AuthorTaskDto();
 
		dto.setData((List<Magazine>)magazineService.findAll());
		return new ResponseEntity<AuthorTaskDto>(dto, HttpStatus.OK);
     
    }
	
	@GetMapping(path = "/getTasks/{processInstanceId}/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> getTasks(
    		@PathVariable String processInstanceId,
    		@PathVariable String username
    		) {
		 
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
				FormFieldsDto fields = new FormFieldsDto(task.getId(), processInstanceId, properties);
				t.setForm(fields);
				dtos.add(t);
				System.out.println(t.toString());
 
			}
		}
 
        return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	public List<RestDto> getTaskForAssignee(String username){
		String url = "http://localhost:8080/rest/task"; //gets active task
		ResponseEntity<List<RestDto>> response = restApiTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RestDto>>(){});
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
	
	 
}