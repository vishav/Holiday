import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import { Observable } from 'rxjs';

@Injectable()
export class AuthenticationService {
  public token: string;

  constructor(private http: HttpClient) {
  }

  getToken() {
    return localStorage.getItem('mea2necomm-token');
  }

  saveToken(token) {
    console.log(token);
    localStorage.setItem('mea2necomm-token', token);
  }

  login(useremail: string, password: string) {

    var base64Credential: string = btoa( useremail+ ':' + password);
    const headers = new HttpHeaders({"Authorization": "Basic " + base64Credential});
    // creating base64 encoded String from useremail and password

    let options = {headers};

    return this.http.post('/login', {email: useremail, password: password})
      .map((response) => {
        // login successful if there's a user object in the response
        if (response) {
          console.log("response:",response);
          const token = JSON.stringify(response);
          this.saveToken(token);

          // return true to indicate successful login
          return true;
        } else {
          // return false to indicate failed login
          return false;
        }
      })
      .catch((error, caught) => {
        if (error.status === 401) {
          console.log(error);

        }
        return Observable.throw(error);
      });
  }

  register(user) {
    console.log(user);
    return this.http.post('/register', {
      fname: user.firstName,
      lname: user.lastName,
      password: user.password,
      email: user.email
    })
      .map(res => {
        if(res){
          const token = JSON.stringify(res);
          console.log(token);
          this.saveToken(token);
          return true;
        } else {
          return false;
        }
      })
      .catch((error, caught) => {
        return Observable.throw(error);
      });
  }

  logout() {
    localStorage.removeItem('mea2necomm-token')
  }

  isLoggedIn() {
    const token = this.getToken();
    if (token) {
      console.log("inside authentication yes token");
      const payload = JSON.parse(token);
      console.log("expire date",payload.expiryDate);
      return payload.expiryDate > Date.now();
    }
    else {
      console.log("inside authentication no token");
      return false;
    }
  }

  // Returns the user if user is logged in, or returns false if not logged in
  currentUser() {
    if (this.isLoggedIn()) {
      const token = this.getToken();
      const payload = JSON.parse(token);
      const user = {
        name: payload.fname,
        email: payload.email,
        userId: payload.userId,
        role:"",
        expiryDate: payload.expiryDate
      };
      if(payload.authorities){
        user.role =  payload.authorities[0].name;
      }
      return user;
    }
    else return null;
  }
}
