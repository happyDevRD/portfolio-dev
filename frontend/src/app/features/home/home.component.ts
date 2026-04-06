import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TerminalComponent } from './terminal/terminal.component';
import { ServicesComponent } from './services/services.component';
import { PortfolioService } from '../../core/services/portfolio.service';
import { SkillGroup } from '../../core/models/skill-group.model';
import { Skill } from '../../core/models/skill.model';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { CountUpDirective } from '../../shared/directives/count-up.directive';
import { SeoService } from '../../core/services/seo.service';

const CATEGORY_ORDER = ['Backend', 'Frontend', 'Database', 'DevOps', 'Tools', 'Reporting', 'Quality'];

const FA_ICON_MAP: Record<string, string> = {
  'Java':           'fab fa-java',
  'Spring Boot':    'fas fa-leaf',
  'Quarkus':        'fab fa-quarkus',
  'Jakarta EE':     'fab fa-java',
  'Angular':        'fab fa-angular',
  'TypeScript':     'fab fa-js-square',
  'Docker':         'fab fa-docker',
  'Git':            'fab fa-git-alt',
  'Jenkins':        'fab fa-jenkins',
  'PostgreSQL':     'fas fa-database',
  'Oracle':         'fas fa-database',
  'SQL':            'fas fa-table',
  'PL/SQL':         'fas fa-code',
  'Jasper Reports': 'fas fa-file-pdf',
  'Oracle Reports': 'fas fa-file-pdf',
  'SonarQube':      'fas fa-shield-alt',
};

export interface SnapSection {
  id: string;
  label: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, TerminalComponent, ServicesComponent, CountUpDirective],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit, OnDestroy {
  skillGroups$: Observable<SkillGroup[]>;
  /** Error al cargar skills desde el API (no se oculta como lista vacía). */
  skillsLoadError = false;
  activeSection = 0;

  readonly snapSections: SnapSection[] = [
    { id: 'snap-hero',     label: 'Inicio' },
    { id: 'snap-tech',     label: 'Tecnologías' },
    { id: 'snap-services', label: 'Servicios' },
    { id: 'snap-about',    label: 'Sobre mí' },
  ];

  @ViewChild('snapContainer') snapContainerRef!: ElementRef<HTMLElement>;

  constructor(
    private portfolioService: PortfolioService,
    private seo: SeoService
  ) {
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
        this.skillsLoadError = true;
        return of([]);
      })
    );
  }

  ngOnInit(): void {
    this.seo.update({
      title: 'Eleazar Garcia | Desarrollador Full Stack',
      description: 'Eleazar Garcia — Full Stack (Java, Spring Boot, Angular). Integración, reporting y modernización de sistemas. elgarcia.org',
      keywords: 'desarrollador full stack, Spring Boot, Quarkus, Angular, Java, integración, JasperReports, trabajo remoto',
      url: '/'
    });
    this.seo.setStructuredData(this.seo.homePageJsonLd());
  }

  ngOnDestroy(): void {
    this.seo.setStructuredData(null);
  }

  /** Sección activa: compatible con alturas variables en móvil */
  onScroll(event: Event): void {
    const container = event.target as HTMLElement;
    const cRect = container.getBoundingClientRect();
    const probeY = cRect.top + Math.min(100, cRect.height * 0.12);

    const sections = Array.from(container.querySelectorAll<HTMLElement>(':scope > .snap-section'));
    let idx = 0;
    for (let i = 0; i < sections.length; i++) {
      const r = sections[i].getBoundingClientRect();
      if (r.top <= probeY + 2) {
        idx = i;
      }
    }
    this.activeSection = idx;
  }

  /** Scroll a sección por índice (altura variable) */
  snapTo(index: number): void {
    const container = this.snapContainerRef?.nativeElement;
    if (!container) return;
    const sections = container.querySelectorAll<HTMLElement>(':scope > .snap-section');
    const el = sections[index];
    if (!el) return;
    el.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  getFaIcon(name: string): string {
    return FA_ICON_MAP[name] ?? 'fas fa-code';
  }

  getLevelClass(proficiency: number): string {
    if (proficiency >= 90) return 'level-expert';
    if (proficiency >= 75) return 'level-advanced';
    if (proficiency >= 60) return 'level-mid';
    return 'level-basic';
  }

  getLevelText(proficiency: number): string {
    if (proficiency >= 90) return 'Experto';
    if (proficiency >= 75) return 'Avanzado';
    if (proficiency >= 60) return 'Intermedio';
    return 'Básico';
  }
}
