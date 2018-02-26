import { Routes } from '@angular/router';

import { HselectionComponent } from './components/hselection/hselection.component';
import { HolidaylistComponent } from './components/holidaylist/holidaylist.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ShoppingcartComponent } from './components/shoppingcart/shoppingcart.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { AuthGuard } from './_gaurds/auth.gaurd';
import { SuccessComponent } from './components/common/success.component';
import { FailureComponent } from './components/common/failure.component';
import { OrderComponent } from './components/order/order.component';
import { LogoutComponent } from './components/common/logout.component';
import { InstructionsComponent } from './components/common/instructions.component';
import { PricingComponent } from './components/common/pricing.component';
import { InfoComponent } from './components/common/info.component';
import { ResetpasswordComponent } from './components/admin/resetpassword/resetpassword.component';
import { ChangepasswordComponent } from './components/admin/changepassword/changepassword.component';
import { ResetrequestComponent } from './components/resetrequest/resetrequest.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TransactionComponent } from "./components/admin/transaction/transaction.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'hselection',
    pathMatch: 'full'
  },
  {
    path: 'hselection',
    component: HselectionComponent
  },
  {
    path: '',
    component: HselectionComponent
  },
  {
    path: 'holidaylist/:country/:state/:city/:fromyear/:frommonth/:fromday/:toyear/:tomonth/:today',
    component: HolidaylistComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'freeholidaylist/:country/:state/:city/:fromyear/:frommonth/:fromday/:toyear/:tomonth/:today',
    component: HolidaylistComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'shoppingcart',
    component: ShoppingcartComponent
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    canActivate: [AuthGuard]
  }
  ,
  {
    path: 'success',
    component: SuccessComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'failure',
    component: FailureComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'orders',
    component: OrderComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'instructions',
    component: InstructionsComponent
  },
  {
    path: 'pricing',
    component: PricingComponent
  },
  {
    path: 'pwdreset',
    component: ResetpasswordComponent
  },
  {
    path: 'changepassword/:requesteduuid',
    component: ChangepasswordComponent
  },
  {
    path: 'info',
    component: InfoComponent
  },
  {
    path: 'resetrequest',
    component: ResetrequestComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'transaction/:angular2 optio/:state/:city/:fromyear/:frommonth/:fromday/:toyear/:tomonth/:today',
    component: TransactionComponent,
    canActivate: [AuthGuard]
  }

];
