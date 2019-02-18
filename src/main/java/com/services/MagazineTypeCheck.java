package com.services;

import java.util.HashMap;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Magazine;

@Service
public class MagazineTypeCheck implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Magazine m = (Magazine)execution.getVariable("magazine");
		 
		runtimeService.setVariable(execution.getId(), "magazineType", m.getPaymentType().toString());
	}
}
	
 