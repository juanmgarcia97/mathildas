import { computed, Injectable, signal } from '@angular/core';
import { LANGUAGES } from '../constants/general-constants';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class I18nService {
  readonly currentLanguage = signal<string>(LANGUAGES.english.code);

  constructor(private translate: TranslateService) {
    const savedLang = localStorage.getItem('lang') || LANGUAGES.english.code;
    this.translate.setDefaultLang(LANGUAGES.english.code);
    this.translate.use(savedLang).subscribe(() => {
      this.currentLanguage.set(savedLang);
    });
  }

  switchLanguage(language: string) {
    this.translate.use(language).subscribe(() => {
      this.currentLanguage.set(language);
      localStorage.setItem('lang', language);
    });
  }

  translateKey = (key: string) =>
    computed<string>(() => this.translate.instant(key));

  isLanguageSet(language: string) {
    return this.currentLanguage() === language;
  }
}
