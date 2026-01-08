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
  filter$ = new BehaviorSubject<string>('Todos');

  itemsPerPage = 6;
  currentPage$ = new BehaviorSubject<number>(1);
  totalPages$ = new BehaviorSubject<number>(1);

  categories = ['Todos', 'Backend', 'Frontend', 'DevOps'];
  currentCategory = 'Todos';

  filteredProjects$: Observable<Project[]>;
  paginatedProjects$: Observable<Project[]>;

  constructor(private portfolioService: PortfolioService) {
    // 1. Filter Projects
    this.filteredProjects$ = combineLatest([
      this.projects$,
      this.filter$
    ]).pipe(
      map(([projects, filter]) => {
        const filtered = filter === 'Todos' ? projects : projects.filter(p =>
          p.tags && p.tags.some(tag => tag.toLowerCase() === filter.toLowerCase())
        );

        // Reset to page 1 on filter change
        this.currentPage$.next(1);

        // Calculate total pages
        const total = Math.ceil(filtered.length / this.itemsPerPage);
        this.totalPages$.next(total || 1);

        return filtered;
      })
    );

    // 2. Paginate Filtered Projects
    this.paginatedProjects$ = combineLatest([
      this.filteredProjects$,
      this.currentPage$
    ]).pipe(
      map(([projects, page]) => {
        const startIndex = (page - 1) * this.itemsPerPage;
        return projects.slice(startIndex, startIndex + this.itemsPerPage);
      })
    );
  }

  ngOnInit(): void {
    this.portfolioService.getProjects().subscribe(p => this.projects$.next(p));
  }

  setFilter(category: string): void {
    this.currentCategory = category;
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
    const element = document.querySelector('.projects-section');
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}
