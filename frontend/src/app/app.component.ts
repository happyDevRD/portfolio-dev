import { Component, OnInit, HostListener, inject } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './shared/components/footer/footer.component';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  isDarkMode = false;
  isScrolled = false;
  isMobileMenuOpen = false;
  private router = inject(Router);

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.isMobileMenuOpen = false;
      this.isScrolled = false;
      const main = document.querySelector('.main-content') as HTMLElement;
      if (main) main.scrollTop = 0;
    });

    const saved = localStorage.getItem('theme');
    this.isDarkMode = saved ? saved === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
    this.applyTheme();
  }

  @HostListener('window:scroll')
  onWindowScroll() {
    this.isScrolled = window.scrollY > 20;
  }

  /** Llamado desde HomeComponent cuando cambia su scroll interno */
  onInnerScroll(scrollTop: number) {
    this.isScrolled = scrollTop > 20;
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    localStorage.setItem('theme', this.isDarkMode ? 'dark' : 'light');
    this.applyTheme();
  }

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  scrollToTop() {
    const main = document.querySelector('.main-content') as HTMLElement;
    if (main) main.scrollTop = 0;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  private applyTheme() {
    document.body.classList.toggle('dark-mode', this.isDarkMode);
  }
}
