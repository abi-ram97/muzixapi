import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as jwt_decode from 'jwt-decode';

export const TOKEN_NAME : string = 'jwt-token';

@Injectable()
export class AuthenticationService {
  serverEndPoint:string;
  constructor(private http:HttpClient) { 
    this.serverEndPoint = 'http://localhost:8081/api/v1/userservice';
  }

  registerUser(data){
    const url = `${this.serverEndPoint}/register`;
    return this.http.post(url,data,{responseType:'text'});
  }

  loginUser(data){
    const url = `${this.serverEndPoint}/login`;
    return this.http.post(url,data);
  }

  setToken(token: string){
    sessionStorage.setItem(TOKEN_NAME,token);
  }
  getToken(){
    return sessionStorage.getItem(TOKEN_NAME);
  }
  deleteToken(){
    sessionStorage.removeItem(TOKEN_NAME);
  }
  getTokenExpirationDate(token:string): Date{
    const decoded = jwt_decode(token);
    if(decoded.exp === undefined)
      return null;
    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }
  isTokenExpired(token?:string): boolean {
    if(!token) token = this.getToken();
    if(!token) return true;
    const date = this.getTokenExpirationDate(token);
    if(date === undefined || date === null)
      return false;
    return !(date.valueOf()>new Date().valueOf());
  }


}
