import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Observable } from 'rxjs';
import { Experience } from '../../core/models/experience.model';
import { SkillTagComponent } from '../../shared/components/skill-tag/skill-tag.component';
import { Skill } from '../../core/models/skill.model';
import { SkillGroup } from '../../core/models/skill-group.model';
import { map } from 'rxjs/operators';
import { SeoService } from '../../core/services/seo.service';

const CATEGORY_ORDER = ['Backend', 'Frontend', 'DevOps', 'Database', 'Tools', 'Reporting', 'Quality'];

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, DatePipe, SkillTagComponent],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss'
})
export class ResumeComponent implements OnInit {
  experiences$!: Observable<Experience[]>;
  skillGroups$!: Observable<SkillGroup[]>;

  constructor(private portfolioService: PortfolioService, private seo: SeoService) { }

  ngOnInit(): void {
    this.seo.update({
      title: 'Currículum',
      description: 'Currículum de Eleazar Garcia. Experiencia en desarrollo full stack con Java, Spring Boot, Angular y DevOps.',
      keywords: 'currículum, CV, desarrollador, Java, Spring Boot, Angular, experiencia',
      url: '/resume'
    });

    this.experiences$ = this.portfolioService.getExperiences();

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
      })
    );
  }

  downloadPDF() {
    window.print();
  }
}
