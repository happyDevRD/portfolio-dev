import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CertificateShowcaseComponent } from '../certificate-showcase/certificate-showcase.component';
import { SeoService } from '../../../core/services/seo.service';
import { RESUME } from '../resume-content';

@Component({
  selector: 'app-resume-certificates',
  standalone: true,
  imports: [CommonModule, RouterLink, CertificateShowcaseComponent],
  templateUrl: './resume-certificates.component.html',
  styleUrl: './resume-certificates.component.scss'
})
export class ResumeCertificatesComponent implements OnInit {
  readonly cv = RESUME;

  constructor(private seo: SeoService) {}

  ngOnInit(): void {
    this.seo.update({
      title: 'Certificaciones',
      description:
        'Credenciales verificables y cursos — HackerRank (SQL avanzado, Software Engineer) y más.',
      keywords: 'certificaciones, cursos, HackerRank, SQL, credenciales',
      url: '/resume/certificates'
    });
  }
}
