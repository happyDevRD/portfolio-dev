import { Directive, HostListener, Input } from '@angular/core';

/**
 * Precarga el chunk lazy de una ruta al pasar el ratón o al enfocar con teclado (mejor TTI en la siguiente navegación).
 */
@Directive({
  selector: '[appPrefetch]',
  standalone: true
})
export class PrefetchRouteDirective {
  @Input({ required: true }) appPrefetch!: string;

  @HostListener('pointerenter')
  @HostListener('focusin')
  prefetch(): void {
    const key = this.appPrefetch?.trim();
    if (!key) return;

    const loaders: Record<string, () => Promise<unknown>> = {
      home: () => import('../../features/home/home.component').then((m) => m.HomeComponent),
      projects: () => import('../../features/projects/projects.component').then((m) => m.ProjectsComponent),
      blog: () => import('../../features/blog/blog/blog.component').then((m) => m.BlogComponent),
      resume: () => import('../../features/resume/resume.component').then((m) => m.ResumeComponent),
      contact: () => import('../../features/contact/contact.component').then((m) => m.ContactComponent)
    };

    loaders[key]?.();
  }
}
