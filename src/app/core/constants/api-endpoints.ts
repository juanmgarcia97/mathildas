import {
  ADMIN_ROUTE,
  AUTH_ROUTE,
  CART,
  CATEGORY,
  COUPONS,
  CUSTOMER_ROUTE,
  LOGIN,
  LOGOUT,
  PRODUCT,
  SEARCH,
  SIGN_UP,
  SLASH,
} from './api-routes';
const APPLY_COUPON = 'apply-coupon';
const ITEM_QUANTITY = 'item-quantity';
const PLACE_ORDER = 'place-order';
const REFRESH = 'refresh-token';
export const API_BASE_URL = 'http://localhost:8080/api/v1';
export const API_ENDPOINTS = {
  auth: {
    path: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH}`,
    login: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH + LOGIN}`,
    refreshToken: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH + REFRESH}`,
    logout: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH + LOGOUT}`,
    signUp: `${API_BASE_URL + SLASH + AUTH_ROUTE + SIGN_UP}`,
  },
  admin: {
    path: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH}`,
    category: {
      list: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + CATEGORY}`,
      create: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + CATEGORY}`,
      update: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + CATEGORY + SLASH}`,
      find: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + CATEGORY + SLASH}`,
      delete: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + CATEGORY + SLASH}`,
    },
    product: {
      list: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT}`,
      create: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT}`,
      update: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT + SLASH}`,
      find: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT + SLASH}`,
      delete: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT + SLASH}`,
      search: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + PRODUCT + SLASH + SEARCH + SLASH}`,
    },
    coupon: {
      list: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + COUPONS}`,
      create: `${API_BASE_URL + SLASH + ADMIN_ROUTE + SLASH + COUPONS}`,
    }
  },
  customer: {
    path: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH}`,
    product: {
      list: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + PRODUCT}`,
    },
    cart: {
      add: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + CART}`,
      get: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + CART + SLASH}`,
      applyCoupon: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + CART + SLASH + APPLY_COUPON + SLASH}`,
      itemQuantity: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + CART + SLASH + ITEM_QUANTITY}`,
      placeOrder: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH + CART + SLASH + PLACE_ORDER}`,
    }
  },
};
