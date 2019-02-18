package com.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CommentDto;
import com.dto.TaskDto;
import com.model.Comment;
import com.model.Credentials;
import com.model.Editor;
import com.model.Magazine;
import com.model.Paper;
import com.model.Review;
import com.model.Reviewer;
import com.model.Editor.EditorType;
import com.model.Paper.Status;
 
import com.repositories.CommentRepository;
import com.repositories.IssueRepository;
import com.repositories.PaperRepository;
import com.repositories.ReviewRepository;
import com.repositories.ReviewerRepository;
import com.services.repo.EditorService;
import com.services.repo.MagazineService;

@RestController
@RequestMapping("/chief")
@CrossOrigin(origins = "http://localhost:4200")
public class ChiefEditorController {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
 
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperRepository paperRepo;
	
	@Autowired
	IssueRepository issueRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	EditorService editorService;
	
	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	ReviewerRepository revRepo;
	
	@GetMapping(path = "/getAllPapersToReview/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<Paper>> getAllPapersToReview(
    		@PathVariable String username) {	 
		List<Paper> new_papers = new ArrayList<Paper>();
		for(Paper paper : paperRepo.findAll()){
			for(Magazine m : magazineService.findAll()){
				if(m.getEditor().getCredentials().getUsername().equals(username)){
					if(paper.getWantedMagazine().equals(m.getIssn())){
						if(paper.getStatus() == Status.NEW){
							new_papers.add(paper);
							break; 
						}
						
					}
				}
				
			}
		}
		
        return new ResponseEntity<List<Paper>>(new_papers, HttpStatus.OK);
    }
	
	@PostMapping(path = "/needsModification/{pid}", produces = "application/json")
    public @ResponseBody ResponseEntity needsModification(@PathVariable String pid, @RequestBody CommentDto dto) {

		
		Comment c = new Comment();
		Paper p = paperRepo.findById(Long.parseLong(dto.getPaperId())).orElse(null);
		if(p!=null){
			p.setStatus(Status.MODIFY);
			paperRepo.save(p);
			c.setPaper(p);
			c.setComment(dto.getComment());
			c.setDeadline(dto.getDeadline());
			
		}
		commentRepo.save(c);
		
		runtimeService.setVariable(pid, "chiefEditorOpinion", "notWellFormated");
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		taskService.complete(task.getId()); 
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/reject/{pid}", produces = "application/json")
    public @ResponseBody ResponseEntity reject(@PathVariable String pid, @RequestBody CommentDto dto) {
		
		Comment c = new Comment();
		Paper p = paperRepo.findById(Long.parseLong(dto.getPaperId())).orElse(null);
		if(p!=null){
			p.setStatus(Status.REJECTED);
			paperRepo.save(p);
			c.setPaper(p);
			c.setComment("Text is not suitable for magazine.");
			c.setDeadline(null);
			
		}
		commentRepo.save(c);

		runtimeService.setVariable(pid, "chiefEditorOpinion", "unacceptable");
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		taskService.complete(task.getId()); 
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/approve/{pid}", produces = "application/json")
    public @ResponseBody ResponseEntity approve(@PathVariable String pid, @RequestBody Paper paper)
	{

		
		boolean activeEditor = false;
		
		for(Editor editor : editorService.findAll()){
			if(editor.getEditorType() == EditorType.FIELD){
				if(editor.getFieldOfWork().getCode().equals(paper.getField().getCode())){
					activeEditor = true;
					runtimeService.setVariable(pid, "editor", editor.getCredentials().getUsername());
					break;
				}
			}
		}
		
		if(paper!=null){
			if(activeEditor){
				paper.setStatus(Status.FOR_FIELD_EDITOR);
				paperRepo.save(paper);
			} else {
				runtimeService.setVariable(pid, "chief", magazineService.findOneByIssn(paper.getWantedMagazine()).getEditor().getCredentials().getUsername());
			}
			runtimeService.setVariable(pid, "chiefEditorOpinion", "acceptable");
			runtimeService.setVariable(pid, "newPaper", paper);
		}
		
 
		
		
		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		taskService.complete(task.getId());  
		
		if(activeEditor)
			return new ResponseEntity<>(true, HttpStatus.OK);
		
		return new ResponseEntity<>(false, HttpStatus.OK);
		
         
    }
	
	@PostMapping(path = "/chooseReviewers/{pid}/{username}/{paperId}", produces = "application/json")
    public @ResponseBody ResponseEntity chooseReviewers(
    		@PathVariable String pid, 
    		@PathVariable String username, 
    		@PathVariable String paperId, 
    		@RequestBody List<String> reviewers)
	{
		runtimeService.setVariable(pid, "reviewers", reviewers);
		Paper paper = paperRepo.findById(Long.parseLong(paperId)).orElse(null);
		paper.setStatus(Status.REVIEW);

		Task task = taskService.createTaskQuery().
				processInstanceId(pid).active().singleResult();
		taskService.complete(task.getId());  

		return new ResponseEntity<>(HttpStatus.OK);
 
    }
}
