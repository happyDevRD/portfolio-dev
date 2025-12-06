import { Injectable, signal } from '@angular/core';
import { EN, ES } from './translations.data';

type Language = 'en' | 'es';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private currentLang = signal<Language>('es'); // Default to Spanish as requested? Or check browser? Let's default ES for now as user speaks Spanish

  constructor() {
    // Optional: detect browser language
    const browserLang = navigator.language.split('-')[0];
    if (browserLang === 'en') {
      this.currentLang.set('en');
    }
  }

  get currentLanguage() {
    return this.currentLang();
  }

  toggleLanguage() {
    this.currentLang.update(lang => lang === 'en' ? 'es' : 'en');
  }

  translate(key: string): string {
    const dict = this.currentLang() === 'en' ? EN : ES;
    return (dict as any)[key] || key;
  }
}
