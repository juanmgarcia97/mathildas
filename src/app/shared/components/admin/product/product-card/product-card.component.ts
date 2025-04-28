import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MATERIAL_IMPORTS } from '../../../../material';
import { Product } from '../../../../../core/models/product.model';
import { RouterModule } from '@angular/router';
import { API_ROUTES } from '../../../../../core/constants/api-routes';
import { I18nService } from '../../../../../core/i18n/i18n.service';

@Component({
  selector: 'app-product-card',
  imports: [CommonModule, RouterModule, ...MATERIAL_IMPORTS],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss',
})
export class ProductCardComponent {
  @Input()
  product!: Product;

  @Output()
  deleteClicked: EventEmitter<Product> = new EventEmitter<Product>();

  i18nService: I18nService = inject(I18nService);

  routes = API_ROUTES;

  constructor() {}

  deleteProduct() {
    this.deleteClicked.emit(this.product);
  }
}
