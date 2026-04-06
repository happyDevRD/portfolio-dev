import { DOCUMENT } from '@angular/common';
import { Component, OnInit, OnDestroy, AfterViewInit, inject } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, NavigationEnd } from '@angular/router';
import { slideInAnimation } from './core/animations/route.animations';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './shared/components/footer/footer.component';
import { PrefetchRouteDirective } from './shared/directives/prefetch-route.directive';
import { filter } from 'rxjs/operators';
import { Subscription, fromEvent } from 'rxjs';

import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, FooterComponent, PrefetchRouteDirective],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  animations: [slideInAnimation]
})
export class AppComponent implements OnInit, OnDestroy, AfterViewInit {
  isDarkMode = false;
  isScrolled = false;
  isMobileMenuOpen = false;
  private router = inject(Router);
  private document = inject(DOCUMENT);
  private subs = new Subscription();
  private scrollSub?: Subscription;

  ngOnInit() {
    this.subs.add(
      this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
        this.isMobileMenuOpen = false;
        this.isScrolled = false;
        setTimeout(() => {
          const el = this.getScrollContainer();
          if (el) el.scrollTop = 0;
          this.attachScrollListener();
        }, 0);
      })
    );

    const saved = localStorage.getItem('theme');
    this.isDarkMode = saved ? saved === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
    this.applyTheme();

    if (environment.umamiWebsiteId) {
      const s = this.document.createElement('script');
      s.defer = true;
      s.src = 'https://cloud.umami.is/script.js';
      s.setAttribute('data-website-id', environment.umamiWebsiteId);
      this.document.head.appendChild(s);
    }
  }

  ngAfterViewInit() {
    setTimeout(() => this.attachScrollListener(), 0);
  }

  ngOnDestroy() {
    this.subs.unsubscribe();
    this.detachScrollListener();
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    localStorage.setItem('theme', this.isDarkMode ? 'dark' : 'light');
    this.applyTheme();
  }

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  prepareRoute(outlet: RouterOutlet): number | undefined {
    return outlet?.isActivated ? (outlet.activatedRouteData['animation'] as number | undefined) : undefined;
  }

  scrollToTop() {
    const el = this.getScrollContainer();
    if (el) {
      el.scrollTo({ top: 0, behavior: 'smooth' });
    }
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  private attachScrollListener(): void {
    this.detachScrollListener();
    const el = this.getScrollContainer();
    if (!el) {
      this.isScrolled = false;
      return;
    }
    this.isScrolled = el.scrollTop > 20;
    this.scrollSub = fromEvent(el, 'scroll', { passive: true }).subscribe(() => {
      this.isScrolled = el.scrollTop > 20;
    });
  }

  private detachScrollListener(): void {
    this.scrollSub?.unsubscribe();
    this.scrollSub = undefined;
  }

  /** Contenedor que hace scroll real (home, rutas con page-scroll-wrapper, proyectos). */
  private getScrollContainer(): HTMLElement | null {
    const main = document.querySelector('main.main-content');
    if (!main) return null;
    return (
      (main.querySelector('.snap-container') as HTMLElement | null) ||
      (main.querySelector('.page-scroll-wrapper') as HTMLElement | null) ||
      (main.querySelector('.projects-page') as HTMLElement | null)
    );
  }

  private applyTheme() {
    document.body.classList.toggle('dark-mode', this.isDarkMode);
    const meta = document.querySelector('meta[name="theme-color"]');
    if (meta) {
      meta.setAttribute('content', this.isDarkMode ? '#0f172a' : '#f8f6f6');
    }
  }
}
