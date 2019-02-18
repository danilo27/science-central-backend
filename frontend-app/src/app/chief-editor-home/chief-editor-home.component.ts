import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../services/magazines/magazine.service';
import { Router } from '@angular/router';
import {UserService} from '../services/users/user.service';
import { NotifierService } from 'angular-notifier';
import {PaperService} from '../services/paper/paper.service';
import {TaskService} from '../services/task/task.service';
@Component({
  selector: 'app-chief-editor-home',
  templateUrl: './chief-editor-home.component.html',
  styleUrls: ['./chief-editor-home.component.css']
})
export class ChiefEditorHomeComponent implements OnInit {
  activeEditor: string;
  papers_to_approve: any[];
  papers_to_assign: any[];
  reviewers: any[];
  current = null;
   assignedReviewersModel = [];
  constructor(private user_service: UserService, private notifier: NotifierService, 
  	private paper_service: PaperService, private router: Router, private taskService: TaskService) { }

  ngOnInit() {
  	//let x = this.user_service.getPapersToReview(JSON.parse(localStorage.getItem('data')).username);
  	 this.getPapersToReview('chief')
  }

  getPapersToReview(username){
  	let x = this.user_service.getPapersToReview(username);
  	x.subscribe(
        res => {

           
          console.log(res);
          this.papers_to_approve = res as any[];
          if(res.length==0){
          	this.notifier.notify('info', 'No new tasks...');
          }
           
        },
        err => {
          
        }
      );
  }

  modify(p){
  	var commentDto = {
  		paperId: p.id,
  		comment: 'Modify please',
  		deadline: 'odma'
  	}
  	let x = this.user_service.modify(commentDto);
  	x.subscribe(
        res => {

           
          let y = this.user_service.getPapersToReview('chief');
  			y.subscribe(
       		 res => {

           
        	 console.log(res);
         	 this.papers_to_approve = res as any[];
          
           
        },
        err => {
          
        }
      );
          
           
        },
        err => {
          
        }
      );
  }

  reject(p){
  	var commentDto = {
  		paperId: p.id 
  	}
  	let x = this.user_service.reject(commentDto);
  	x.subscribe(
        res => {

           
          let y = this.user_service.getPapersToReview('chief');
  			y.subscribe(
       		 res => {

           
        	 console.log(res);
         	 this.papers_to_approve = res as any[];
          
           
        },
        err => {
          
        }
      );
          
           
        },
        err => {
          
        }
      );
  }


   approve(p){ 
   	this.current = p.id;
  	let x = this.user_service.approve(p);
  	x.subscribe(
        res => {

            if(res){
           	this.notifier.notify('success', 'Approval successful. System will forward Reviewer assignment to the field editor.')
          let y = this.user_service.getPapersToReview('chief');
  			y.subscribe(
       		 res => {

           
        	 console.log(res);
         	 this.papers_to_approve = res as any[];
          
           
		        },
		        err => {
		          
		        }
		      );

          let z = this.taskService.getTasks( JSON.parse(localStorage.getItem('user')).username);

          z.subscribe(
            res => {
              console.log("tasks:");
              console.log(res);
            },
            err => {

            }
            )










         } else {
          	this.notifier.notify('default', 'There is no active editor for this field. Please choose reviewers yourself.')
          	this.activeEditor = 'no';
          	 let assignComp = new ChiefEditorHomeComponent(this.user_service,this.notifier, 
	    	this.paper_service, this.router, this.taskService);
	        assignComp.p = p;
 			this.router.navigate(['assign']);
   

         }
           
        },
        err => {
          
        }
      );
   }

 getPapersToAssign(username){
  	 let y = this.user_service.getPapersToAssign('editor');
  			y.subscribe(
       		 res => {
       		 	if(res!=null){
            	
         		 this.papers_to_assign = res as any[];
          	   }
          	   else {
          	   	 this.notifier.notify('info', 'No new tasks...');
          	   	 this.papers_to_assign = [];
          	   }
           
	        },
	        err => {
	          
	        })
  }

   printAssigned(){
  	console.log(this.assignedReviewersModel)
  }

  chooseReviewersChief(paper){
  	 if(this.assignedReviewersModel.length>=2){
  	 let y = this.user_service.chooseReviewersChief(paper, this.assignedReviewersModel);
  			y.subscribe(
       		 res => {
       		 	 
            	 this.notifier.notify('success', 'Successful assignment.');
         		 //this.getPapersToAssign('chief');
         		 this.getPapersToReview('chief');
          	    
	        },
	        err => {
	          this.notifier.notify('error', 'Error while assigning. Please try again.');
	        })
  	} else {
  	 this.notifier.notify('error', 'Please assign two or more reviewers.');
  }
}
  
  goToPaper(p){
    let assignComp = new ChiefEditorHomeComponent(this.user_service,this.notifier, 
    	this.paper_service, this.router, this.taskService);
    assignComp.p = p;
   
  	 this.router.navigate(['assign']);
   
  }


get p():any{
    return this.paper_service.paper;
  }

  set p(value: any){
    this.paper_service.paper = value;
  }
}
