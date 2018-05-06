import {Component, Input, OnChanges} from '@angular/core';
import {TransactionService} from '../../../services/transaction.service';
import {AuthenticationService} from '../../../services/authentication.service';
import { saveAs } from 'file-saver';
import {TransactionResponse} from "../../../models/TransactionResponse";
import {RefundResponse} from "../../../models/RefundResponse";

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnChanges {
  @Input() country: any;
  @Input() state: any;
  @Input() city: any;
  @Input() fromDate: any = null;
  @Input() toDate: any = null;
  @Input() model: any = {};
  errorMessage: string;
  transactions: any = [];
  role: string;
  currentUser: any;
  notloggederrormessage = 'To view this page, you must login first.';
  unautherrormessage = 'You are not authorized to view this page';
  norecordfoundsmessage = 'No records found.';
  isloggedin: boolean;
  index: any = null;
  refundAmount: 0;
  refundResponse: any = {};
  refundMessage = "";
  refundSuccess = false;
  minimumrefundamountmessage = 'You can only refund amount greater than $.01 and  less than the total amount.';

  constructor(private authenticationService: AuthenticationService,
              private transactionservice: TransactionService) {
    this.isloggedin = authenticationService.isLoggedIn();
    if (this.isloggedin) {
      const curuser: any = authenticationService.currentUser();
      if (curuser !== false) {
        this.currentUser = curuser;
        if (this.currentUser != null) {
          this.role = this.currentUser.role;
        } else {
          this.role = 'customer';
        }
      }
    }
  }

  ngOnChanges() {
    const data = {
      country: this.country,
      state: this.state,
      city: this.city,
      fromDate: this.fromDate,
      toDate: this.toDate,
      fname: this.model.fname,
      lname: this.model.lname,
      useremail: this.model.useremail
    };
    this.transactionservice.getTransactions(data)
      .subscribe(transactions => {
          console.log(transactions);
          this.transactions = transactions;
        },
        error => {
          this.errorMessage = <any>error;
          console.log('error:', this.errorMessage);
        }
      );
  }

  setindex(idx) {
    this.index = idx;
    this.resetMessages();
    this.refundResponse.refundAmount = this.transactions[this.index].checkoutOrder.refundAmount;
    this.refundResponse.refundDate = this.transactions[this.index].checkoutOrder.refundDate;
  }

  downloadTransactions() {
    const data = {
      country: this.country,
      state: this.state,
      city: this.city,
      fromDate: this.fromDate,
      toDate: this.toDate,
      fname: this.model.fname,
      lname: this.model.lname,
      useremail: this.model.useremail
    };
    this.transactionservice.downloadTransactions(data)
      .subscribe(transactiondetails => {
          this.downloadFile(transactiondetails);
          console.log(transactiondetails);
        },
        error => {
          this.errorMessage = <any>error;
          console.log('error:', this.errorMessage);
        }
      );
  }

  downloadFile(data) {
    const date = new Date();
    let currenttime = (date.getMonth() + 1) + '-' + date.getDate() + '-' + date.getFullYear();
    currenttime += 'T' + date.getHours() + ':' + date.getMinutes();
    console.log("creating file");
    const filename = 'Transaction_Details_' + currenttime + '.xlsx';
    const blob = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'});
    saveAs(blob, filename);
    console.log('file downloaded');
  }

  refundTransaction(orderId, total) {
    // const numbers = new RegExp(/^[0-9]+$/);

    // let amount = total;
    // console.log('amount:', amount);
    console.log('refundamount:', this.refundAmount);
    // if (this.refundAmount != null) {
    //   amount = this.refundAmount;
    // }

    if(this.refundAmount == null || isNaN(parseFloat(this.refundAmount.toString())) || !isFinite(this.refundAmount)){
      this.refundMessage = "Only numbers are allowed in the refund input box";
      this.refundSuccess = false;
    }else if (this.refundAmount <= 0 || this.refundAmount > total) {
      this.refundMessage = this.minimumrefundamountmessage;
      this.refundSuccess = false;
    } else {
      this.resetMessages();
      const data = {
        'orderId': orderId,
        'refundAmount': this.refundAmount
      };
      console.log('refundamount:', this.refundAmount);
      this.transactionservice.refundTransaction(data).subscribe((result: RefundResponse) => {
          if (result.success) {
            this.refundMessage = 'Refund initiated successfully';
            this.refundSuccess = result.success;
            this.refundResponse = result;
          } else {
            console.log(result.refundMessage);
            this.refundMessage = result.refundMessage;
            this.refundSuccess = false;
          }
        },
        error => {
          console.log('Error while refunding transaction');
          this.refundMessage = 'Error while initiating refund. Please try again later.';
          this.refundSuccess = false;
        });
    }
  }

  resetMessages() {
    this.refundMessage = '';
    this.refundSuccess = false;
  }
}
