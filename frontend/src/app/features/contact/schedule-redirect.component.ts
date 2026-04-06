import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

/**
 * Mantiene enlaces antiguos a /schedule redirigiendo a la página unificada con ancla #agenda.
 */
@Component({
  selector: 'app-schedule-redirect',
  standalone: true,
  template: ''
})
export class ScheduleRedirectComponent implements OnInit {
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.router.navigate(['/contact'], { fragment: 'agenda', replaceUrl: true });
  }
}
