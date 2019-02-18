import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  fetchUsers() {
    return this.httpClient.get("http://localhost:8080/user/fetch") as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/register/".concat(taskId), user) as Observable<any>;
  }

  loginUser(user, taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/login/".concat(taskId), user) as Observable<any>;
  }

  getPapersToReview(username) {
    return this.httpClient.get("http://localhost:8080/chief/getAllPapersToReview/"+username) as Observable<any>;
  }

  modify(commentDto) {
    return this.httpClient.post("http://localhost:8080/chief/needsModification/"+JSON.parse(localStorage.getItem('pid')).value, commentDto) as Observable<any>;
  }

  reject(commentDto) {
    return this.httpClient.post("http://localhost:8080/chief/reject/"+JSON.parse(localStorage.getItem('pid')).value, commentDto) as Observable<any>;
  }

  getReviewers(username){
    return this.httpClient.get("http://localhost:8080/editor/getReviewers/"+username) as Observable<any>;
  }

  getPapersToAssign(username){
    return this.httpClient.get("http://localhost:8080/editor/getPapersToAssign/"+username) as Observable<any>;
  }

  approve(paper) {
    return this.httpClient.post("http://localhost:8080/chief/approve/"+JSON.parse(localStorage.getItem('pid')).value, paper) as Observable<any>;
  }

  getPapersToAssignChief(code){
    return this.httpClient.get("http://localhost:8080/editor/getPapersToAssignChief/"+code) as Observable<any>;
  } 

  chooseReviewers(paper, reviewers) {
    return this.httpClient.post("http://localhost:8080/editor/chooseReviewers/"
      +JSON.parse(localStorage.getItem('pid')).value
      +"/"
      +'username'
      +'/'
      +paper.id, reviewers) as Observable<any>;
  }

  chooseReviewersChief(paper, reviewers) {
    return this.httpClient.post("http://localhost:8080/chief/chooseReviewers/"
      +JSON.parse(localStorage.getItem('pid')).value
      +"/"
      +'username'
      +'/'
      +paper.id, reviewers) as Observable<any>;
  }

  getReviews() {
    return this.httpClient.get("http://localhost:8080/editor/getReviews/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username) as Observable<any>;
  }
  
  editorAccept() {
    return this.httpClient.post("http://localhost:8080/editor/accept/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username+"/"+'paperId', null) as Observable<any>;
  }

  editorReject() {
    return this.httpClient.post("http://localhost:8080/editor/reject/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username+"/"+'paperId', null) as Observable<any>;
  }

  anotherOpinion(rev, paperId) {
    return this.httpClient.post("http://localhost:8080/editor/additional/"+rev+"/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username+"/"+paperId, null) as Observable<any>;
  }

  partialAccept() {
    return this.httpClient.get("http://localhost:8080/editor/partialAccept/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username) as Observable<any>;
  }

  action(actionName) {
    return this.httpClient.post("http://localhost:8080/editor/action/"+actionName+"/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username+"/"+'paperId', null) as Observable<any>;
  }

  
}
