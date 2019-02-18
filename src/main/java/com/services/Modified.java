package com.services;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.Data;
import com.dto.Email;
import com.repositories.EmailRepo;
import com.services.repo.SubscriptionService;

@Service 
public class Modified implements JavaDelegate{
	
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
		e.setSubject("Author " + runtimeService.getVariable(execution.getId(), "author") + " modified the paper.");
		e.setText("Author " + runtimeService.getVariable(execution.getId(), "author") + " modified the paper. His response is: \n\n" + runtimeService.getVariable(execution.getId(), "content"));
		 
		emailRepo.save(e);
		
		runtimeService.setVariable(execution.getId(), "mail", e);
		
	}
}
