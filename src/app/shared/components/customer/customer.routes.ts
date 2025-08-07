import { Routes } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CART, DASHBOARD } from '../../../core/constants/api-routes';
import { CartComponent } from '../../../features/customer/cart/cart.component';

export const CUSTOMER_ROUTES: Routes = [
  {
    path: '',
    children: [
      { path: '', component: CustomerComponent },
      { path: DASHBOARD, component: DashboardComponent },
      { path: CART, component: CartComponent },
    ],
  },
];
