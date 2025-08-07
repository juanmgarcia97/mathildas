import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CartItem, ProductCartItemQuantity } from '../../../../core/models/cart.model';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart-item',
  imports: [MatIconModule, CommonModule],
  templateUrl: './cart-item.component.html',
  styleUrl: './cart-item.component.scss'
})
export class CartItemComponent {

  @Input()
  cartItem!: CartItem;

  @Output()
  modifyItemClicked: EventEmitter<ProductCartItemQuantity> = new EventEmitter<ProductCartItemQuantity>();

  constructor() { }

  increaseCartItemQuantity() {
    const data: ProductCartItemQuantity = {
      userId: this.cartItem.userId,
      productId: this.cartItem.productId,
      quantity: 1,
    };
    this.modifyItemClicked.emit(data);
  }

  decreaseCartItemQuantity() {
    const data: ProductCartItemQuantity = {
      userId: this.cartItem.userId,
      productId: this.cartItem.productId,
      quantity: -1,
    };
    this.modifyItemClicked.emit(data);
  }
}
