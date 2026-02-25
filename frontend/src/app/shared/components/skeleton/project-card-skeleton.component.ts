import { Component } from '@angular/core';
import { SkeletonComponent } from './skeleton.component';

@Component({
  selector: 'app-project-card-skeleton',
  standalone: true,
  imports: [SkeletonComponent],
  template: `
    <div class="skeleton-card">
      <app-skeleton height="160px" radius="0"></app-skeleton>
      <div class="skeleton-body">
        <app-skeleton height="1.2rem" width="70%" radius="4px"></app-skeleton>
        <app-skeleton height="0.8rem" radius="4px" style="margin-top:0.5rem"></app-skeleton>
        <app-skeleton height="0.8rem" width="85%" radius="4px" style="margin-top:0.3rem"></app-skeleton>
        <app-skeleton height="0.8rem" width="60%" radius="4px" style="margin-top:0.3rem"></app-skeleton>
        <div class="skeleton-tags">
          <app-skeleton height="1.5rem" width="60px" radius="4px"></app-skeleton>
          <app-skeleton height="1.5rem" width="50px" radius="4px"></app-skeleton>
          <app-skeleton height="1.5rem" width="70px" radius="4px"></app-skeleton>
        </div>
        <div class="skeleton-btns">
          <app-skeleton height="2rem" radius="6px"></app-skeleton>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .skeleton-card {
      background: var(--surface-color);
      border: 1px solid var(--border-color);
      border-radius: 12px;
      overflow: hidden;
    }
    .skeleton-body {
      padding: 1.25rem;
      display: flex;
      flex-direction: column;
      gap: 0;
    }
    .skeleton-tags {
      display: flex;
      gap: 0.4rem;
      margin: 0.75rem 0;
    }
    .skeleton-btns {
      margin-top: 0.5rem;
    }
  `]
})
export class ProjectCardSkeletonComponent {}
