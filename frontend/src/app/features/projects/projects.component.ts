import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Project } from '../../core/models/project.model';
import { ProjectCardComponent } from '../../shared/components/project-card/project-card.component';
import { BehaviorSubject, Observable, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, ProjectCardComponent],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent implements OnInit {
  projects$ = new BehaviorSubject<Project[]>([]);
  filter$ = new BehaviorSubject<string>('All');

  categories = ['All', 'Backend', 'Frontend', 'DevOps'];

  filteredProjects$: Observable<Project[]>;

  constructor(private portfolioService: PortfolioService) {
    this.filteredProjects$ = combineLatest([
      this.projects$,
      this.filter$
    ]).pipe(
      map(([projects, filter]) => {
        if (filter === 'All') return projects;
        // Simple heuristic filtering based on description or title for this demo
        // Ideally projects would have a 'category' or 'technologies' list
        return projects.filter(p =>
          p.description.toLowerCase().includes(filter.toLowerCase()) ||
          p.title.toLowerCase().includes(filter.toLowerCase())
        );
      })
    );
  }

  ngOnInit(): void {
    this.portfolioService.getProjects().pipe(
      map(projects => projects.map(p => ({
        ...p,
        tags: this.generateTags(p.description)
      })))
    ).subscribe(p => this.projects$.next(p));
  }

  private generateTags(description: string): string[] {
    const keywords = ['Angular', 'Spring Boot', 'Java', 'Docker', 'SQL', 'Oracle', 'Jasper', 'IBM', 'SOAP', 'REST'];
    return keywords.filter(k => description.includes(k));
  }

  setFilter(category: string): void {
    this.filter$.next(category);
  }
}
