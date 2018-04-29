import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import {Observable} from 'rxjs';
import {SearchQuery} from '../models/SearchQuery';
import {OrderService} from './order.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {Payment} from "../models/payment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class PaymentService {
  paymentid: string;
  cart: SearchQuery[];

  constructor(private http: HttpClient,
              private orderservice: OrderService,
              private authservice: AuthenticationService,
              private router: Router) {
  }

  createpayment(data) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    const options = {headers};
    this.http.post('/payment', JSON.stringify({
      expmon: data.expire_month,
      expyear: data.expire_year,
      fname: data.first_name,
      lname: data.last_name,
      method: data.method,
      cnum: data.number,
      total: data.total,
      type: data.type,
      cvv: data.cvv
    }), options)
      .catch((error, caught) => {
      if (error.status === 400) {
        console.log(error);
      }
      return Observable.throw(error);
    })
      .subscribe(
        (result:Payment) => {
          if (!result.success) {
            this.router.navigate(['/failure'])
          }else if (result.success) {
            this.paymentid = result.transactionID;
            this.orderservice.pushtoorders(this.paymentid, data.total);
            this.router.navigate(['/success'])
          }
        }, error => {
          console.log(error);
          this.router.navigate(['/failure'])
        });
  }
}
