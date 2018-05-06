import {Injectable} from '@angular/core';
import {AuthenticationService} from './authentication.service';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ResponseContentType} from "@angular/http";

@Injectable()
export class TransactionService {

  constructor(private http: HttpClient, private authservice: AuthenticationService) { }

  getTransactions(data) {
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers};

    const parameter = data.country + '/' + data.state + '/' + data.city + '/' + data.fname + '/' + data.lname + '/' + data.useremail + '/' + data.fromDate + '/' + data.toDate;
    return this.http.get('/transactions/' + parameter, options);
  }

  downloadTransactions(data){
    const headers = new HttpHeaders({
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers, responseType:'blob' as 'blob'};

    // const options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    const parameter = data.country + '/' + data.state + '/' + data.city + '/' + data.fname + '/' + data.lname + '/' + data.useremail + '/' + data.fromDate + '/' + data.toDate;
    return this.http.get('/download/' + parameter, options);
  }

  refundTransaction(data) {
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + this.authservice.getToken()
    });

    const options = {headers};

    return this.http.post('/refund', JSON.stringify({
      orderId: data.orderId,
      refundAmount: data.refundAmount
    }), options);
  }
}
