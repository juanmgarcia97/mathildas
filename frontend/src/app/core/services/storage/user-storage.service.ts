import { Injectable } from '@angular/core';
import { UserLoggedIn, UserRole } from '../../models/user.model';

const TOKEN = 'auth-token';
const USER = 'auth-user';


@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  static saveUser(user: any): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getUser(): UserLoggedIn {
    const user = window.localStorage.getItem(USER);
    if (user) {
      return JSON.parse(user);
    }
    return {
      id: 0,
      role: UserRole.CUSTOMER
    };
  }

  static getUserId(): number {
    const user: UserLoggedIn = this.getUser();
    if (user) {
      return user.id;
    }
    return 0;
  }

  static getUserRole(): string {
    const user: UserLoggedIn = this.getUser();
    if (user) {
      return user.role;
    }
    return '';
  }

  static isAdminLoggedIn(): boolean {
    const role = this.getUserRole();
    return role === UserRole.ADMIN;
  }
  
  static isCustomerLoggedIn(): boolean {
    const role = this.getUserRole();
    return role === UserRole.CUSTOMER;
  }

  static logout(): void {
    window.localStorage.clear();
  }
}
