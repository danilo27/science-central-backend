import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../services/magazines/magazine.service';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import {TaskService} from '../services/task/task.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
 
  private formFieldsDto = null;
  public formFields = []; 
  private processInstance = "";
  private enumValues = [];
  public tasks = [];
  private submitAction = "";

  private show = 'all';
  private hasReviews = 'false';
  private comments = [];

  bookForm: FormGroup;

  constructor(private magazineService : MagazineService, private router:Router
    , private notifier: NotifierService, private taskService: TaskService) { }
  private magazines: any;

  ngOnInit() { 


    this.bookForm = new FormGroup({
                title: new FormControl('',[Validators.required]),
               // author: new FormControl('',[Validators.required]),
                keywords : new FormControl('',[Validators.required]),
                filename:new FormControl(''),
              //  field : new FormControl('',[Validators.required])
            });


  	let x = this.magazineService.getAll("id");
    x.subscribe(
        res => {
          console.log(res);
      
            this.magazines = res as any[];
      
        },
        err => {
          
        }
      );

    
    this.getComments();
  }
  getComments(){
     let y = this.magazineService.getComments();
     y.subscribe(
        res => {
          console.log('coms:',res);
      
            this.comments = res as any[];
      
        },
        err => {
          
        }
      );
  }
  get m():any{
    return this.magazineService.magazine;
  }

  set m(value: any){
    this.magazineService.magazine = value;
  }
   
  goToMagazine(m){
    let home = new HomeComponent(this.magazineService, this.router, this.notifier, this.taskService);
    home.m = m;
    console.log('magazineje: ', m);
  	let x = this.magazineService.chooseMagazine(m);
  	x.subscribe(
        res => {

          this.show = 'chosen';
          console.log(res);
          this.getDynamicForm(res);
           
        },
        err => {
          
        }
      );
 
  }

  getDynamicForm(res){
     
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
      x = this.magazineService.submitPaper(o, this.formFieldsDto.taskId,  JSON.parse(localStorage.getItem("user")).username);
      x.subscribe(
        res => {
          this.notifier.notify('success', 'You successfuly submitted your paper.');
          this.getDynamicForm(res);
        },
        err => {
          this.notifier.notify('error', 'There was an error while submitting your paper.');
        }
      );
     
 
  }
  _fileName:string;
  fileChange(event){


    let fileList: FileList = event.target.files;
        if(fileList.length > 0) {
            let file: File = fileList[0];
            this.taskService.sendFile(file).subscribe(x=>{
              this._fileName = x['filename'];
                if(x['NOXML']!=null){
                    alert("No metadata");
                    this.bookForm = new FormGroup({
                        title: new FormControl('',[Validators.required]), 
                        keywords : new FormControl(''), 
                        filename: new FormControl(x['filename'])
                        });
                    return;
                }
                this.bookForm = new FormGroup({
                        title: new FormControl(x['Title'],[Validators.required]), 
                        keywords : new FormControl(x['Keywords']), 
                        filename: new FormControl(x['filename'])
                });
            });
        }



  }

  modify(content:string){
    var dto = {
      value : content
    }
    let x = this.magazineService.modify(dto);
    x.subscribe(
        res => {
          this.notifier.notify('success', 'You successfuly submitted your paper.');
          this.getDynamicForm(res);
          this.getComments();
        },
        err => {
          this.notifier.notify('error', 'There was an error while submitting your paper.');
        }
      );

  }
 
}
