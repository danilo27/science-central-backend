package com.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Email;
import com.dto.FormSubmissionDto;
import com.model.Credentials;
import com.model.Magazine;
import com.model.Paper;
import com.model.UserDetails;
import com.model.Paper.Status;
import com.repositories.EmailRepo;
import com.repositories.PaperRepository;
import com.services.repo.FieldService;

@Service
public class SubmitPaper implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	EmailRepo emailRepo;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	FieldService fieldService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
 
		Magazine m = (Magazine)execution.getVariable("magazine");
		System.out.println("Chosen magazine: " + m.toString());
		//Paper paper = (Paper)execution.getVariable("paper");
		
		Paper newPaper = new Paper();
		Set<UserDetails> coauthors = new HashSet<>();
		
		List<FormSubmissionDto> paper_dto = (List<FormSubmissionDto>)execution.getVariable("submitPaperDto");
		
		for (FormSubmissionDto formField : paper_dto) {
			if(formField.getFieldId().equals("title")){
				newPaper.setTitle(formField.getFieldValue());
			} else if (formField.getFieldId().equals("couathors")){
				//coauthors...
			} else if (formField.getFieldId().equals("keywords")){
				newPaper.setKeywords(formField.getFieldValue());
			} else if (formField.getFieldId().equals("abstract")){
				newPaper.setPaperAbstract(formField.getFieldValue());
			} else if (formField.getFieldId().equals("science_field")){
				newPaper.setField(null);
			} else if (formField.getFieldId().equals("filename")){
				newPaper.setFilename(formField.getFieldValue());
			}
		   	System.out.println("->"+formField.getFieldId()+"->"+formField.getFieldValue()); 
		}
		
		Credentials user = (Credentials)execution.getVariable("userFromSession");
		newPaper.setWantedMagazine(m.getIssn());
		newPaper.setStatus(Status.NEW);
		newPaper.setAuthorCreds(user);
		 
		newPaper.setField(fieldService.findAll().get(0));
		
		newPaper = paperRepo.save(newPaper);
		
		runtimeService.setVariable(execution.getId(), "newPaper", newPaper);
 
		runtimeService.setVariable(execution.getId(), "chief", m.getEditor().getCredentials().getUsername());
 
		Email e = new Email();
		e.setTo("testmailftn@gmail.com");
		e.setFrom("testmailftn@gmail.com");
		e.setSubject("Notification");
		e.setText("New paper is submited to magazine: " + m.getName());
		 
		emailRepo.save(e);
		
		runtimeService.setVariable(execution.getId(), "mail", e);
		
	 
		
	}
}
