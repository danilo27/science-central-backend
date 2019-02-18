import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaperService {
  paper: any;

  constructor(private httpClient: HttpClient) { }

  getReviewersFromMagazine(username){
    return this.httpClient.get("http://localhost:8080/paper/getReviewersFromMagazine/"
    	+username
    	+"/"
    	+this.paper.wantedMagazine) as Observable<any>;
  }

  setRevs(revs){
  	 return this.httpClient.post("http://localhost:8080/paper/setRevs/"+JSON.parse(localStorage.getItem('pid')).value+"/"+JSON.parse(localStorage.getItem('user')).username, revs) as Observable<any>;
  	}

   
  	reviewed(paper, taskId){
    return this.httpClient.post("http://localhost:8080/rev/reviewed/"+taskId+"/"+JSON.parse(localStorage.getItem('user')).username, paper) as Observable<any>;
  }

  getReviewersForEditor(username, magazine){
    return this.httpClient.get("http://localhost:8080/paper/getReviewersForEditor/"
      +username
      +"/"
      +magazine) as Observable<any>;
  }
}
