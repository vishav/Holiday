import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {RouterModule} from '@angular/router';
import {AppComponent} from './app.component';
import {HselectionComponent} from './components/hselection/hselection.component';
import {routes} from './app.routes';
import {AuthGuard} from './_gaurds/auth.gaurd';

import {HselectionService} from './services/hselection.service';
import {ShoppingcartService} from './services/shoppingcart.service';
import {HolidaylistComponent} from './components/holidaylist/holidaylist.component';
import {LoginComponent} from './components/login/login.component';
import {AuthenticationService} from './services/authentication.service';
import {NavbarComponent} from './components/navbar/navbar.component';
import {RegisterComponent} from './components/register/register.component';
import {CustomValidator} from './equal-validator.directive';
import {ShoppingcartComponent} from './components/shoppingcart/shoppingcart.component';
import {CheckoutComponent} from './components/checkout/checkout.component';
import {OrderService} from './services/order.service';
import {SuccessComponent} from './components/common/success.component';
import {FailureComponent} from './components/common/failure.component';
import {OrderComponent} from './components/order/order.component';
import {LogoutComponent} from './components/common/logout.component';
import {MyDatePickerModule} from 'mydatepicker';
import {InstructionsComponent} from './components/common/instructions.component';
import {PricingComponent} from './components/common/pricing.component';
import {InfoComponent} from './components/common/info.component';
import {UsersService} from './services/users.service';
import {ResetpasswordComponent} from './components/admin/resetpassword/resetpassword.component';
import {ChangepasswordComponent} from './components/admin/changepassword/changepassword.component';
import {ResetrequestComponent} from './components/resetrequest/resetrequest.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {TransactionComponent} from './components/admin/transaction/transaction.component';
import {TransactionService} from './services/transaction.service';
import {PricingService} from "./services/pricing.service";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {NgxBraintreeModule} from "ngx-braintree";
import { MessagesComponent } from './components/messages/messages.component';
import { ContactusComponent } from './components/contactus/contactus.component';
import {ContactusService} from './services/contactus.service';

// Define the routes

@NgModule({
  declarations: [
    AppComponent,
    HselectionComponent,
    HolidaylistComponent,
    LoginComponent,
    NavbarComponent,
    RegisterComponent,
    CustomValidator,
    ShoppingcartComponent,
    CheckoutComponent,
    SuccessComponent,
    FailureComponent,
    OrderComponent,
    LogoutComponent,
    InstructionsComponent,
    PricingComponent,
    InfoComponent,
    ResetpasswordComponent,
    ChangepasswordComponent,
    ResetrequestComponent,
    DashboardComponent,
    TransactionComponent,
    MessagesComponent,
    ContactusComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MyDatePickerModule,
    NgxBraintreeModule,
    RouterModule.forRoot(routes) // Add routes to the app
  ],
  providers: [
    HselectionService,
    AuthenticationService,
    ShoppingcartService,
    AuthGuard,
    UsersService,
    OrderService,
    TransactionService,
    PricingService,
    ContactusService,
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
