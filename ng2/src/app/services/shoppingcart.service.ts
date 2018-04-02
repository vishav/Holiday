import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/map'
import {Observable} from "rxjs";

import {SearchQuery} from '../models/SearchQuery';
import {AuthenticationService} from '../services/authentication.service';

@Injectable()
export class ShoppingcartService {
  public shoppingCart: SearchQuery[];


  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {
    this.setFromSavedCart();
  }

  getShoppingCart(): Observable<SearchQuery[]> {
    //return this.shoppingCart;
    console.log("getting saved cart");
    if (this.authenticationService.isLoggedIn()) {

      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + this.authenticationService.getToken(),
      });
      // headers=headers.append('observe','response');
      let options = {headers};

      // first setting shopping cart variable locally
      this.http.get<SearchQuery[]>('/getShoppingCart/' + this.authenticationService.currentUser().userId, options).subscribe(usercartitems => {
        if (usercartitems)
          console.log(usercartitems);
        this.shoppingCart = usercartitems;
      });

      // returning observable to calling component
      return this.http.get<SearchQuery[]>('/getShoppingCart/' + this.authenticationService.currentUser().userId, options);
    } else {
      let savedShoppingCart = JSON.parse(localStorage.getItem('mea2necomm-shopping-cart'));
      if (savedShoppingCart) {
        this.shoppingCart = savedShoppingCart;
      } else {
        this.shoppingCart = [];
      }
      return Observable.of(this.shoppingCart);
    }
  }

  addItem(cartItem: SearchQuery) {
    if(!this.shoppingCart){
      this.shoppingCart = [];
      console.log("initalizing shopping cart")
    }
    this.shoppingCart.push(cartItem);
    console.log("adding item to cart: " + this.shoppingCart);
    this.updateStorage();
  }

  removeItem(index: number) {
    if (index < this.shoppingCart.length && index >= 0) {
      this.shoppingCart.splice(index, 1);
      this.updateStorage();
    }
  }

  addItems(cartItems: SearchQuery[]) {
    this.shoppingCart = this.shoppingCart.concat(cartItems);
    this.updateStorage();
  }

  clearItems() {
    this.shoppingCart = [];
    this.updateStorage();
  }

  // this function integrates the locally stored cart with the server
  // call on login
  updateLocalStorageToServer() {
    //return Observable.of(true);

    if (this.authenticationService.isLoggedIn()) {
      let savedShoppingCart = JSON.parse(localStorage.getItem('mea2necomm-shopping-cart'));
      if (savedShoppingCart) {


        const headers = new HttpHeaders({
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + this.authenticationService.getToken()
        });

        let options = {headers};
        console.log("inside shopping cart sending request");
        return this.http.get<SearchQuery[]>('/getShoppingCart/' + this.authenticationService.currentUser().userId, options)
          .map(res => {
            console.log("updateLocalStorageToServer:", res);
            if (res) {
              // let usercartitems = res;
              this.shoppingCart = res;
            }
            this.addItems(savedShoppingCart);
            //resetting local storage
            localStorage.setItem('mea2necomm-shopping-cart', JSON.stringify([]));
            return true;
          });


      } else {
        console.log("inside shopping cart no saved cart");
        return Observable.of(true);
      }
    } else { // user not authenticated
      console.log("inside shopping cart not logged in");
      return Observable.of(false);
    }

  }


  //function sets the local cart variable from either the server or the local storage
  private setFromSavedCart() {
    console.log("getting saved cart");
    if (this.authenticationService.isLoggedIn()) {

      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + this.authenticationService.getToken()
      });

      let options = {headers};
      console.log("setting from saved cart");
      // first setting shopping cart variable locally
      this.http.get<SearchQuery[]>('/getShoppingCart/' + this.authenticationService.currentUser().userId, options)
        .subscribe(usercartitems => {
          if (usercartitems) {
            console.log(usercartitems);
            this.shoppingCart = usercartitems;
          }
        });
    } else {
      let savedShoppingCart = JSON.parse(localStorage.getItem('mea2necomm-shopping-cart'));
      if (savedShoppingCart) {
        this.shoppingCart = savedShoppingCart;
      } else {
        this.shoppingCart = [];
      }
    }
  }

  // this function updates the locally stored cart to the server
  private updateStorage() {
    if (this.authenticationService.isLoggedIn()) {
      console.log("setting logged in users cart");
      var user = this.authenticationService.currentUser();
      // add to server
      //console.log(user);
      //console.log(JSON.stringify({ useremail: user.email, cartItems: this.shoppingCart }));

      const headers = new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.authenticationService.getToken(),
        'user_id': user.userId.toString(),
      });
      // headers=headers.append('observe', 'response');

      let options = {headers};
      //console.log(headers);

      this.http.post('/saveShoppingCart', JSON.stringify(this.shoppingCart
      ), options)
        .map((response) => {

          if (response) {
            console.log("saved cart successfully to db");
            // return true to indicate successful saved
            return true;
          } else {
            console.log("error while saving cart to db. Status: " + response);
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
        .subscribe();

    } else {
      console.log("setting non-logged in users cart");
      localStorage.setItem('mea2necomm-shopping-cart', JSON.stringify(this.shoppingCart));
    }

  }

  getItemNum() {
    return this.shoppingCart.length;
  }

  getPricePerYear() {
    return 5;
  }

}
