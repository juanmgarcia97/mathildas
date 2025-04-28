import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Category } from '../../models/category.model';
import { map, Observable } from 'rxjs';
import { ResponseBody } from '../../models/general.model';
import { API_ENDPOINTS } from '../../constants/api-endpoints';
import { Product } from '../../models/product.model';
import { CONSTANTS } from '../../constants/general-constants';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  /* Categories */
  createCategory(categoryDTO: Category): Observable<ResponseBody<Category>> {
    return this.httpClient.post<ResponseBody<Category>>(
      `${API_ENDPOINTS.admin.category.create}`,
      categoryDTO
    );
  }

  updateCategory(categoryDTO: Category): Observable<ResponseBody<Category>> {
    return this.httpClient.put<ResponseBody<Category>>(
      `${API_ENDPOINTS.admin.category.update + categoryDTO.id}`,
      categoryDTO
    );
  }

  deleteCategory(categoryId: number): Observable<ResponseBody<boolean>> {
    return this.httpClient.delete<ResponseBody<boolean>>(
      `${API_ENDPOINTS.admin.category.delete + categoryId}`
    );
  }

  getCategoryById(categoryId: number): Observable<ResponseBody<Category>> {
    return this.httpClient.get<ResponseBody<Category>>(
      `${API_ENDPOINTS.admin.category.find + categoryId}`
    );
  }

  getAllCategories(): Observable<ResponseBody<Category[]>> {
    return this.httpClient.get<ResponseBody<Category[]>>(
      `${API_ENDPOINTS.admin.category.list}`
    );
  }

  /* Products */
  createProduct(productDTO: FormData): Observable<ResponseBody<Product>> {
    return this.httpClient.post<ResponseBody<Product>>(
      `${API_ENDPOINTS.admin.product.create}`,
      productDTO
    );
  }

  updateProduct(productDTO: FormData): Observable<ResponseBody<Product>> {
    return this.httpClient.put<ResponseBody<Product>>(
      `${API_ENDPOINTS.admin.product.update + productDTO.get('id')}`,
      productDTO
    );
  }

  deleteProduct(productId: number): Observable<ResponseBody<boolean>> {
    return this.httpClient.delete<ResponseBody<boolean>>(
      `${API_ENDPOINTS.admin.product.delete + productId}`
    );
  }

  getProductById(productId: number): Observable<ResponseBody<Product>> {
    return this.httpClient
      .get<ResponseBody<Product>>(
        `${API_ENDPOINTS.admin.product.find + productId}`
      )
      .pipe(
        map((res: ResponseBody<Product>) => {
          if (res.data) {
            res.data.imgBytes = CONSTANTS.BASE64_STRING + res.data?.imgBytes;
            return res;
          }
          return res;
        })
      );
  }

  getAllProducts(): Observable<ResponseBody<Product[]>> {
    return this.httpClient
      .get<ResponseBody<Product[]>>(`${API_ENDPOINTS.admin.product.list}`)
      .pipe(
        map((res: ResponseBody<Product[]>) => {
          if (res.data) {
            res.data = res.data.map((product: Product) => {
              product.imgBytes = CONSTANTS.BASE64_STRING + product.imgBytes;
              return product;
            });
          }
          return res;
        })
      );
  }

  getAllProductsByName(name: string): Observable<ResponseBody<Product[]>> {
    return this.httpClient
      .get<ResponseBody<Product[]>>(
        `${API_ENDPOINTS.admin.product.search + name}`
      )
      .pipe(
        map((res: ResponseBody<Product[]>) => {
          if (res.data)
            res.data = res.data.map((product: Product) => {
              product.imgBytes = CONSTANTS.BASE64_STRING + product.imgBytes;
              return product;
            });
          return res;
        })
      );
  }
}
