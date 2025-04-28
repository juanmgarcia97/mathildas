import { HttpInterceptorFn } from '@angular/common/http';
import { UserStorageService } from '../services/storage/user-storage.service';
import { API_ENDPOINTS } from '../constants/api-endpoints';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const token = UserStorageService.getToken();
  const authEndpoints = [API_ENDPOINTS.admin.path, API_ENDPOINTS.customer.path];
  const isUrlAuthRequired = authEndpoints.some(path => req.url.includes(path));
  
  if(token !== null && isUrlAuthRequired) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(authReq);
  }
  return next(req);
};
