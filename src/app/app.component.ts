import { Component, signal } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { MATERIAL_IMPORTS } from './shared/material';
import { UserStorageService } from './core/services/storage/user-storage.service';
import { CommonModule } from '@angular/common';
import { API_ROUTES } from './core/constants/api-routes';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { LANGUAGES } from './core/constants/general-constants';
import { I18nService } from './core/i18n/i18n.service';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    RouterModule,
    CommonModule,
    TranslateModule,
    ...MATERIAL_IMPORTS
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'mathildas-front';

  isAdminLoggedIn = signal(false);
  isCustomerLoggedIn = signal(false);
  LANGUAGES = LANGUAGES;

  apiRoutes = API_ROUTES

  constructor(private router: Router, private i18nService: I18nService) { }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      this.isAdminLoggedIn.set(UserStorageService.isAdminLoggedIn());
      this.isCustomerLoggedIn.set(UserStorageService.isCustomerLoggedIn());
    })
  }

  logout() {
    UserStorageService.signOut();
    this.router.navigate(['/login']);
  }

  isLanguageSet = (language: string) => this.i18nService.isLanguageSet(language);
  switchLanguage = (language: string) => this.i18nService.switchLanguage(language);
}
