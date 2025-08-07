import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, switchMap, throwError } from 'rxjs';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { API_ROUTES } from '../constants/api-routes';
import { AuthService } from '../services/auth/auth.service';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const cloned = req.clone({ withCredentials: true });

  return next(cloned).pipe(
    catchError((err) => {
      if (err.status === 401) {
        return authService.refreshToken().pipe(
          switchMap(() => next(cloned)),
          catchError((refreshErr) => {
            if (refreshErr.status === 401) {
              router.navigate([API_ROUTES.visitor.login]);
            }
            return throwError(() => refreshErr);
          })
        );
      }
      return throwError(() => err);
    })
  );
};
