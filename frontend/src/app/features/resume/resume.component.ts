import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Observable } from 'rxjs';
import { Experience } from '../../core/models/experience.model';
import { Project } from '../../core/models/project.model';
import { SkillTagComponent } from '../../shared/components/skill-tag/skill-tag.component';
import { Skill } from '../../core/models/skill.model';
import { map } from 'rxjs/operators';

interface SkillGroup {
  category: string;
  items: Skill[];
}

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, DatePipe, SkillTagComponent],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss'
})
export class ResumeComponent implements OnInit {
  experiences$: Observable<Experience[]> | undefined;
  projects$: Observable<Project[]> | undefined;
  skillGroups$: Observable<SkillGroup[]> | undefined;

  constructor(private portfolioService: PortfolioService) { }

  ngOnInit(): void {
    this.experiences$ = this.portfolioService.getExperiences();
    this.projects$ = this.portfolioService.getProjects();

    this.skillGroups$ = this.portfolioService.getSkills().pipe(
      map(skills => {
        const groups: { [key: string]: Skill[] } = {};
        skills.forEach(skill => {
          if (!groups[skill.category]) {
            groups[skill.category] = [];
          }
          groups[skill.category].push(skill);
        });

        // Define order if desired, or just return keys
        // Prioritize: Backend, Frontend, DevOps, Tools, then others
        const order = ['Backend', 'Frontend', 'DevOps', 'Database', 'Tools', 'Reporting', 'Quality'];

        return Object.keys(groups)
          .sort((a, b) => {
            const idxA = order.indexOf(a);
            const idxB = order.indexOf(b);
            // If both in order list, sort by index
            if (idxA !== -1 && idxB !== -1) return idxA - idxB;
            // If a is in list, it comes first
            if (idxA !== -1) return -1;
            // If b is in list, it comes first
            if (idxB !== -1) return 1;
            // Otherwise sort alphabetic
            return a.localeCompare(b);
          })
          .map(category => ({
            category,
            items: groups[category]
          }));
      })
    );
  }

  downloadPDF() {
    window.print();
  }
}
