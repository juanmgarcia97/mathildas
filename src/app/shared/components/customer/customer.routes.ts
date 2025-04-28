import { Routes } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DASHBOARD } from '../../../core/constants/api-routes';

export const CUSTOMER_ROUTES: Routes = [
  {
    path: '',
    children: [
      { path: '', component: CustomerComponent },
      { path: DASHBOARD, component: DashboardComponent },
    ],
  },
];
