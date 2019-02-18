package com.controllers;

import org.camunda.bpm.engine.RuntimeService;
import org.h2.engine.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.data.Data;
import com.dto.Email;
import com.repositories.EmailRepo;
import com.services.repo.SubscriptionService;

@RestController
@RequestMapping("/connect") 
public class ConnectorController {
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	EmailRepo emailRepo;
	@RequestMapping(value="/accept",  method=RequestMethod.GET)
//	@GetMapping(path = "/accept")
    public ResponseEntity partialAccept( ) {	 
		System.out.println("Editor accepted paper. Sending email...");
		Email e = new Email();
		e.setTo("testmailftn@gmail.com");
		e.setFrom("testmailftn@gmail.com");
		e.setSubject("Paper is accepted");
		e.setText("Your paper is accepted.");
		 
		emailRepo.save(e);
		System.out.println("Pid u connect: " + Data.pid);
		System.out.println("Email: " + e.toString());
		runtimeService.setVariable(Data.pid, "mailDecission", e);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@GetMapping(path = "/partialAccept/{pid}/{username}/{paperId}", produces = "application/json")
//    public @ResponseBody ResponseEntity partialAccept(@PathVariable String pid,
//    		@PathVariable String username, @PathVariable String paperId) {	 
// 
//		return null;
//	}
//	
//	@GetMapping(path = "/partialAccept/{pid}/{username}/{paperId}", produces = "application/json")
//    public @ResponseBody ResponseEntity reject(@PathVariable String pid,
//    		@PathVariable String username, @PathVariable String paperId) {	 
// 
//		return null;
//	}
}
