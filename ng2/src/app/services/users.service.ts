import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import {Observable} from "rxjs";

import {SearchQuery} from '../models/SearchQuery';
import {AuthenticationService} from '../services/authentication.service';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class UsersService {

  constructor(private http: HttpClient,
              private authservice: AuthenticationService) {
  }

  getUsers() {
    //return this.shoppingCart;
    console.log("getting saved cart");
    if (this.authservice.isLoggedIn()) {

      const headers = new HttpHeaders({
        'Accept': 'application/json',
        // 'Content-Type': 'application/json'
        'Authorization': 'Bearer ' + this.authservice.getToken()
      });

      const options = {headers};

      // returning observable to calling component
      return this.http.get('/users/', options);
    }
  }

  resetPassword(useremail) {

    //if(this.authservice.isLoggedIn()){

    /*    let headers = new Headers({'Content-Type': 'application/json', 'Accept': 'application/json'});
        //headers.append('Authorization', 'Bearer '+ this.authservice.getToken());
        let options = new RequestOptions({headers: headers});*/

    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
      // 'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers};
    // returning observable to calling component

    return this.http.post('/resetPassword', JSON.stringify({
      // userId: this.authservice.currentUser().userId,
      email: useremail
    }), options);
  }

  getUserByUUID(uuid) {
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      // 'Content-Type': 'application/json'
      // 'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers};

    // returning observable to calling component
    console.log("sending request for uuid " + uuid);
    return this.http.get('/uuiduser/' + uuid, options);
  }

  updatePassword(uuid, password) {

    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers};

    // returning observable to calling component

    return this.http.post('/changePassword', JSON.stringify({requesteduuid: uuid, password: password}), options);

  }
}
