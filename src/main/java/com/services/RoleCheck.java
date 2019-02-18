package com.services;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Credentials;
import com.model.Credentials.Role;
import com.model.Paper.Status;
import com.model.Issue;
import com.model.Magazine;
import com.model.Paper;
import com.model.Subscription;
import com.repositories.IssueRepository;
import com.repositories.PaperRepository;
import com.services.repo.MagazineService;
import com.services.repo.SubscriptionService;

@Service
public class RoleCheck implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Autowired
	TaskService taskService;

	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	IssueRepository issueRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Credentials user = (Credentials)execution.getVariable("userFromSession");
		System.out.println("Ulogovan: " + user.toString());
		System.out.println("user role: " + user.getRole());
		System.out.println("role sef : " +Role.CHIEF_EDITOR.toString());
		if(user.getRole().toString().equals(Role.AUTHOR.toString())){
			runtimeService.setVariable(execution.getId(), "role", Role.AUTHOR.toString()); 
		} else if(user.getRole().toString().equals(Role.CHIEF_EDITOR.toString())){
			System.out.println("Logged in as CHIEF. Preparing tasks...");
			runtimeService.setVariable(execution.getId(), "role", Role.CHIEF_EDITOR.toString());	
		} else if(user.getRole().toString().equals(Role.EDITOR.toString())){
			runtimeService.setVariable(execution.getId(), "role", Role.EDITOR.toString());
		} else if(user.getRole().toString().equals(Role.REVIEWER.toString())){
			runtimeService.setVariable(execution.getId(), "role", Role.REVIEWER.toString());
		}
		
	}
}
