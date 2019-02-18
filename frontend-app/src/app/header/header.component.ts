import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../services/authentication/authentication.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
   
  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
  	this.username = JSON.parse(localStorage.getItem('user')).username;
  }

  get username():any{
    return this.authService.username;
  }

  set username(value: any){
    this.authService.username = value;
  }

  loggedIn(){
  	if(this.username!=null)
  		return true;
  	return false;
  }

  logout(){
  	localStorage.removeItem('user');
  	localStorage.removeItem('pid');
  	this.username = null;
  }


}
