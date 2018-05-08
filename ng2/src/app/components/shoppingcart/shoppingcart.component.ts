import {Component, OnInit} from '@angular/core';
import {ShoppingcartService} from '../../services/shoppingcart.service';
import {Router} from '@angular/router';
import {PricingService} from "../../services/pricing.service";

@Component({
  selector: 'app-shoppingcart',
  templateUrl: 'shoppingcart.component.html',
  styleUrls: ['shoppingcart.component.css']
})
export class ShoppingcartComponent implements OnInit {

  pricing: any = null;
  cartitems: any = [];
  cartnumber: number;
  total: number;

  constructor(private cartservice: ShoppingcartService,
              private pricingservice: PricingService,
              private router: Router) {
  }

  ngOnInit() {
    this.pricingservice.getPricing().subscribe(pricing => {
      if (pricing) {
        this.pricing = pricing;
      }
      console.log("the pricing object:");
      console.log(pricing);
      this.cartservice.getShoppingCart().subscribe(usercartitems => {
        if (usercartitems) {
          console.log("shopping cart component");
          console.log(usercartitems);
          this.cartitems = usercartitems;
        }
        this.total = this.totalprice();
      });
    });


    console.log(this.total);
  }

  removeallfromcart() {
    this.cartservice.clearItems().subscribe();
    this.cartitems = [];
    this.cartnumber = 0;
  }

  removefromcart(index: number) {
    console.log("delete index: " + index);
    this.cartservice.removeItem(index).subscribe(cartItems => {
      console.log('type of cartitems shoppingcart:'+typeof(cartItems));
      this.cartitems=cartItems;
      this.cartnumber = cartItems.length;
      this.total = this.totalprice();
      console.log("cartnumber from delete:" + this.cartnumber);
    });
  }

  totalprice() {
    var total = 0;
    for (var i = 0; i < this.cartitems.length; i++) {
      total += this.itemprice(this.cartitems[i]);
    }
    return total;
  }

  daydiff(first, second) {
    return Math.round((second - first) / (1000 * 60 * 60 * 24));
  }

  itemprice(item) {
    var itemprice = 0;
    if (item.state == "State") {
      itemprice = this.pricing.countryPrice;
    } else if (item.city == "City") {
      itemprice = this.pricing.statePrice;
    } else {
      itemprice = this.pricing.cityPrice;
    }
    var fromdate = new Date(item.fromYear, item.fromMonth, item.fromDay);
    var todate = new Date(item.toYear, item.toMonth, item.toDay);
    var noofdays = this.daydiff(fromdate, todate);

    var priceperday = itemprice / 365;
    var totalprice = priceperday * noofdays;
    if (totalprice > this.pricing.minPrice)
      return (totalprice);
    else
      return (this.pricing.minPrice);

  }

  checkout() {
    this.router.navigate(['/checkout']);
  }

}
