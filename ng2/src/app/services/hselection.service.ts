import { Injectable } from '@angular/core';

// import { Http, RequestOptions, Headers, Jsonp } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import 'rxjs/add/operator/map';

import {GlobalConstants} from '../constants/GlobalConstants';

@Injectable()
export class HselectionService {

  constructor(private http: HttpClient, private authentication: AuthenticationService) {
  }

  // Get all posts from the API
  getAllCountries() {
    return this.http.get('/getFreeHolidays/countries');
  }

  getStates(country) {
    return this.http.get('/getFreeHolidays/countryStates/'+ country);
  }

  getCountryCites(country) {
    return this.http.get('/getFreeHolidays/countryCities/' + country);
  }

  getCities(state, country) {
    return this.http.get('/getFreeHolidays/countryStateCities/' + state + '/' + country);
  }

  getHolidays(data) {
    console.log(this.authentication.getToken());
    const headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    headers.append('Authorization', 'Bearer ' + this.authentication.getToken());
    const options = {headers};
    return this.http.get('/' + data.country + '/' + data.state + '/' + data.city + '/' + data.fromYear + '/' + data.fromMonth + '/' + data.fromDay + '/' + data.toYear + '/' + data.toMonth + '/' + data.toDay, options);
  }

  getFreeHolidays(data) {
    console.log(data);
    return this.http.get('/getFreeHolidays/' + data.country + '/' + data.state + '/' + data.city + '/' + data.fromYear + '/' + data.fromMonth + '/' + data.fromDay + '/' + data.toYear + '/' + data.toMonth + '/' + data.toDay);
  }

  // getYear() {
  //   return this.http.get(GlobalConstants.baseURL);
  // }
}
