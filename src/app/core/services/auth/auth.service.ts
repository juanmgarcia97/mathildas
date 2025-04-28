import { inject, Injectable } from '@angular/core';
import { LoginRequest, SignUpRequest, UserLoggedIn } from '../../models/user.model';
import { map, Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_ENDPOINTS } from '../../constants/api-endpoints';
import { UserStorageService } from '../storage/user-storage.service';
import { ResponseBody } from '../../models/general.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  httpClient: HttpClient = inject(HttpClient);

  constructor() { }

  register(request: SignUpRequest): Observable<any> {
    return this.httpClient.post(`${API_ENDPOINTS.auth.signUp}`, request);
  }

  login(request: LoginRequest): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<ResponseBody<UserLoggedIn>>(`${API_ENDPOINTS.auth.login}`, request, { headers, observe: 'response' }).pipe(
      map((response) => {
        const token = response.headers.get('Authorization')?.substring(7);
        const user = response.body?.data;
        if (token && user) {
          UserStorageService.saveToken(token);
          UserStorageService.saveUser(user);
          return true;
        }
        return false;
      })
    );
  }

  logout(): Observable<any> {
    return this.httpClient.post(`${API_ENDPOINTS.auth.logout}`, null);
  }
  
}
