import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {RepositoryService} from '../services/repository/repository.service';
import { ActivatedRoute, Router } from '@angular/router';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {TaskService} from '../services/task/task.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  public formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  public tasks = [];
  private submitAction = "";

  constructor(private userService : UserService, 
              private repositoryService : RepositoryService, 
              private route:ActivatedRoute, 
              private router:Router,
              private authService:AuthenticationService,
              private taskService : TaskService
              ) {}
    
    

  ngOnInit() {
 
    this.authService.username = null;
  }

getDynamicForm(res){
    console.log(res);
    //this.categories = res;
    if(res!=null){
      this.formFieldsDto = res;
      this.formFields = res.formFields;
      this.processInstance = res.processInstanceId;
      this.submitAction = res.submitAction;
      this.formFields.forEach( (field) =>{
        
        if( field.type.name=='enum'){
          this.enumValues = Object.keys(field.type.values);
        }
      });
    }
  }


onSubmit(username,password){
 
    let dto = {
      "username": username,
      "password": password
    }
    console.log(dto);
   this.authService.log(dto);
   
 
  }



}