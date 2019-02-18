package com.services;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.FormSubmissionDto;
import com.model.Credentials;
import com.services.repo.CredentialsService;

@Service
public class LoginService implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	CredentialsService credService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("LoginService JavaDelegate - execute");
 
		List<FormSubmissionDto> login = (List<FormSubmissionDto>)execution.getVariable("login");

		String username = "";
		String password = "";
		for (FormSubmissionDto formField : login) {
			if(formField.getFieldId().equals("username")) {
				username = formField.getFieldValue();
			} else if (formField.getFieldId().equals("password")) {
				password = formField.getFieldValue();	 
			}
		}

		runtimeService.setVariable(execution.getId(), "loginSuccess", false);
		
		for(Credentials user : credService.findAll()){
			if(user.getUsername().equals(username)){
				if(user.getPassword().equals(password)){
					runtimeService.setVariable(execution.getId(), "userFromSession", user);
					runtimeService.setVariable(execution.getId(), "role", user.getRole().toString());
					runtimeService.setVariable(execution.getId(), "loginSuccess", true);
					runtimeService.setVariable(execution.getId(), "author", user.getUsername());
					System.out.println("Logged in as: " + user.toString());
				}
			}
		}	
		
	}
		
}
