import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {AuthenticationService} from "./authentication.service";
import {Router} from "@angular/router";

@Injectable()
export class PricingService {

  constructor(private http: HttpClient, private authservice: AuthenticationService) {
  }

  getPricing() {
    const headers = new HttpHeaders({
      'Accept': 'application/json'
    });
    const options = {headers};
    return this.http.get('/getPricing/', options);
  }

  savePricing(data) {

    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });
    // headers=headers.append( 'observe', 'response' );
    const options = {headers};

    const parameters = {
      countryPrice: data.countryPrice,
      statePrice: data.statePrice,
      cityPrice: data.cityPrice,
      minPrice: data.minPrice
    };
    return this.http.post('/savePricing/', JSON.stringify(parameters), options)
      .map((res) => {
        if (res) {
          return res;
        } else {
          throw new Error('This request has failed ' + res);
        }
      });
  }

}
