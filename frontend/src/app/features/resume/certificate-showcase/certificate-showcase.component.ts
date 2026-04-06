import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RESUME, ResumeCertificate } from '../resume-content';

@Component({
  selector: 'app-certificate-showcase',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './certificate-showcase.component.html',
  styleUrl: './certificate-showcase.component.scss'
})
export class CertificateShowcaseComponent {
  readonly certificates = RESUME.certificates;

  trackByCertificate(_index: number, cert: ResumeCertificate): string {
    return cert.title + '|' + cert.issuedOn;
  }
}
