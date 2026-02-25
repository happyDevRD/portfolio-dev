import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

export interface SeoConfig {
  title: string;
  description: string;
  keywords?: string;
  url?: string;
  type?: string;
}

const BASE_TITLE = 'Eleazar Garcia | Desarrollador Full Stack';
const BASE_URL = 'https://portfolio-dev-1-592i.onrender.com';

@Injectable({ providedIn: 'root' })
export class SeoService {
  constructor(private meta: Meta, private title: Title) {}

  update(config: SeoConfig): void {
    const fullTitle = config.title === BASE_TITLE
      ? BASE_TITLE
      : `${config.title} | Eleazar Garcia`;
    const url = config.url ? `${BASE_URL}${config.url}` : BASE_URL;
    const type = config.type ?? 'website';

    this.title.setTitle(fullTitle);

    this.meta.updateTag({ name: 'description', content: config.description });
    if (config.keywords) {
      this.meta.updateTag({ name: 'keywords', content: config.keywords });
    }

    this.meta.updateTag({ property: 'og:title', content: fullTitle });
    this.meta.updateTag({ property: 'og:description', content: config.description });
    this.meta.updateTag({ property: 'og:url', content: url });
    this.meta.updateTag({ property: 'og:type', content: type });

    this.meta.updateTag({ name: 'twitter:title', content: fullTitle });
    this.meta.updateTag({ name: 'twitter:description', content: config.description });
  }
}
