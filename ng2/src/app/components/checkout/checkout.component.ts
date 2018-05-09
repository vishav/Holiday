import {Component, OnInit} from '@angular/core';
import {SearchQuery} from "../../models/SearchQuery";
import {ShoppingcartService} from "../../services/shoppingcart.service";
import {PricingService} from "../../services/pricing.service";
import {OrderService} from "../../services/order.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cartitems: SearchQuery[];
  total: number;
  pricing: any = null;
  paymentResponse: any;

  constructor(private cartservice: ShoppingcartService,
              private pricingservice: PricingService,
              private orderservice: OrderService,
              private router: Router) {
  }

  ngOnInit() {
    this.cartservice.getShoppingCart().subscribe(cartitems => {
      console.log("returned shopping cart from local storage");
      this.cartitems = cartitems;
      this.total = this.totalprice();
      console.log(this.total);
    });

    this.pricingservice.getPricing().subscribe(pricing => {
      if (pricing) {
        this.pricing = pricing;
      }
      console.log("the pricing object:");
      console.log(pricing);
    });
  }

  totalprice() {
    var total = 0;
    for (var i = 0; i < this.cartitems.length; i++) {
      total += this.itemprice(this.cartitems[i]);
    }
    return Math.round(total * 100) / 100;
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

  onPaymentStatus(response): void{
    this.paymentResponse = response;
    if(this.paymentResponse && this.paymentResponse.success){
      const paymentid = this.paymentResponse.target.id;
      this.orderservice.pushtoorders(paymentid, this.total);
      this.router.navigate(['/success'])
    }else{
      this.router.navigate(['/failure']);
    }
  }
}
