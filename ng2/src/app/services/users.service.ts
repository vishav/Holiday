import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import { Observable } from "rxjs";

import { SearchQuery } from '../models/SearchQuery';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class UsersService {

  constructor(private http: Http,
              private authenticationService: AuthenticationService) {
  }

  getUsers() {
    //return this.shoppingCart;
    console.log("getting saved cart");
    if (this.authenticationService.isLoggedIn()) {

      let headers = new Headers({'Accept': 'application/json'});
      headers.append('Authorization', 'Bearer ' + this.authenticationService.getToken());
      let options = new RequestOptions({headers: headers});

      // returning observable to calling component
      return this.http.get('/users/', options)
        .map(res => res.json());
    }
  }

  resetPassword(useremail) {

    //if(this.authenticationService.isLoggedIn()){

    let headers = new Headers({'Content-Type': 'application/json', 'Accept': 'application/json'});
    //headers.append('Authorization', 'Bearer '+ this.authenticationService.getToken());
    let options = new RequestOptions({headers: headers});

    // returning observable to calling component

    return this.http.post('/resetpassword', JSON.stringify({useremail: useremail, reset: true}), options)
      .map(res => res.json());
    //}
  }

  getUserByUUID(uuid) {
    let headers = new Headers({'Accept': 'application/json'});
    //headers.append('Authorization', 'Bearer '+ this.authenticationService.getToken());
    let options = new RequestOptions({headers: headers});

    // returning observable to calling component
    console.log("sending request for uuid " + uuid);
    return this.http.get('/uuiduser/' + uuid, options)
      .map(res => res.json());
  }

  updatePassword(uuid, password) {


    let headers = new Headers({'Content-Type': 'application/json', 'Accept': 'application/json'});
    headers.append('Authorization', 'Bearer ' + this.authenticationService.getToken());
    let options = new RequestOptions({headers: headers});

    // returning observable to calling component

    return this.http.post('/changepassword', JSON.stringify({requesteduuid: uuid, password: password}), options)
      .map(res => res.json());

  }
}
