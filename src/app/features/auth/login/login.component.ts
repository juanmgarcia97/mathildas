import { Component, inject, signal } from '@angular/core';
import { MATERIAL_IMPORTS } from '../../../shared/material';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth/auth.service';
import { LoginRequest } from '../../../core/models/user.model';
import { MessagesService } from '../../../core/services/messages/messages.service';
import { UserStorageService } from '../../../core/services/storage/user-storage.service';
import { API_ROUTES } from '../../../core/constants/api-routes';
import { CONSTANTS } from '../../../core/constants/general-constants';
import { I18nService } from '../../../core/i18n/i18n.service';

@Component({
  selector: 'app-login',
  imports: [...MATERIAL_IMPORTS, ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginForm: FormGroup;

  showPassword = signal(false);
  isLoading = signal(false);

  formBuilder: FormBuilder = inject(FormBuilder);
  router: Router = inject(Router);
  authService: AuthService = inject(AuthService);
  messagesService: MessagesService = inject(MessagesService);
  i18nService: I18nService = inject(I18nService);

  loginLable = this.i18nService.translateKey('navigation.login');
  emailLabel = this.i18nService.translateKey('auth.login.email');
  passwordLabel = this.i18nService.translateKey('auth.login.password');

  emailRequiredError = this.i18nService.translateKey('errors.auth.login.email.required');
  emailInvalidError = this.i18nService.translateKey('errors.auth.login.email.invalid');
  passwordRequiredError = this.i18nService.translateKey('errors.auth.login.password.required');

  constructor() {
    this.loginForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(CONSTANTS.MIN_PASSWORD_LENGTH)]],
        rememberMe: [false],
      },
      {
        updateOn: 'blur',
      }
    );
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const formData = this.loginForm.value;
      const userData: LoginRequest = {
        username: formData.email,
        password: formData.password,
      };
      this.authService.login(userData).subscribe({
        next: (response) => {
          console.info('Login successful', response);
          if (UserStorageService.isAdminLoggedIn()) {
            this.router.navigateByUrl(API_ROUTES.admin.dashboard);
          } else if (UserStorageService.isCustomerLoggedIn()) {
            this.router.navigateByUrl(API_ROUTES.customer.dashboard);
          }
        },
        error: (error) => {
          this.messagesService.addErrorMessage('Login failed', error);
        },
      });
    }
  }

  togglePasswordVisibility() {
    this.showPassword.set(!this.showPassword());
  }
}
