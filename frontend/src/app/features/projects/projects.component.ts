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
  private allProjects$ = new BehaviorSubject<Project[]>([]);
  filter$ = new BehaviorSubject<string>('Todos');
  currentPage$ = new BehaviorSubject<number>(1);
  totalPages$ = new BehaviorSubject<number>(1);

  readonly itemsPerPage = 6;

  categories$: Observable<string[]> = this.allProjects$.pipe(
    map(projects => {
      const tags = new Set<string>();
      projects.forEach(p => p.tags?.forEach(t => tags.add(t)));
      return ['Todos', ...Array.from(tags).sort()];
    })
  );

  filteredProjects$: Observable<Project[]>;
  paginatedProjects$: Observable<Project[]>;

  constructor(private portfolioService: PortfolioService) {
    this.filteredProjects$ = combineLatest([this.allProjects$, this.filter$]).pipe(
      map(([projects, filter]) => {
        const filtered = filter === 'Todos'
          ? projects
          : projects.filter(p => p.tags?.some(t => t.toLowerCase() === filter.toLowerCase()));

        this.currentPage$.next(1);
        this.totalPages$.next(Math.ceil(filtered.length / this.itemsPerPage) || 1);
        return filtered;
      })
    );

    this.paginatedProjects$ = combineLatest([this.filteredProjects$, this.currentPage$]).pipe(
      map(([projects, page]) => {
        const start = (page - 1) * this.itemsPerPage;
        return projects.slice(start, start + this.itemsPerPage);
      })
    );
  }

  ngOnInit(): void {
    this.portfolioService.getProjects().subscribe(p => this.allProjects$.next(p));
  }

  setFilter(category: string): void {
    this.filter$.next(category);
  }

  nextPage(): void {
    if (this.currentPage$.value < this.totalPages$.value) {
      this.currentPage$.next(this.currentPage$.value + 1);
      this.scrollToTop();
    }
  }

  prevPage(): void {
    if (this.currentPage$.value > 1) {
      this.currentPage$.next(this.currentPage$.value - 1);
      this.scrollToTop();
    }
  }

  private scrollToTop(): void {
    document.querySelector('.projects-section')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}
