import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import { Observable } from 'rxjs';

@Injectable()
export class AuthenticationService {
  public token: string;

  constructor(private http: Http) {
  }

  getToken() {
    return localStorage.getItem('mea2necomm-token');
  }

  saveToken(token) {
    console.log(token);
    localStorage.setItem('mea2necomm-token', token);
  }

  login(useremail: string, password: string) {
    return this.http.post('/api/login', {email: useremail, password: password})
      .map((response) => {
        // login successful if there's a jwt token in the response
        const token = response.json() && response.json().token;
        if (token) {
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
    return this.http.post('/api/register', {
      fname: user.firstName,
      lname: user.lastName,
      password: user.password,
      email: user.email
    })
      .map(res => {
        const token = res.json() && res.json().token;
        console.log(token);
        if (token) {
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
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp > Date.now() / 1000;

    }
    else {
      return false;
    }
  }

  // Returns the user if user is logged in, or returns false if not logged in
  currentUser() {
    if (this.isLoggedIn()) {
      const token = this.getToken;
      const payload = JSON.parse(atob(token().split('.')[1]));
      const user = {
        name: payload.fname,
        email: payload.email,
        role: payload.role
      };
      return user;
    }
    else return null;
  }
}
