import { Injectable } from '@angular/core';

import { RouterModule, Routes, Router } from '@angular/router';

import { Headers, RequestOptions } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  username: any;
  constructor(private httpClient: HttpClient, private router: Router) { }
  log(dto){
    let x = this.httpClient.post('http://localhost:8080/welcome/login', dto) as Observable<any>;

     x.subscribe(
      res => {
        console.log(res);
         
        localStorage.setItem('user', JSON.stringify(res));
        this.username = JSON.parse(localStorage.getItem('user')).username;

        if(res!=null){
          if(res.role == 'AUTHOR'){
            this.getPidAndNavigate('/home');  
          } else if (res.role == 'CHIEF_EDITOR'){
            this.getPidAndNavigate('/chief_editor');  
          } else if (res.role == 'EDITOR'){
            this.getPidAndNavigate('/editor'); 
          } else if (res.role == 'REVIEWER'){
            this.getPidAndNavigate('/reviewer'); 
          }

           
        }
        //alert("Success!");
      },
      err => {
        console.log("Error occured");
        alert("Error occured!");
      }
    );
  }

  getPidAndNavigate(url){
    let y = this.getPid();

            y.subscribe(
            res => {
              console.log(res);
              if(res!=null){
                 

                localStorage.setItem('pid', JSON.stringify(res));
                this.router.navigateByUrl(url);

              }
        
            },
            err => {
              console.log("Error occured");
              alert("Error occured!");
            }
          );
  }

  // login(dto,taskId){
  //   let x = this.httpClient.post('http://localhost:8080/welcome/login/'.concat(taskId), dto) as Observable<any>

  //   x.subscribe(
  //     res => {
  //       console.log(res);
  //       if(res!=null){
  //         if(res.role == 'AUTHOR'){
  //           this.router.navigateByUrl('/home');
  //         } else if (res.role == 'CHIEF_EDITOR'){
  //           this.router.navigateByUrl('/chief_editor');
  //         } else if (res.role == 'EDITOR'){
  //           this.router.navigateByUrl('/editor');
  //         } else if (res.role == 'REVIEWER'){
  //           this.router.navigateByUrl('/reviewer');
  //         }

  //         localStorage.setItem('data', JSON.stringify(res));


  //         localStorage.setItem('user', JSON.stringify(res));
  //        // localStorage.setItem('role', res.role);
          
  //         //window.location.reload();
  //        // this.router.navigateByUrl('/home');

  //        this.getPid();
  //       }
  //       //alert("Success!");
  //     },
  //     err => {
  //       console.log("Error occured");
  //       alert("Error occured!");
  //     }
  //   );
  // }

   getPid(){
    return this.httpClient.get('http://localhost:8080/welcome/getPid') as Observable<any>

    
  }

  dummy(to){
    let x = this.httpClient.get('http://localhost:8080/welcome/get') as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
          
        
        alert("POGODJENNNNNNNNNNNNNNNNN!");
      },
      err => {
        console.log("Error occured");
        alert("Error occured!");
      }
    );
  }

  logout(){
    let x = this.httpClient.post('http://localhost:8080/logout', {}) as Observable<any>

    x.subscribe(
      res => {
        console.log(res);
        window.location.reload();
        this.router.navigateByUrl('/login');
      },
      err => {
        console.log("Error occured");
      }
    );
  }
    
}
