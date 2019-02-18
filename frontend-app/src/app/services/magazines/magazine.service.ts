import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {
  magazine: any;
  constructor(private httpClient: HttpClient) { }

  getAll(taskId) {
    return this.httpClient.get("http://localhost:8080/welcome/getAllMagazines/".concat(taskId)) as Observable<any>;
  }

  chooseMagazine(m){
  	 return this.httpClient.get("http://localhost:8080/magazine/chooseMagazine/"+m.issn+'/'+ JSON.parse(localStorage.getItem('pid')).value.toString()) as Observable<any>;
  }

  submitPaper(paper, taskId, username){
    return this.httpClient.post("http://localhost:8080/magazine/submitPaper/"+taskId+"/"+username, paper) as Observable<any>;
  }

  submit(paper, taskId, username){
    return this.httpClient.post("http://localhost:8080/editor/submit/"+taskId+"/"+username, paper) as Observable<any>;
  }

  getTask() {
    return this.httpClient.get("http://localhost:8080/welcome/getTask/".concat(JSON.parse(localStorage.getItem('pid')).value)) as Observable<any>;
  }

  getComments() {
    return this.httpClient.get("http://localhost:8080/paper/getComments/".concat(JSON.parse(localStorage.getItem('user')).username)) as Observable<any>;
  }

  modify(content) {
    return this.httpClient.post("http://localhost:8080/paper/modify/".concat(JSON.parse(localStorage.getItem('user')).username),content) as Observable<any>;
  }
  
  getActions(){
    return this.httpClient.get("http://localhost:8080/editor/getActions/".concat(JSON.parse(localStorage.getItem('user')).username)) as Observable<any>;
  }
}


