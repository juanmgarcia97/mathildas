import { Component, inject, signal } from '@angular/core';
import { MATERIAL_IMPORTS } from '../../../shared/material';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { SignUpRequest } from '../../../core/models/user.model';
import { AuthService } from '../../../core/services/auth/auth.service';
import { LOGIN } from '../../../core/constants/api-routes';
import { CONSTANTS } from '../../../core/constants/general-constants';

@Component({
  selector: 'app-sign-up',
  imports: [
    ...MATERIAL_IMPORTS, 
    ReactiveFormsModule, 
    CommonModule
  ],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.scss'
})
export class SignUpComponent {
  
  signUpForm!: FormGroup;
  showPassword = signal(false);
  showConfirmPassword = signal(false);

  formBuilder: FormBuilder = inject(FormBuilder);
  router: Router = inject(Router);
  authService: AuthService = inject(AuthService);

  constructor() {
    this.signUpForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(CONSTANTS.MIN_NAME_LENGTH), Validators.maxLength(CONSTANTS.MAX_NAME_LENGTH)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(CONSTANTS.MIN_PASSWORD_LENGTH), Validators.maxLength(CONSTANTS.MAX_PASSWORD_LENGTH)]],
      confirmPassword: ['', [Validators.required]],
      termsAccepted: [false, Validators.requiredTrue]
    }, {
      updateOn: 'blur',
      // Custom validator to check if password and confirm password match
      validators: this.passwordMatchValidator
    });
  }
  
  passwordMatchValidator(form: FormGroup) {
    return form.get('password')?.value === form.get('confirmPassword')?.value ? null : { mismatch: true };
  }
  
  onSubmit() {
    if (this.signUpForm.valid) {
      const formData = this.signUpForm.value;
      const userData: SignUpRequest = {
        name: formData.name,
        email: formData.email,
        password: formData.password,
      }
      this.authService.register(userData).subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          // Navigate to the login page or show a success message
          this.router.navigate([LOGIN]);
        },
        error: (error) => {
          console.error('Registration failed', error);
          // Handle error response
          // Show an error message to the user
        }
      });
    } else {
      console.log('Form is invalid');
    }
  }

  togglePasswordVisibility() {
    this.showPassword.set(!this.showPassword());
  }
  
  toggleConfirmPasswordVisibility() {
    this.showConfirmPassword.set(!this.showConfirmPassword());
  }
  
  get minPasswordLength() {
    return this.signUpForm.get('password')?.errors?.['minlength']?.requiredLength || CONSTANTS.MIN_PASSWORD_LENGTH;
  }

  get minNameLength() {
    return this.signUpForm.get('name')?.errors?.['minlength']?.requiredLength || CONSTANTS.MIN_NAME_LENGTH;
  }

  get maxNameLength() {
    return this.signUpForm.get('name')?.errors?.['maxlength']?.requiredLength || CONSTANTS.MAX_NAME_LENGTH;
  }

  get maxPasswordLength() {
    return this.signUpForm.get('password')?.errors?.['maxlength']?.requiredLength || CONSTANTS.MAX_PASSWORD_LENGTH;
  }
  
}
