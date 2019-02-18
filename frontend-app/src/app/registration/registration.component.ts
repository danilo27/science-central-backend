import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {RepositoryService} from '../services/repository/repository.service';
import { ActivatedRoute, Router } from '@angular/router';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {TaskService} from '../services/task/task.service';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

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
              private taskService : TaskService,
              private notifier: NotifierService
              ) {}
    
    

  ngOnInit() {
    let z = this.taskService.startRegistrationSubprocess();
    z.subscribe(
      res => {
        let x = this.repositoryService.getForms(res.pid);

        x.subscribe(
          res => {
            this.getDynamicForm(res);
          },
          err => {
            console.log("Error occured");
          }
     );
      },
      err => {
        console.log("Error occured");
      }
    );
     
    
  }

  getDynamicForm(res){
    //console.log(res);
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

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    //console.log(o);

    let x;

     
      x = this.userService.registerUser(o, this.formFieldsDto.taskId);
      x.subscribe(
        res => {
          this.notifier.notify('success', 'You successfuly registred. Please Sign in to proceed.');
          this.router.navigateByUrl('/login');
        },
        err => {
          this.notifier.notify('error', 'Error occured. Please try again.');
        }
      );
     
 
  }













  getTasks(){
    let x = this.repositoryService.getTasks(this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
   }

   claim(taskId){
    let x = this.repositoryService.claimTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );
   }

   complete(taskId){
    let x = this.repositoryService.completeTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
   }

}
