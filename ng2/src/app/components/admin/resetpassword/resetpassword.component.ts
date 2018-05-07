import { Component, OnInit } from '@angular/core';
import { UsersService } from "../../../services/users.service";
import {ResetPassword} from "../../../models/ResetPassword";
//const uuidv1 = require('uuid/v1');
//import * as uuidv1 from 'uuid/v1.js';

//import * as nodemailer from 'nodemailer';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {
  private users: any = null;
  private modalmessage: string = "";
  private resetPasswordResponse: ResetPassword = null;

  constructor(private userservice: UsersService) {
  }

  ngOnInit() {

    this.userservice.getUsers().subscribe(users => {
      if (users) {
        console.log(users);
        this.users = users;
      } else {
        console.log("users not found");
      }
    });
  }

  resetpwd(user) {
    console.log(user);
    //console.log(uuidv1());
    console.log(window.location.origin);

    this.userservice.resetPassword(user.email).subscribe((response: ResetPassword) => {
/*      if (response && response.success) {
        console.log(response.message);
        this.resetPasswordResponse = response;
      } else {*/
        console.log(response.message);
        this.resetPasswordResponse = response;
      // }
    });


  }

}
