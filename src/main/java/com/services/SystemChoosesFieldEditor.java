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

import com.dto.Email;
import com.dto.ListDto;
import com.model.Editor;
import com.model.Editor.EditorType;
import com.model.Magazine;
import com.model.Paper;
import com.model.Review;
import com.model.Reviewer;
import com.model.Credentials.Role;
import com.repositories.EmailRepo;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.EditorService;
import com.services.repo.SubscriptionService;

 
@Service
public class SystemChoosesFieldEditor implements JavaDelegate{
		
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
		boolean activeFieldEditor = false;
		
		for(Editor editor : editorService.findAll()){
			if(editor.getCredentials().getRole() == Role.EDITOR){
				
				if(true){
					
				//TODO change to real field check
				//if(editor.getFieldOfWork().getCode().equals(newPaper.getField().getCode())){
				 
					//TODO promeni u mejl editora...
					Email e = new Email();
					e.setTo("testmailftn@gmail.com");
					e.setFrom("testmailftn@gmail.com");
					e.setSubject("New paper is submitted to your field");
					e.setText("New paper is submitted to your field. \nPlease choose two reviewers to review this paper.");
					 
					emailRepo.save(e);
					
					runtimeService.setVariable(execution.getId(), "mail", e);
					
					runtimeService.setVariable(execution.getId(), "editor", editor.getCredentials().getUsername());
					
					newPaper.setEditor(editor.getCredentials().getUsername());
					newPaper = paperRepo.save(newPaper);
					runtimeService.setVariable(execution.getId(), "newPaper", newPaper);
					
					System.out.println(newPaper.toString());					
					
					activeFieldEditor = true;
					break;
				}
			}
		} 
		
		Magazine m = (Magazine)execution.getVariable("magazine");
		
		if(!activeFieldEditor){
			Email e = new Email();
			e.setTo("testmailftn@gmail.com");
			e.setFrom("testmailftn@gmail.com");
			e.setSubject("New paper is submitted to your field");
			e.setText("New paper is submitted, however, there is no active editor for this field. \nPlease choose yourself two reviewers to review this paper.");
			 
			emailRepo.save(e);
			
			runtimeService.setVariable(execution.getId(), "mail", e);
			runtimeService.setVariable(execution.getId(), "chief", m.getEditor().getCredentials().getUsername());
			
			runtimeService.setVariable(execution.getId(), "editor", m.getEditor().getCredentials().getUsername());
			
			newPaper.setEditor(m.getEditor().getCredentials().getUsername());
			newPaper = paperRepo.save(newPaper);
			runtimeService.setVariable(execution.getId(), "newPaper", newPaper);

			System.out.println(newPaper.toString());
		}
		
		
		
		List<String> revs = new ArrayList<>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		 
		ListDto list = new ListDto();
		for(Reviewer rev : revRepo.findAll() ){
			for(Magazine mag : rev.getMagazines()){
				if(mag.getIssn().equals(m.getIssn())){
					revs.add(rev.getUser().getUsername());
					map.put(rev.getUser().getUsername(), rev.getUser().getUsername());
				}
			}
		}
		
		if(revs.size()==0){
			revs.add(m.getEditor().getCredentials().getUsername());
			runtimeService.setVariable(execution.getId(), "reviewers", revs);
			runtimeService.setVariable(execution.getId(), "fieldReviewers", false);
		} else {
			runtimeService.setVariable(execution.getId(), "fieldReviewers", true);
		}
		
		 
		
		list.setList(revs);
		ObjectValue mapOV = Variables.objectValue(map)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();
		//execution.setVariable("reviewersForMagazine",mapOV);
		
		runtimeService.setVariable(execution.getId(), "trenutno_zavrsenih", 0);
		runtimeService.setVariable(execution.getId(), "activeFieldEditor", activeFieldEditor);
	}
}
