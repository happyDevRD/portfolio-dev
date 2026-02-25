import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Project } from '../../core/models/project.model';
import { ProjectCardComponent } from '../../shared/components/project-card/project-card.component';
import { BehaviorSubject, Observable, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { SeoService } from '../../core/services/seo.service';
import { ProjectCardSkeletonComponent } from '../../shared/components/skeleton/project-card-skeleton.component';

/** Categorías visibles en los filtros — agrupa tags relacionados */
const FILTER_GROUPS: Record<string, string[]> = {
  'Todos':      [],
  'Backend':    ['Java', 'Spring Boot', 'Spring Cloud', 'Jakarta EE', 'Backend'],
  'Frontend':   ['Angular', 'TypeScript', 'Frontend'],
  'Full Stack': ['Full Stack'],
  'Base de datos': ['Oracle', 'SQL', 'PostgreSQL', 'PL/SQL', 'Redis'],
  'DevOps':     ['Docker', 'DevOps', 'GitHub Actions', 'Resilience4j'],
  'Reportes':   ['Jasper Reports', 'Oracle Reports', 'Migration'],
};

export interface FilterTab {
  label: string;
  icon: string;
}

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, ProjectCardComponent, ProjectCardSkeletonComponent],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent implements OnInit {
  isLoading = true;
  private allProjects$ = new BehaviorSubject<Project[]>([]);
  filter$ = new BehaviorSubject<string>('Todos');
  currentPage$ = new BehaviorSubject<number>(1);
  totalPages$ = new BehaviorSubject<number>(1);

  // Solo 3 proyectos por “vista” para que todo quepa en una pantalla
  readonly itemsPerPage = 3;

  /** Solo muestra los grupos que tienen al menos un proyecto */
  readonly filterTabs: FilterTab[] = [
    { label: 'Todos',        icon: 'fas fa-th-large' },
    { label: 'Backend',      icon: 'fas fa-server' },
    { label: 'Frontend',     icon: 'fab fa-angular' },
    { label: 'Full Stack',   icon: 'fas fa-layer-group' },
    { label: 'Base de datos',icon: 'fas fa-database' },
    { label: 'DevOps',       icon: 'fab fa-docker' },
    { label: 'Reportes',     icon: 'fas fa-file-alt' },
  ];

  /** Tabs que tienen proyectos disponibles */
  availableTabs$: Observable<FilterTab[]>;
  filteredProjects$: Observable<Project[]>;
  paginatedProjects$: Observable<Project[]>;
  resultCount$: Observable<number>;

  constructor(
    private portfolioService: PortfolioService,
    private seo: SeoService,
    private cdr: ChangeDetectorRef
  ) {
    // Solo mostrar tabs con proyectos
    this.availableTabs$ = this.allProjects$.pipe(
      map(projects => {
        return this.filterTabs.filter(tab => {
          if (tab.label === 'Todos') return true;
          const keywords = FILTER_GROUPS[tab.label] ?? [];
          return projects.some(p =>
            p.tags?.some(t => keywords.some(k => t.toLowerCase().includes(k.toLowerCase())))
          );
        });
      })
    );

    this.filteredProjects$ = combineLatest([this.allProjects$, this.filter$]).pipe(
      map(([projects, filter]) => {
        let filtered: Project[];
        if (filter === 'Todos') {
          filtered = projects;
        } else {
          const keywords = FILTER_GROUPS[filter] ?? [filter];
          filtered = projects.filter(p =>
            p.tags?.some(t => keywords.some(k => t.toLowerCase().includes(k.toLowerCase())))
          );
        }
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

    this.resultCount$ = this.filteredProjects$.pipe(map(p => p.length));
  }

  ngOnInit(): void {
    this.seo.update({
      title: 'Proyectos',
      description: 'Proyectos de desarrollo full stack de Eleazar Garcia: aplicaciones web con Spring Boot, Angular, Docker y más.',
      keywords: 'proyectos, portfolio, Spring Boot, Angular, full stack',
      url: '/projects'
    });
    this.portfolioService.getProjects().subscribe(p => {
      this.allProjects$.next(p);
      this.isLoading = false;
      this.cdr.detectChanges();
    });
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
