import { Component, OnInit } from '@angular/core';
import {TaskService} from '../services/task/task.service';
import {PaperService} from '../services/paper/paper.service';
import { NotifierService } from 'angular-notifier';
@Component({
  selector: 'app-reviewer-home',
  templateUrl: './reviewer-home.component.html',
  styleUrls: ['./reviewer-home.component.css']
})
export class ReviewerHomeComponent implements OnInit {
  private formFieldsDto = null;
  public formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  public tasks = [];
  private submitAction = "";
 
  constructor(private taskService : TaskService,private notifier: NotifierService,private paperService: PaperService) { }

  ngOnInit() {
  	this.getTasks();



  }
  
  getTasks(){
	let z = this.taskService.getTasks( JSON.parse(localStorage.getItem('user')).username);

  z.subscribe(
    res => {
      console.log("tasks:");
      this.tasks = res as any[];
      console.log(res);
      this.getDynamicForm(this.tasks[0]);
      this.notifier.notify('success', 'You have new papers to review.');
    },
    err => {
    	alert(err);
    }
    )
  }

  getDynamicForm(res){

  	  this.formFieldsDto = res.form;
      this.formFields = res.form.formFields;
      this.processInstance = res.pid;
      //
      this.formFields.forEach( (field) =>{
        
        if( field.type.name=='enum'){
          this.enumValues = Object.keys(field.type.values);
        }
      });

      this.getUrl();
  }
check(result:any){
         
            return true;
         
    }
  base = 'http://localhost:8080/';
  file_url:string;
  getUrl(){
  	for(let f in this.formFields){
  		console.log(this.formFields[f]);
  		if(this.formFields[f]['id']=='file_url'){
  			console.log(this.formFields[f]['value'].value);
  			this.file_url = this.base+this.formFields[f]['value'].value.substring(this.formFields[f]['value'].value.lastIndexOf("\\"));
  			console.log(this.file_url);
  		}
  	}
 
  }


   onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log('Submitting: ', o);

   	let x = this.paperService.reviewed(o, this.formFieldsDto.taskId);
      x.subscribe(
        res => {
          this.notifier.notify('success', 'You submitted your review successfuly.');
         
        },
        err => {
          this.notifier.notify('error', 'Error occured. Please try again.');
        }
      );
           
           
        
     
 
  }
}
