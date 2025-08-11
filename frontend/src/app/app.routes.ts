import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { SignUpComponent } from './features/auth/sign-up/sign-up.component';
import { UserStorageService } from './core/services/storage/user-storage.service';
import { ADMIN_ROUTES } from './shared/components/admin/admin.routes';
import { CUSTOMER_ROUTES } from './shared/components/customer/customer.routes';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent },
  {
    path: 'admin',
    loadChildren: () =>
      import('./shared/components/admin/admin.routes').then(
        (m) => m.ADMIN_ROUTES
      ),
  },
  {
    path: 'customer',
    loadChildren: () =>
      import('./shared/components/customer/customer.routes').then(
        (m) => m.CUSTOMER_ROUTES
      ),
  },
];
