import { Injectable, signal } from '@angular/core';
import { EN, ES } from './translations.data';

type Language = 'en' | 'es';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private currentLanguage: Language = 'es'; // Default to Spanish as requested? Or check browser? Let's default ES for now as user speaks Spanish

  constructor() {
    this.currentLanguage = 'es';
  }

  toggleLanguage() {
    // Disabled as per user request to keep site in Spanish only
    this.currentLanguage = 'es';
  }

  translate(key: string): string {
    const translation = this.currentLanguage === 'en' ? EN[key as keyof typeof EN] : ES[key as keyof typeof ES];
    return translation || key;
  }
}
