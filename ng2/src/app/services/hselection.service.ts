import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import 'rxjs/add/operator/map';

@Injectable()
export class HselectionService {

  constructor(private http: HttpClient, private authservice: AuthenticationService) {
  }

  // Get all posts from the API
  getAllCountries() {
    return this.http.get('/getFreeHolidays/countries');
  }

  getStates(country) {
    return this.http.get('/getFreeHolidays/countryStates/' + country);
  }

  getCountryCites(country) {
    return this.http.get('/getFreeHolidays/countryCities/' + country);
  }

  getCities(state, country) {
    return this.http.get('/getFreeHolidays/countryStateCities/' + state + '/' + country);
  }

  getHolidays(data) {
    console.log(this.authservice.getToken());
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });
    const options = {headers};
    return this.http.get('/holidays/' + data.country + '/' + data.state + '/' + data.city + '/' + data.fromYear + '/' + data.fromMonth + '/' + data.fromDay + '/' + data.toYear + '/' + data.toMonth + '/' + data.toDay, options);
  }

  getFreeHolidays(data) {
    console.log(data);
    return this.http.get('/getFreeHolidays/' + data.country + '/' + data.state + '/' + data.city + '/' + data.fromYear + '/' + data.fromMonth + '/' + data.fromDay + '/' + data.toYear + '/' + data.toMonth + '/' + data.toDay);
  }

  // getYear() {
  //   return this.http.get(GlobalConstants.baseURL);
  // }
}
