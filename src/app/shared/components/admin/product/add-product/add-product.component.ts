import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CONSTANTS } from '../../../../../core/constants/general-constants';
import { AdminService } from '../../../../../core/services/admin/admin.service';
import { MessagesService } from '../../../../../core/services/messages/messages.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { MATERIAL_IMPORTS } from '../../../../material';
import { Category } from '../../../../../core/models/category.model';
import { Router } from '@angular/router';
import { API_ROUTES } from '../../../../../core/constants/api-routes';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-product',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    CurrencyPipe,
    ...MATERIAL_IMPORTS
  ],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.scss'
})
export class AddProductComponent {

  productForm!: FormGroup;
  selectedFile!: File | null;
  imagePreview!: string | ArrayBuffer | null;
  categories!: Category[];

  router: Router = inject(Router);
  formBuilder: FormBuilder = inject(FormBuilder);
  adminService: AdminService = inject(AdminService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() { }

  ngOnInit() {
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(CONSTANTS.MIN_PRODUCT_NAME_LENGTH)]],
      description: ['', [Validators.required, Validators.minLength(CONSTANTS.MIN_PRODUCT_DESCRIPTION_LENGTH)]],
      price: [''],
      category: [''],
      img: ['']
    });
    this.loadCategories();
  }

  loadCategories() {
    this.adminService.getAllCategories().subscribe({
      next: (res) => {
        this.categories = res.data as Category[];
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);
        this.messagesService.addErrorMessage(err.message);
      },
    });
  }

  addProduct() {
    if (this.productForm.valid) {
      const formData: FormData = new FormData();
      formData.append('name', this.productForm.get('name')?.value);
      formData.append('description', this.productForm.get('description')?.value);
      formData.append('price', this.productForm.get('price')?.value);
      formData.append('categoryId', this.productForm.get('category')?.value);
      formData.append('file', this.selectedFile as Blob);
      console.log(formData.values());
      this.adminService.createProduct(formData).subscribe({
        next: (res) => {
          this.messagesService.addSuccessMessage(res.message);
          this.productForm.reset();
          this.router.navigateByUrl(API_ROUTES.admin.dashboard)
        },
        error: (err: HttpErrorResponse) => {
          this.messagesService.addErrorMessage(err.message);
        },
      });
    } else {
      this.productForm.markAllAsTouched();
      this.messagesService.addWarningMessage('Please fill in all required fields');
    }
  }

  onFileSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
      this.previewImage(this.selectedFile);
    }
  }
  
  previewImage(file: File) {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(file);
  }

  get price() {
    return this.productForm.get('price');
  }

}
