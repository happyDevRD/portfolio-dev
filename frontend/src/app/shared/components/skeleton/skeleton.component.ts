import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-skeleton',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="skeleton-wrap" [ngStyle]="{'width': width, 'height': height, 'border-radius': radius}"></div>
  `,
  styles: [`
    .skeleton-wrap {
      background: linear-gradient(
        90deg,
        var(--surface-light) 25%,
        var(--border-color) 37%,
        var(--surface-light) 63%
      );
      background-size: 400% 100%;
      animation: skeleton-loading 1.4s ease infinite;
      display: block;
    }
    @keyframes skeleton-loading {
      0%   { background-position: 100% 50%; }
      100% { background-position: 0% 50%; }
    }
  `]
})
export class SkeletonComponent {
  @Input() width = '100%';
  @Input() height = '1rem';
  @Input() radius = '6px';
}
