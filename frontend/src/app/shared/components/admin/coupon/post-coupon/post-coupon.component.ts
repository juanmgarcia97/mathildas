import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MATERIAL_IMPORTS } from '../../../../material';
import { AdminService } from '../../../../../core/services/admin/admin.service';
import { MessagesService } from '../../../../../core/services/messages/messages.service';
import { Router } from '@angular/router';
import { API_ROUTES } from '../../../../../core/constants/api-routes';
import { HttpErrorResponse } from '@angular/common/http';
import { CreateCoupon } from '../../../../../core/models/coupon.model';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-post-coupon',
  imports: [CommonModule, ReactiveFormsModule, ...MATERIAL_IMPORTS],
  templateUrl: './post-coupon.component.html',
  styleUrl: './post-coupon.component.scss'
})
export class PostCouponComponent {

  couponForm!: FormGroup;

  formBuilder: FormBuilder = inject(FormBuilder);
  adminService: AdminService = inject(AdminService);
  messagesService: MessagesService = inject(MessagesService);
  router: Router = inject(Router);

  constructor() { }

  ngOnInit() {
    this.couponForm = this.formBuilder.group({
      name: ['', Validators.required],
      code: ['', Validators.required],
      discount: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      expirationDate: ['', Validators.required, this.dateValidator],
    })
  }

  dateValidator(control: AbstractControl): Observable<ValidationErrors | null> {
    const date = new Date(control.value);
    const today = new Date();
    if (date < today) {
      return of({ invalidDate: true });
    }
    return of(null);
  }

  postCoupon() {
    if (this.couponForm.valid) {
      const couponData: CreateCoupon = {
        name: this.couponForm.value.name,
        code: this.couponForm.value.code,
        discount: this.couponForm.value.discount,
        expirationDate: this.couponForm.value.expirationDate,
      }
      this.adminService.createCoupon(couponData).subscribe({
        next: (res) => {
          this.messagesService.addSuccessMessage(res.message);
          this.router.navigate([API_ROUTES.admin.dashboard]);
        },
        error: (err: HttpErrorResponse) => {
          this.messagesService.addErrorMessage(err.error.message);
        }
      });
    } else {
      this.couponForm.markAllAsTouched();
    }
  }
}
