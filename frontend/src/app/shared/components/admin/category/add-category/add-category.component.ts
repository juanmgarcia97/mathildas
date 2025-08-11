import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MATERIAL_IMPORTS } from '../../../../material';
import { AdminService } from '../../../../../core/services/admin/admin.service';
import { Category } from '../../../../../core/models/category.model';
import { MessagesService } from '../../../../../core/services/messages/messages.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { API_ROUTES } from '../../../../../core/constants/api-routes';

@Component({
  selector: 'app-add-category',
  imports: [ CommonModule, FormsModule, ReactiveFormsModule, ...MATERIAL_IMPORTS],
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.scss',
})
export class AddCategoryComponent {
  categoryForm!: FormGroup;

  router: Router = inject(Router);
  formBuilder: FormBuilder = inject(FormBuilder);
  adminService: AdminService = inject(AdminService);
  messagesService: MessagesService = inject(MessagesService);

  constructor() {}

  ngOnInit() {
    this.categoryForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  addCategory() {
    if (this.categoryForm.valid) {
      const formData: Category = this.categoryForm.value;
      this.adminService.createCategory(formData).subscribe({
        next: (res) => {
          this.messagesService.addSuccessMessage(res.message);
          this.categoryForm.reset();
          this.router.navigateByUrl(API_ROUTES.admin.dashboard)
        },
        error: (err) => {
          this.messagesService.addErrorMessage(err);
        },
      });
    } else {
      this.categoryForm.markAllAsTouched();
      this.messagesService.addWarningMessage(
        'Please fill in all required fields'
      );
    }
  }
}
