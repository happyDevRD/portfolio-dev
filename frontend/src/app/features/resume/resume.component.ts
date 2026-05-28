import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Observable, Subject, of } from 'rxjs';
import { catchError, takeUntil } from 'rxjs/operators';
import { Experience } from '../../core/models/experience.model';
import { Skill } from '../../core/models/skill.model';
import { SkillGroup } from '../../core/models/skill-group.model';
import { map } from 'rxjs/operators';
import { SeoService } from '../../core/services/seo.service';
import {
  RESUME,
  ResumeCertificate,
  CvProfile,
  CvProfileConfig,
  CV_PROFILES,
  CV_PROFILE_LIST,
} from './resume-content';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, DatePipe, RouterLink],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss',
})
export class ResumeComponent implements OnInit, OnDestroy {
  readonly cv = RESUME;
  readonly profileList = CV_PROFILE_LIST;
  readonly profilesMap = CV_PROFILES;

  experiences$!: Observable<Experience[]>;
  skillGroups: SkillGroup[] = [];
  loadError = false;

  activeCvType: CvProfile = 'fullstack';

  get activeProfile(): CvProfileConfig {
    return CV_PROFILES[this.activeCvType];
  }

  private allSkills: Skill[] = [];
  private destroyed$ = new Subject<void>();

  constructor(private portfolioService: PortfolioService, private seo: SeoService) {}

  ngOnInit(): void {
    this.seo.update({
      title: 'Currículum',
      description:
        'Eleazar Garcia — Desarrollador Full Stack (Java, Spring Boot, Angular). APIs REST, integraciones y reporting. CV y experiencia profesional.',
      keywords: 'currículum, CV, Java, Spring Boot, Angular, integración, JasperReports, REST',
      url: '/resume',
    });

    this.experiences$ = this.portfolioService.getExperiences().pipe(
      catchError(() => {
        this.loadError = true;
        return of([]);
      })
    );

    this.portfolioService
      .getSkills()
      .pipe(
        takeUntil(this.destroyed$),
        catchError(() => {
          this.loadError = true;
          return of([]);
        })
      )
      .subscribe((skills) => {
        this.allSkills = skills;
        this.rebuildSkillGroups();
      });
  }

  ngOnDestroy(): void {
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  setCvType(type: CvProfile): void {
    this.activeCvType = type;
    this.rebuildSkillGroups();
  }

  downloadPDF(): void {
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

  plainSkillsLine(group: SkillGroup): string {
    return group.items.map((s) => s.name).join(', ');
  }

  private rebuildSkillGroups(): void {
    const priority = CV_PROFILES[this.activeCvType].skillPriority;
    const groups: Record<string, Skill[]> = {};
    this.allSkills.forEach((skill) => {
      const cat = skill.category ?? 'Other';
      if (!groups[cat]) groups[cat] = [];
      groups[cat].push(skill);
    });
    this.skillGroups = Object.keys(groups)
      .sort((a, b) => {
        const ia = priority.indexOf(a);
        const ib = priority.indexOf(b);
        if (ia !== -1 && ib !== -1) return ia - ib;
        if (ia !== -1) return -1;
        if (ib !== -1) return 1;
        return a.localeCompare(b);
      })
      .map((category) => ({ category, items: groups[category] }));
  }
}
