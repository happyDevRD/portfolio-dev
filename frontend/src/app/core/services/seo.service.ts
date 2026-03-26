import { DOCUMENT } from '@angular/common';
import { Injectable, inject } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

import { environment } from '../../../environments/environment';

export interface SeoConfig {
  title: string;
  description: string;
  keywords?: string;
  /** Ruta bajo el sitio, p. ej. `/blog/mi-post` */
  url?: string;
  type?: string;
  /** Imagen para Open Graph / Twitter (URL absoluta o ruta relativa al sitio). */
  imageUrl?: string;
}

const BASE_TITLE = 'Eleazar Garcia | Desarrollador Full Stack';

@Injectable({ providedIn: 'root' })
export class SeoService {
  private readonly doc = inject(DOCUMENT);
  private readonly meta = inject(Meta);
  private readonly title = inject(Title);

  update(config: SeoConfig): void {
    const base = environment.siteUrl.replace(/\/$/, '');
    const path = config.url ?? '/';
    const canonicalPath = path.startsWith('/') ? path : `/${path}`;
    const canonical = `${base}${canonicalPath === '//' ? '/' : canonicalPath}`;

    const fullTitle = config.title === BASE_TITLE
      ? BASE_TITLE
      : `${config.title} | Eleazar Garcia`;
    const type = config.type ?? 'website';

    const ogImage = this.absoluteImageUrl(config.imageUrl)
      ?? `${base}${environment.defaultOgImagePath}`;

    this.title.setTitle(fullTitle);

    this.meta.updateTag({ name: 'description', content: config.description });
    if (config.keywords) {
      this.meta.updateTag({ name: 'keywords', content: config.keywords });
    }

    this.meta.updateTag({ property: 'og:title', content: fullTitle });
    this.meta.updateTag({ property: 'og:description', content: config.description });
    this.meta.updateTag({ property: 'og:url', content: canonical });
    this.meta.updateTag({ property: 'og:type', content: type });
    this.meta.updateTag({ property: 'og:image', content: ogImage });
    this.meta.updateTag({ property: 'og:locale', content: 'es_DO' });

    this.meta.updateTag({ name: 'twitter:card', content: 'summary_large_image' });
    this.meta.updateTag({ name: 'twitter:title', content: fullTitle });
    this.meta.updateTag({ name: 'twitter:description', content: config.description });
    this.meta.updateTag({ name: 'twitter:image', content: ogImage });

    this.setCanonicalHref(canonical);
  }

  /** URL absoluta para una imagen del sitio o remota (p. ej. portada de artículo). */
  absoluteImageUrl(url?: string | null): string | undefined {
    if (!url?.trim()) return undefined;
    const t = url.trim();
    if (t.startsWith('http://') || t.startsWith('https://')) return t;
    const base = environment.siteUrl.replace(/\/$/, '');
    return t.startsWith('/') ? `${base}${t}` : `${base}/${t}`;
  }

  private setCanonicalHref(href: string): void {
    const head = this.doc.head;
    if (!head) return;
    let link = head.querySelector('link[rel="canonical"]') as HTMLLinkElement | null;
    if (!link) {
      link = this.doc.createElement('link');
      link.setAttribute('rel', 'canonical');
      head.appendChild(link);
    }
    link.setAttribute('href', href);
  }
}
