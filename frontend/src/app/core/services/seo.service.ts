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
  /** p. ej. `noindex, nofollow` para páginas de error. */
  robots?: string;
}

const BASE_TITLE = 'Eleazar Garcia | Desarrollador Full Stack';

const JSONLD_SCRIPT_ID = 'portfolio-jsonld';

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

    if (config.robots) {
      this.meta.updateTag({ name: 'robots', content: config.robots });
    } else {
      this.meta.removeTag('name=\'robots\'');
    }

    this.setCanonicalHref(canonical);
  }

  /**
   * JSON-LD (schema.org). Pasar `null` para eliminar el bloque (p. ej. al salir de la home).
   */
  setStructuredData(jsonLd: Record<string, unknown> | null): void {
    const head = this.doc.head;
    if (!head) return;
    head.querySelector(`#${JSONLD_SCRIPT_ID}`)?.remove();
    if (!jsonLd) return;
    const script = this.doc.createElement('script');
    script.id = JSONLD_SCRIPT_ID;
    script.type = 'application/ld+json';
    script.textContent = JSON.stringify(jsonLd);
    head.appendChild(script);
  }

  /** WebSite + Person para la página de inicio (datos enriquecidos en buscadores). */
  homePageJsonLd(): Record<string, unknown> {
    const base = environment.siteUrl.replace(/\/$/, '');
    return {
      '@context': 'https://schema.org',
      '@graph': [
        {
          '@type': 'WebSite',
          '@id': `${base}/#website`,
          url: `${base}/`,
          name: 'Eleazar Garcia',
          description:
            'Desarrollador Full Stack (Java, Spring Boot, Angular). Integración, reporting y modernización de sistemas.',
          inLanguage: 'es-DO'
        },
        {
          '@type': 'Person',
          '@id': `${base}/#person`,
          name: 'Eleazar Garcia',
          url: `${base}/`,
          jobTitle: 'Desarrollador Full Stack',
          email: 'hola@elgarcia.org',
          sameAs: [
            'https://github.com/happyDevRD',
            'https://www.linkedin.com/in/garciaeleazar/'
          ]
        }
      ]
    };
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
