import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import {PaperService} from '../services/paper/paper.service';

@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {
 
  constructor(private notifier: NotifierService, 
  	private paper_service: PaperService) { }
    private reviewers = [];
    assignedReviewersModel = null;
  ngOnInit() {
  	 	let x = this.paper_service.getReviewersFromMagazine('username');
  		x.subscribe(
        res => {
 			 this.reviewers = res as any[];
        },
        err => {
          
        }
      );
  }

   get p():any{
    return this.paper_service.paper;
  }

  set p(value: any){
    this.paper_service.paper = value;
  }

  printAssigned(){
    console.log(this.assignedReviewersModel)
  }

  chooseReviewers(){
    let x = this.paper_service.setRevs(this.assignedReviewersModel);
      x.subscribe(
        res => {
          console.log('assigned');
        },
        err => {
          
        }
      );
  }
}
