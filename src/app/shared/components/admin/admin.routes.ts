import { Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddCategoryComponent } from './category/add-category/add-category.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { CATEGORY, DASHBOARD, PRODUCT } from '../../../core/constants/api-routes';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    children: [
      { path: '', component: AdminComponent },
      { path: DASHBOARD, component: DashboardComponent },
      { path: CATEGORY, component: AddCategoryComponent },
      { path: PRODUCT, component: AddProductComponent },
    ],
  },
];
