import { Component, CUSTOM_ELEMENTS_SCHEMA, inject } from '@angular/core';
import { Product } from '../../../../core/models/product.model';
import { MessagesService } from '../../../../core/services/messages/messages.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProductCardComponent } from '../../shared/product-card/product-card.component';
import { ProductFilterComponent } from '../../shared/product-filter/product-filter.component';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../../../../core/services/customer/customer.service';
import { ProductCartItem } from '../../../../core/models/cart.model';
import { UserStorageService } from '../../../../core/services/storage/user-storage.service';

@Component({
  selector: 'app-customer-dashboard',
  imports: [ProductCardComponent, ProductFilterComponent, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DashboardComponent {
  products!: Product[];
  displayProducts!: Product[];

  customerService: CustomerService = inject(CustomerService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.customerService.getAllProducts().subscribe({
      next: (res) => {
        this.products = res.data as Product[];
        this.displayProducts = this.products;
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    });
  }

  filterProducts(search: any) {
    this.displayProducts = this.products.filter((product) => {
      return product.name.toLowerCase().includes(search.toLowerCase());
    });
  }

  addToCart(product: Product) {
    const ProductCartItem: ProductCartItem = {
      productId: product.id,
      userId: UserStorageService.getUserId(),
    };
    this.customerService.addToCart(ProductCartItem).subscribe({
      next: (res) => {
        this.messagesService.addSuccessMessage(res.message);
      },
      error: (err: HttpErrorResponse) => {
        this.messagesService.addErrorMessage(err.error.message);
      },
    });
  }
}
