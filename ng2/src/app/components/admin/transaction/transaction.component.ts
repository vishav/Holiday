import {Component, Input, OnChanges} from '@angular/core';
import {TransactionService} from '../../../services/transaction.service';
import {AuthenticationService} from '../../../services/authentication.service';
import { saveAs } from 'file-saver';
import {TransactionResponse} from "../../../models/TransactionResponse";

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
  refundMessage = "";
  refundError = false;
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

  refundTransaction(paymentid, total) {
    let amount = total;
    console.log('amount:', amount);
    console.log('refundamount:', this.refundAmount);
    if (this.refundAmount != null && this.refundAmount !== 0) {
      amount = this.refundAmount;
    }

    if (amount < 0 || amount > total) {
      this.refundMessage = this.minimumrefundamountmessage;
      this.refundError = true;
    } else {
      this.resetMessages();
      const data = {
        'paymentid': paymentid,
        'refundAmount': amount
      };
      console.log('refundamount:', amount);
      this.transactionservice.refundTransaction(data).subscribe((result: TransactionResponse) => {
          if (result.success) {
            this.refundMessage = 'Refund initiated successfully';
            this.refundSuccess = true;
          } else {
            console.log(result.message);
            this.refundMessage = result.message;
            this.refundError = true;
          }
        },
        error => {
          console.log('Error while refunding transaction');
          this.refundMessage = 'Error while initiating refund. Please try again later.';
          this.refundError = true;
        });
    }
  }

  resetMessages() {
    this.refundMessage = '';
    this.refundError = false;
    this.refundSuccess = false;
  }
}
