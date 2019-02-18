package com.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.Data;
import com.dto.Email;
import com.dto.ListDto;
import com.model.Editor;
import com.model.Magazine;
import com.model.Paper;
import com.model.Reviewer;
import com.model.Editor.EditorType;
import com.model.Paper.Status;
import com.repositories.EmailRepo;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.EditorService;
import com.services.repo.SubscriptionService;

@Service
public class ReviewingFinished implements JavaDelegate{
		
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Autowired
	EditorService editorService;
	
	@Autowired
	EmailRepo emailRepo;
	
	@Autowired
	ReviewerRepository revRepo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Paper newPaper = (Paper)runtimeService.getVariable(execution.getId(), "newPaper");
		
		newPaper.setStatus(Status.REVIEWED);
		newPaper = paperRepo.save(newPaper);
		
		runtimeService.setVariable(execution.getId(), "newPaper", newPaper);
		runtimeService.setVariable(Data.pid, "timerDuration", "PT24H");

	}
}
