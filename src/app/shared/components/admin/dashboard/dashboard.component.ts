import { Component, CUSTOM_ELEMENTS_SCHEMA, inject } from '@angular/core';
import { Product } from '../../../../core/models/product.model';
import { AdminService } from '../../../../core/services/admin/admin.service';
import { ProductCardComponent } from '../product/product-card/product-card.component';
import { CommonModule } from '@angular/common';
import { MessagesService } from '../../../../core/services/messages/messages.service';
import { ProductFilterComponent } from '../product/product-filter/product-filter.component';

@Component({
  selector: 'app-admin-dashboard',
  imports: [ProductCardComponent, ProductFilterComponent, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DashboardComponent {
  products!: Product[];
  displayProducts!: Product[];

  adminService: AdminService = inject(AdminService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.adminService.getAllProducts().subscribe({
      next: (res) => {
        this.products = res.data as Product[];
        this.displayProducts = this.products;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  deleteProduct(product: Product) {
    this.adminService.deleteProduct(product.id).subscribe({
      next: (res) => {
        if (res.data) {
          const index = this.products.findIndex((p) => p.id === product.id);
          if (index !== -1) {
            this.products.splice(index, 1);
            this.displayProducts = this.products;
            this.messagesService.addSuccessMessage(res.message);
          }
        }
      },
      error: (err) => {
        this.messagesService.addErrorMessage(err.message);
      },
    });
  }

  filterProducts(search: any) {
    this.displayProducts = this.products.filter((product) => {
      return product.name.toLowerCase().includes(search.toLowerCase());
    });
  }
}
