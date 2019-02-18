package com.services;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Magazine;
import com.model.Subscription;
import com.services.repo.SubscriptionService;

@Service
public class ActiveSubCheck implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Magazine magazine = (Magazine)execution.getVariable("magazine");
		User user = (User)execution.getVariable("userFromSession");
		List<Subscription> all = subService.findAll();
		
		runtimeService.setVariable(execution.getId(), "activeSubscription", false);
		for(Subscription s : all){
			if(s.getUser().getUsername().equals(user.getId())){
				if(s.getMagazine().getIssn().equals(magazine.getIssn())){
					runtimeService.setVariable(execution.getId(), "activeSubscription", true);
					break;
				}
			}
		}
		
		runtimeService.setVariable(execution.getId(), "activeSubscription", true);
		
 
		
	}
}
