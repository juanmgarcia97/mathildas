import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';
import { MatFormField } from '@angular/material/input';
import { MATERIAL_IMPORTS } from '../../../material';

@Component({
  selector: 'app-product-filter',
  imports: [CommonModule, FormsModule, ...MATERIAL_IMPORTS],
  templateUrl: './product-filter.component.html',
  styleUrl: './product-filter.component.scss'
})
export class ProductFilterComponent {

  searchText: string = '';
  @Output() filter = new EventEmitter<string>();

  constructor() { }
}
