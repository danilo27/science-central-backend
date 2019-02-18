package com.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.FormSubmissionDto;
import com.model.Author;
import com.model.Credentials;
import com.model.UserDetails;
import com.model.Credentials.Role;
import com.repositories.AuthorRepository;
import com.services.repo.CredentialsService;
import com.services.repo.UserDetailsService;

@Service
public class RegisterService implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	AuthorRepository authorRepo;
	
	@Autowired
	CredentialsService credService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		System.out.println("RegisterService JavaDelegate - execute");
	    //String var = "pera";
	    //var = var.toUpperCase();
	    //execution.setVariable("input", var);
	    List<FormSubmissionDto> registration = (List<FormSubmissionDto>)delegateExecution.getVariable("registration");
	    //System.out.println("reg je: " + registration);
	    //User user = identityService.newUser("");
	   
	    //identityService.saveUser(user);
	   
	    
	    
	    Author user = new Author();
	    authorRepo.save(user);
	    
	  
	 
	    Credentials author = new Credentials();
	    UserDetails author_details = new UserDetails();
	    
	    author.setRole(Role.AUTHOR);
		 
		
 
		 
	    
		 
		 
		
	    for (FormSubmissionDto formField : registration) {
	    	System.out.println("->"+formField.getFieldId());
	    	System.out.println("->"+formField.getFieldValue());
	    	
	    	if(formField.getFieldId().equals("username")) {
	    		author.setUsername(formField.getFieldValue());
	    	} else if(formField.getFieldId().equals("password")) {	 
	    		author.setPassword(formField.getFieldValue());	
	    	} else if(formField.getFieldId().equals("firstName")) {
	    		author_details.setFirstName(formField.getFieldValue());
	    	} else if(formField.getFieldId().equals("lastName")) {
	    		author_details.setLastName(formField.getFieldValue());
	    	} else if(formField.getFieldId().equals("email")) {
	    		author_details.setEmail(formField.getFieldValue());
	    	} else if(formField.getFieldId().equals("city")) {
	    		author_details.setCity(formField.getFieldValue());
	    	} else if(formField.getFieldId().equals("country")) {
	    		author_details.setCountry(formField.getFieldValue());
	    	} 
	    }
	     
	     
	    
		credService.save(author);
		userDetailsService.save(author_details);
	    
		 
		
	    user.setCredentials(author);
		user.setUserDetails(author_details);
		
		authorRepo.save(user);
		
		System.out.println("New user registred: " + user.toString());
	}	

}
