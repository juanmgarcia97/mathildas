import {
  ADMIN_ROUTE,
  AUTH_ROUTE,
  CATEGORY,
  CUSTOMER_ROUTE,
  LOGIN,
  LOGOUT,
  PRODUCT,
  SEARCH,
  SIGN_UP,
  SLASH,
} from './api-routes';

export const API_BASE_URL = 'http://localhost:8080/api/v1';
export const API_ENDPOINTS = {
  auth: {
    path: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH}`,
    login: `${API_BASE_URL + SLASH + AUTH_ROUTE + SLASH + LOGIN}`,
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
  },
  customer: {
    path: `${API_BASE_URL + SLASH + CUSTOMER_ROUTE + SLASH}`,
  },
};
