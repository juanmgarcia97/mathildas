import { inject, Injectable } from '@angular/core';
import { API_ENDPOINTS } from '../../constants/api-endpoints';
import { map, Observable } from 'rxjs';
import { ResponseBody } from '../../models/general.model';
import { Product } from '../../models/product.model';
import { CONSTANTS } from '../../constants/general-constants';
import { HttpClient } from '@angular/common/http';
import {
  CartItem,
  Order,
  PlaceOrder,
  ProductCartItem,
  ProductCartItemQuantity,
} from '../../models/cart.model';
import { UserStorageService } from '../storage/user-storage.service';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  getAllProducts(): Observable<ResponseBody<Product[]>> {
    return this.httpClient
      .get<ResponseBody<Product[]>>(`${API_ENDPOINTS.customer.product.list}`)
      .pipe(
        map((res: ResponseBody<Product[]>) => {
          if (res.data && res.data !== null) {
            res.data = res.data.map((product: Product) => {
              product.imgBytes = CONSTANTS.BASE64_STRING + product.imgBytes;
              return product;
            });
          }
          return res;
        })
      );
  }

  addToCart(
    ProductCartItem: ProductCartItem
  ): Observable<ResponseBody<unknown>> {
    return this.httpClient.post<ResponseBody<unknown>>(
      `${API_ENDPOINTS.customer.cart.add}`,
      ProductCartItem
    );
  }

  getCartByUserId(): Observable<ResponseBody<Order>> {
    const userId = UserStorageService.getUserId();
    return this.httpClient
      .get<ResponseBody<Order>>(`${API_ENDPOINTS.customer.cart.get + userId}`)
      .pipe(
        map((res: ResponseBody<Order>) => {
          if (res.data && res.data !== null) {
            res.data.cartItems = res.data.cartItems.map((item: CartItem) => {
              item.productImage = CONSTANTS.BASE64_STRING + item.productImage;
              return item;
            });
          }
          return res;
        })
      );
  }

  applyCoupon(code: string): Observable<ResponseBody<Order>> {
    const userId = UserStorageService.getUserId();
    return this.httpClient
      .post<ResponseBody<Order>>(
        `${API_ENDPOINTS.customer.cart.applyCoupon + userId}/${code}`,
        null
      )
      .pipe(
        map((res: ResponseBody<Order>) => {
          if (res.data && res.data !== null) {
            res.data.cartItems = res.data.cartItems.map((item: CartItem) => {
              item.productImage = CONSTANTS.BASE64_STRING + item.productImage;
              return item;
            });
          }
          return res;
        })
      );
  }

  modifyItemQuantity(
    itemQuantity: ProductCartItemQuantity
  ): Observable<ResponseBody<Order>> {
    const userId = UserStorageService.getUserId();
    return this.httpClient
      .post<ResponseBody<Order>>(
        `${API_ENDPOINTS.customer.cart.itemQuantity}`,
        itemQuantity
      )
      .pipe(
        map((res: ResponseBody<Order>) => {
          if (res.data && res.data !== null) {
            res.data.cartItems = res.data.cartItems.map((item: CartItem) => {
              item.productImage = CONSTANTS.BASE64_STRING + item.productImage;
              return item;
            });
          }
          return res;
        })
      );
  }

  placeOrder(placeOrder: PlaceOrder): Observable<ResponseBody<Order>> {
    return this.httpClient
      .post<ResponseBody<Order>>(
        `${API_ENDPOINTS.customer.cart.placeOrder}`,
        placeOrder
      )
      .pipe(
        map((res: ResponseBody<Order>) => {
          if (res.data && res.data !== null) {
            res.data.cartItems = res.data.cartItems.map((item: CartItem) => {
              item.productImage = CONSTANTS.BASE64_STRING + item.productImage;
              return item;
            });
          }
          return res;
        })
      );
  }
}
