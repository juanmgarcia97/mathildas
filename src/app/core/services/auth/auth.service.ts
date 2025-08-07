import { inject, Injectable } from '@angular/core';
import {
  LoginRequest,
  SignUpRequest,
  UserLoggedIn,
  UserSignedUp,
} from '../../models/user.model';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_ENDPOINTS } from '../../constants/api-endpoints';
import { UserStorageService } from '../storage/user-storage.service';
import { ResponseBody } from '../../models/general.model';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { Router } from '@angular/router';
import { LOGIN } from '../../constants/api-routes';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  httpClient: HttpClient = inject(HttpClient);
  router: Router = inject(Router);

  private idleTimeout: any;
  private readonly IDLE_DURATION = 15 * 60 * 1000; // 15 minutes

  constructor() {}

  register(request: SignUpRequest): Observable<ResponseBody<UserSignedUp>> {
    return this.httpClient.post<ResponseBody<UserSignedUp>>(
      `${API_ENDPOINTS.auth.signUp}`,
      request
    );
  }

  login(request: LoginRequest): Observable<boolean> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient
      .post<ResponseBody<UserLoggedIn>>(
        `${API_ENDPOINTS.auth.login}`,
        request,
        { headers, observe: 'response' }
      )
      .pipe(
        map((response) => {
          const user = response.body?.data;
          if (user) {
            UserStorageService.saveUser(user);
            return true;
          }
          return false;
        })
      );
  }

  refreshToken(): Observable<ResponseBody<UserLoggedIn>> {
    return this.httpClient.post<ResponseBody<UserLoggedIn>>(
      `${API_ENDPOINTS.auth.refreshToken}`,
      null,
      { withCredentials: true }
    );
  }

  logout(): void {
    this.httpClient.post(`${API_ENDPOINTS.auth.logout}`, null).subscribe(() => {
      UserStorageService.logout();
      this.router.navigate([LOGIN]);
    });
  }

  startIdleWatcher() {
    this.resetIdleTimer();

    document.addEventListener('mousemove', this.resetIdleTimer.bind(this));
    document.addEventListener('keydown', this.resetIdleTimer.bind(this));
    document.addEventListener('click', this.resetIdleTimer.bind(this));
  }

  resetIdleTimer() {
    clearTimeout(this.idleTimeout);
    this.idleTimeout = setTimeout(() => {
      this.logout();
    }, this.IDLE_DURATION);
  }
}
