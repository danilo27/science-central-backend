import { Injectable } from '@angular/core';

import { Headers, RequestOptions } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileChangeService {

  constructor(private httpClient : HttpClient) { }

  changeProfile(profile){
    profile.originalUsername = JSON.parse(localStorage.getItem('user')).username;
    let x = this.httpClient.put('http://localhost:8080/profile', profile) as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
          
        localStorage.setItem('user', JSON.stringify(res));
        localStorage.setItem('role', res.role);
      },
      err => {
        console.log("Error occured");
      }
    );
  }
}
