import { Injectable } from '@angular/core';
import { Headers, RequestOptions } from "@angular/http";
import { map } from 'rxjs/operators';
import { ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

   
}
