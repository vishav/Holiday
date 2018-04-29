import { Component, OnInit } from '@angular/core';

import { OrderService } from '../../services/order.service';
import { OrderItem } from "../../models/OrderItem";
import { SearchQuery } from "../../models/SearchQuery";
import { PaymentService } from "../../services/payment.service";

@Component({
  selector: 'app-order',
  templateUrl: 'order.component.html',
  styleUrls: ['order.component.css']
})
export class OrderComponent implements OnInit {

  orders: OrderItem[] = [];
  cartitems: SearchQuery[];
  payment: any;
  orderId: number;

  constructor(private orderService: OrderService, private paymentservice: PaymentService) {

  }

  ngOnInit() {
    this.orderService.getAllOrders().subscribe(userorders => {
      if (userorders) {
        this.orders = userorders;
      }
      console.log(userorders);
    });
  }

  getorderdetails(i) {
    this.cartitems = this.orders[i].item;
    this.orderId = this.orders[i].orderId;
  }

/*  getpayment(val) {

    this.paymentservice.getPaymentDetails(val).subscribe(res => {
      console.log(res);
      this.payment = res;
    });
  }*/
}
