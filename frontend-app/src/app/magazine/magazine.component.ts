import { Component, OnInit } from '@angular/core';
import {MagazineService} from '../services/magazines/magazine.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {
 
  constructor(private magazineService : MagazineService, private router:Router) { }

  ngOnInit() {

  }
  get m():any{
  	return this.magazineService.magazine;
  }

  set m(value: any){
  	this.magazineService.magazine = value;
  }
}
