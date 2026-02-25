import { Injectable, OnDestroy } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class RevealService implements OnDestroy {
  private observer: IntersectionObserver;

  constructor() {
    this.observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            this.observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.12 }
    );
  }

  observe(element: Element): void {
    this.observer.observe(element);
  }

  ngOnDestroy(): void {
    this.observer.disconnect();
  }
}
