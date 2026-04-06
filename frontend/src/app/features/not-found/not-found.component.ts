import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

import { SeoService } from '../../core/services/seo.service';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.scss'
})
export class NotFoundComponent implements OnInit {
  private readonly seo = inject(SeoService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    const path = this.router.url.split('?')[0] || '/';
    this.seo.update({
      title: 'Página no encontrada',
      description: 'El recurso solicitado no existe en el sitio de Eleazar Garcia.',
      url: path,
      robots: 'noindex, nofollow'
    });
  }
}
