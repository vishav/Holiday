import {Injectable} from '@angular/core';
import {ShoppingcartService} from "./shoppingcart.service";
import {AuthenticationService} from "./authentication.service";
import {Observable} from 'rxjs'
import {Router} from '@angular/router'
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OrderItem} from "../models/OrderItem";

@Injectable()
export class OrderService {

  constructor(private cartservice: ShoppingcartService,
              private authservice: AuthenticationService,
              private http: HttpClient,
              private router: Router) {
  }

  pushtoorders(paymentid, total) {
    this.cartservice.getShoppingCart().subscribe(cartitems => {

      var user = this.authservice.currentUser();
      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.authservice.getToken(),
        'userId': user.userId.toString(),
      });

      const options = {headers};

      this.http.post('/orders', JSON.stringify({
        item: cartitems,
        paymentId: paymentid,
        total: total
      }), options)
        .map((response) => {
          if (response) {
            console.log("saved order successfully to db");
            // return true to indicate successful saved
            return true;
          } else {
            console.log("error while saving order to db. Status: " + response);
            // return false to indicate it did not save properly
            return false;
          }
        })
        .catch((error, caught) => {
          if (error.status === 401) {
            console.log(error);

          }
          return Observable.throw(error);
        })
        .subscribe(res => {
          console.log("return order value type:", typeof(res));
          if (res === true) {
            console.log("clearing cart items");
            this.cartservice.clearItems().subscribe();
            this.router.navigate(['/success'])
          }
          else {
            console.log("not clearing cart items");
            this.router.navigate(['/failure'])
          }
        }, error => {
          console.log(error);
          this.router.navigate(['/failure'])
        });
    });

  }

  getAllOrders():Observable<OrderItem[]> {
    if (this.authservice.isLoggedIn()) {

      const user = this.authservice.currentUser();
      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + this.authservice.getToken()
      });

      const options = {headers};

      return this.http.get<OrderItem[]>('/orders/' + user.userId, options);
    }
  }

}
