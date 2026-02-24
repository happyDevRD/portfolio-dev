import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SkillTagComponent } from '../../shared/components/skill-tag/skill-tag.component';
import { TerminalComponent } from './terminal/terminal.component';
import { ServicesComponent } from './services/services.component';
import { TestimonialsComponent } from './testimonials/testimonials.component';
import { PortfolioService } from '../../core/services/portfolio.service';
import { SkillGroup } from '../../core/models/skill-group.model';
import { Skill } from '../../core/models/skill.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const CATEGORY_ORDER = ['Backend', 'Frontend', 'Database', 'DevOps', 'Tools', 'Reporting', 'Quality'];

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, SkillTagComponent, TerminalComponent, ServicesComponent, TestimonialsComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  skillGroups$: Observable<SkillGroup[]>;

  constructor(private portfolioService: PortfolioService) {
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
}
