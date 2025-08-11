import { Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddCategoryComponent } from './category/add-category/add-category.component';
import { AddProductComponent } from './product/add-product/add-product.component';
import { CATEGORY, COUPONS, DASHBOARD, POST_COUPON, PRODUCT } from '../../../core/constants/api-routes';
import { PostCouponComponent } from './coupon/post-coupon/post-coupon.component';
import { CouponsComponent } from './coupon/coupons/coupons.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    children: [
      { path: '', component: AdminComponent },
      { path: DASHBOARD, component: DashboardComponent },
      { path: CATEGORY, component: AddCategoryComponent },
      { path: PRODUCT, component: AddProductComponent },
      { path: POST_COUPON, component: PostCouponComponent },
      { path: COUPONS, component: CouponsComponent },
    ],
  },
];
