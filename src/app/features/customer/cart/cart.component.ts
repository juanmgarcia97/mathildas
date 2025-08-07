import { Component, inject } from '@angular/core';
import { CartItem, Order, ProductCartItemQuantity } from '../../../core/models/cart.model';
import { CustomerService } from '../../../core/services/customer/customer.service';
import { MessagesService } from '../../../core/services/messages/messages.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { CartItemComponent } from '../../../shared/components/shared/cart-item/cart-item.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cart',
  imports: [MatIconModule, CommonModule, CartItemComponent, FormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {
  cartItems: CartItem[] = [];
  order!: Order;
  couponCode!: string;

  customerService: CustomerService = inject(CustomerService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() { }

  ngOnInit() {
    this.getCart();
  }

  getCart() {
    this.customerService.getCartByUserId().subscribe({
      next: (res) => {
        this.order = res.data as Order;
        this.cartItems = this.order.cartItems;
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    });
  }

  applyCoupon() {
    this.customerService.applyCoupon(this.couponCode).subscribe({
      next: (res) => {
        this.messagesService.addSuccessMessage(res.message);
        this.order = res.data as Order;
        this.cartItems = this.order.cartItems;
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    });
  }

  modifyItemQuantity(data: ProductCartItemQuantity) {
    this.customerService.modifyItemQuantity(data).subscribe({
      next: (res) => {
        this.messagesService.addSuccessMessage(res.message);
        this.order = res.data as Order;
        this.cartItems = this.order.cartItems;
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    })
  }

  placeOrder() {
  }

  clearCart() {

  }
}
