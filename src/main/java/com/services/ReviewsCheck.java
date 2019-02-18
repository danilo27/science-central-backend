package com.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Comment;
import com.model.Credentials;
import com.model.Magazine;
import com.model.Paper;
import com.model.Subscription;
import com.model.Credentials.Role;
import com.model.Paper.Status;
import com.repositories.CommentRepository;
import com.repositories.IssueRepository;
import com.repositories.PaperRepository;
import com.services.repo.SubscriptionService;

@Service 
public class ReviewsCheck implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	IssueRepository issueRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		runtimeService.setVariable(execution.getId(), "hasReviews", false);
		Credentials user = (Credentials)execution.getVariable("userFromSession");
		List<Comment> comments = new LinkedList<Comment>();
		for(Comment comment : commentRepo.findAll()){
			if(comment.getPaper().getAuthorCreds().getUsername().equals(user.getUsername())){
				if(comment.getPaper().getStatus() == Status.MODIFY){
					runtimeService.setVariable(execution.getId(), "hasReviews", true);
					comments.add(comment);
					
				}
			}
		}
		
		runtimeService.setVariable(execution.getId(), "comments", comments);
		
	}
}