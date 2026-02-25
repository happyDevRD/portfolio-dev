import { Component } from '@angular/core';
import { SkeletonComponent } from './skeleton.component';

@Component({
  selector: 'app-blog-card-skeleton',
  standalone: true,
  imports: [SkeletonComponent],
  template: `
    <div class="skeleton-blog-card">
      <div class="skeleton-tags">
        <app-skeleton height="1.2rem" width="60px" radius="4px"></app-skeleton>
        <app-skeleton height="1.2rem" width="70px" radius="4px"></app-skeleton>
      </div>
      <app-skeleton height="1.5rem" radius="4px" style="margin-top:0.75rem"></app-skeleton>
      <app-skeleton height="1.5rem" width="80%" radius="4px" style="margin-top:0.3rem"></app-skeleton>
      <app-skeleton height="0.85rem" radius="4px" style="margin-top:0.75rem"></app-skeleton>
      <app-skeleton height="0.85rem" width="90%" radius="4px" style="margin-top:0.3rem"></app-skeleton>
      <app-skeleton height="0.85rem" width="65%" radius="4px" style="margin-top:0.3rem"></app-skeleton>
      <div class="skeleton-meta">
        <app-skeleton height="0.75rem" width="80px" radius="3px"></app-skeleton>
        <app-skeleton height="0.75rem" width="60px" radius="3px"></app-skeleton>
      </div>
    </div>
  `,
  styles: [`
    .skeleton-blog-card {
      background: var(--surface-color);
      border: 1px solid var(--border-color);
      border-radius: 16px;
      padding: 1.5rem;
    }
    .skeleton-tags {
      display: flex;
      gap: 0.4rem;
    }
    .skeleton-meta {
      display: flex;
      gap: 1rem;
      margin-top: 1rem;
    }
  `]
})
export class BlogCardSkeletonComponent {}
