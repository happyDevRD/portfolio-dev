import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Experience } from '../../core/models/experience.model';
import { Skill } from '../../core/models/skill.model';
import { SkillGroup } from '../../core/models/skill-group.model';
import { map } from 'rxjs/operators';
import { SeoService } from '../../core/services/seo.service';
import { RESUME, ResumeCertificate } from './resume-content';

const CATEGORY_ORDER = ['Backend', 'Frontend', 'DevOps', 'Database', 'Tools', 'Reporting', 'Quality'];

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, DatePipe, RouterLink],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss'
})
export class ResumeComponent implements OnInit {
  /** Textos compartidos web / PDF (ver resume-content.ts). */
  readonly cv = RESUME;

  experiences$!: Observable<Experience[]>;
  skillGroups$!: Observable<SkillGroup[]>;
  loadError = false;

  constructor(private portfolioService: PortfolioService, private seo: SeoService) { }

  ngOnInit(): void {
    this.seo.update({
      title: 'Currículum',
      description:
        'Eleazar Garcia — Desarrollador Full Stack (Java, Spring Boot, Angular). APIs REST, integraciones y reporting. CV y experiencia profesional.',
      keywords: 'currículum, CV, Java, Spring Boot, Angular, integración, JasperReports, REST',
      url: '/resume'
    });

    this.experiences$ = this.portfolioService.getExperiences().pipe(
      catchError(() => {
        this.loadError = true;
        return of([]);
      })
    );

    this.skillGroups$ = this.portfolioService.getSkills().pipe(
      map(skills => {
        const groups: Record<string, Skill[]> = {};
        skills.forEach(skill => {
          if (!groups[skill.category]) groups[skill.category] = [];
          groups[skill.category].push(skill);
        });
        return Object.keys(groups)
          .sort((a, b) => {
            const ia = CATEGORY_ORDER.indexOf(a);
            const ib = CATEGORY_ORDER.indexOf(b);
            if (ia !== -1 && ib !== -1) return ia - ib;
            if (ia !== -1) return -1;
            if (ib !== -1) return 1;
            return a.localeCompare(b);
          })
          .map(category => ({ category, items: groups[category] }));
      }),
      catchError(() => {
        this.loadError = true;
        return of([]);
      })
    );
  }

  downloadPDF() {
    window.print();
  }

  trackByExperienceId(_index: number, job: Experience): number {
    return job.id;
  }

  trackBySkillId(_index: number, skill: Skill): number {
    return skill.id;
  }

  trackByCategory(_index: number, group: SkillGroup): string {
    return group.category;
  }

  trackByCertificate(_index: number, cert: ResumeCertificate): string {
    return cert.title + '|' + cert.issuedOn;
  }

  /** Habilidades en texto plano por categoría (vista impresión / ATS). */
  plainSkillsLine(group: SkillGroup): string {
    return group.items.map((s) => s.name).join(', ');
  }
}
