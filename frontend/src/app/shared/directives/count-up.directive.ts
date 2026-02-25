import { Directive, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appCountUp]',
  standalone: true
})
export class CountUpDirective implements OnInit {
  @Input('appCountUp') target = 0;
  @Input() duration = 1800;

  constructor(private el: ElementRef) {}

  ngOnInit(): void {
    const observer = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting) {
        this.animate();
        observer.disconnect();
      }
    }, { threshold: 0.5 });
    observer.observe(this.el.nativeElement);
  }

  private animate(): void {
    const start = performance.now();
    const el = this.el.nativeElement;
    const suffix = el.textContent?.replace(/[0-9]/g, '') ?? '';

    const step = (now: number) => {
      const elapsed = now - start;
      const progress = Math.min(elapsed / this.duration, 1);
      const eased = 1 - Math.pow(1 - progress, 3);
      el.textContent = Math.floor(eased * this.target) + suffix;
      if (progress < 1) requestAnimationFrame(step);
    };
    requestAnimationFrame(step);
  }
}
