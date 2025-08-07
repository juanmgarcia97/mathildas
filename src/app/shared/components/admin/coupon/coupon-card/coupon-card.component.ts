import { Component, Input } from '@angular/core';
import { Coupon } from '../../../../../core/models/coupon.model';
import { CommonModule } from '@angular/common';
import { MATERIAL_IMPORTS } from '../../../../material';

@Component({
  selector: 'app-coupon-card',
  imports: [CommonModule, ...MATERIAL_IMPORTS],
  templateUrl: './coupon-card.component.html',
  styleUrl: './coupon-card.component.scss'
})
export class CouponCardComponent {

  @Input()
  coupon!: Coupon;

  constructor() { }

  isCouponExpired(): boolean {
    const currentDate = new Date();
    const expirationDate = new Date(this.coupon.expirationDate);
    return expirationDate < currentDate;
  }
}
