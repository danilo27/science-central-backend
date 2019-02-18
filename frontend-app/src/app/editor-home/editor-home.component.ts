import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../services/magazines/magazine.service';
import { Router } from '@angular/router';
import {UserService} from '../services/users/user.service';
import { NotifierService } from 'angular-notifier';
import {PaperService} from '../services/paper/paper.service';
import {TaskService} from '../services/task/task.service';
@Component({
  selector: 'app-editor-home',
  templateUrl: './editor-home.component.html',
  styleUrls: ['./editor-home.component.css']
})
export class EditorHomeComponent implements OnInit {
  papers_to_assign = [];
  reviewers = [];
  reviews = [];
  assignedReviewersModel = [];
  paperId: any;
  done_revs = [];
  others = [];

  private formFieldsDto = null;
  public formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  public tasks = [];
  private submitAction = "";

  constructor(private user_service: UserService, private notifier: NotifierService,
  	private paper_service: PaperService, private router: Router, private taskService: TaskService,
    private magazineService: MagazineService) { }

  ngOnInit() {
  	let x = this.getPapersToAssign('editor');
    this.getReviews();
    this.getTasks();
      
   
  }

   onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

 
    let x;
      x = this.magazineService.submit(o, this.formFieldsDto.taskId,  JSON.parse(localStorage.getItem("user")).username);
      x.subscribe(
        res => { 
          this.getReviews();
        },
        err => {
 
        }
      );
     
 
  }

  getActions(){
     let x;
      x = this.magazineService.getActions();
      x.subscribe(
        res => { 
          console.log('res:',res);
            this.formFields =res as any[];
           this.formFields.forEach( (field) =>{
        
            if( field.type.name=='enum'){
              this.enumValues = Object.keys(field.type.values);
            }
          });
        },
        err => {
 
        }
      );
    
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
  }
 
  getPapersToAssign(username){
    this.others = [];
  	 let y = this.user_service.getPapersToAssign('editor');
  			y.subscribe(
       		 res => {
       		 	if(res!=null){
               this.notifier.notify('success', 'New assignments...');
            	console.log(res);
         		 this.papers_to_assign = res as any[];
             let x = this.paper_service.getReviewersForEditor('username', this.papers_to_assign[0]['wantedMagazine']);
             x.subscribe(
              res => {
                console.log(res);
             this.reviewers = res as any[];
             console.log('revs: ' + this.reviewers);
             console.log('done_revs: ' + this.done_revs);
             for(let i in this.reviewers){
              if(this.done_revs.indexOf(this.reviewers[i].user.username) < 0){
                this.others.push(this.reviewers[i]);
              }
             }
              },
              err => {
                
              }
            );

          	   }
          	   else {
          	   	 this.notifier.notify('info', 'No new assignments...');
          	   	 this.papers_to_assign = [];
          	   }
           
	        },
	        err => {
	          
	        })
  }

  printAssigned(){
  	console.log(this.assignedReviewersModel)
  }
  another = [];
  printAnother(){
    console.log(this.another);
  }

  chooseReviewers(paper){
  	if( this.assignedReviewersModel.length>=2){ 
  		 let y = this.user_service.chooseReviewers(paper, this.assignedReviewersModel);
      	y.subscribe(
       		 res => {
       		 	 
            	 this.notifier.notify('success', 'Successful assignment.');
         		 this.getPapersToAssign('editor');
          	    
	        },
	        err => {
	          this.notifier.notify('error', 'Error while assigning. Please try again.');
	        }) 
  	} else {
  	 this.notifier.notify('error', 'Please assign two or more reviewers.');
  }
  }

  getRevs(){
      let x = this.paper_service.getReviewersForEditor('username', '12345678');
             x.subscribe(
              res => {
                console.log(res);
             this.reviewers = res as any[]; 
             for(let i in this.reviewers){
              if(this.done_revs.indexOf(this.reviewers[i].user.username) < 0){
                this.others.push(this.reviewers[i]);
              }
             }
              },
              err => {
                
              }
            );
  }

  getReviews(){
    this.done_revs = [];
    let y = this.user_service.getReviews();
        y.subscribe(
           res => {
            if(res!=null){
              console.log(res)
              this.reviews = res as any[];
              this.paperId = this.reviews[0].paper.id;
              for(let i in this.reviews){
                this.done_revs.push(this.reviews[i].reviewer);
              }
              this.getRevs();
              this.notifier.notify('success', 'New Reviews have been made.');
            } else {
              this.notifier.notify('info', 'No New Reviews');
            }
                
          }, 
          err => {
            this.notifier.notify('error', 'Error happened');
          }
          );
  }

   goToPaper(p){
    let assignComp = new EditorHomeComponent(this.user_service,this.notifier, 
    	this.paper_service, this.router, this.taskService, this.magazineService);
    assignComp.p = p;
    

     
 this.router.navigate(['assign']);

  
   
  }



  accept(){
    let x = this.user_service.action('accept');
    x.subscribe(
      res => {
        this.reviews = [];
      })
  }

  reject(){
    let x = this.user_service.action('decline');
    x.subscribe(
      res => {
        this.reviews = [];
      })
  }

  anotherOpinion(){
    let x = this.user_service.anotherOpinion(this.others[0].user.username, this.paperId);
    x.subscribe(
      res => {
        this.reviews = [];
      })
  }

  partialAccept(){
    let x = this.user_service.action('partial');
    x.subscribe(
      res => {
        this.reviews = [];
      })
  }








  get p():any{
    return this.paper_service.paper;
  }

  set p(value: any){
    this.paper_service.paper = value;
  }

 }
