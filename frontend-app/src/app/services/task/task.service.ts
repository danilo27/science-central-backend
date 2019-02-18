import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  startProcess() {
    return this.httpClient.get("http://localhost:8080/task/startProcess") as Observable<any>;
  }

  startRegistrationSubprocess() {
    return this.httpClient.get("http://localhost:8080/task/startRegistrationSubprocess/") as Observable<any>;
  }

  getTasks(username){
  	 return this.httpClient.get("http://localhost:8080/welcome/getTasks/"+JSON.parse(localStorage.getItem('pid')).value+"/"+username) as Observable<any>;
  }
  	
   sendFile(file:any){
            let formData:FormData = new FormData();
            formData.append('file', file);
        
          
            return this.httpClient.post("http://localhost:8080/file/addFile",formData) as Observable<any>;
    }
}
