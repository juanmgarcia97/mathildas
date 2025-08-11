import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { CouponCardComponent } from '../coupon-card/coupon-card.component';
import { Coupon } from '../../../../../core/models/coupon.model';
import { AdminService } from '../../../../../core/services/admin/admin.service';
import { MessagesService } from '../../../../../core/services/messages/messages.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-coupons',
  imports: [CommonModule, CouponCardComponent],
  templateUrl: './coupons.component.html',
  styleUrl: './coupons.component.scss'
})
export class CouponsComponent {

  coupons: Coupon[] = [];

  adminService: AdminService = inject(AdminService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() { }

  ngOnInit() {
    this.getCoupons();
  }

  getCoupons() {
    this.adminService.getAllCoupons().subscribe({
      next: (res) => {
        this.coupons = res.data as Coupon[];
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    })
  }
}
