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
public class EditorRejects implements JavaDelegate{
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
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
		e.setSubject("Paper is rejected");
		e.setText("Your paper is rejected.");
		 
		emailRepo.save(e);
		
		runtimeService.setVariable(execution.getId(), "mail", e);
		
 
		
	}
}
