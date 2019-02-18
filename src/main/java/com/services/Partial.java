package com.services;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Email;
import com.repositories.EmailRepo;
import com.services.repo.SubscriptionService;

@Service 
public class Partial implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Autowired
	EmailRepo emailRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Email e = new Email();
		e.setTo("testmailftn@gmail.com");
		e.setFrom("testmailftn@gmail.com");
		e.setSubject("Your paper is partially accepted.");
		e.setText("Your paper is partially accepted. Please modify the paper according to reviewer's comments and suggestions.");
		 
		emailRepo.save(e);
		
		runtimeService.setVariable(execution.getId(), "mail", e);
	}
}
	
