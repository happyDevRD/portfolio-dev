import { Component, OnInit, OnDestroy, ViewChild, ElementRef, HostListener } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TerminalComponent } from './terminal/terminal.component';
import { ServicesComponent } from './services/services.component';
import { PortfolioService } from '../../core/services/portfolio.service';
import { SkillGroup } from '../../core/models/skill-group.model';
import { Skill } from '../../core/models/skill.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CountUpDirective } from '../../shared/directives/count-up.directive';
import { SeoService } from '../../core/services/seo.service';
import { AppComponent } from '../../app.component';

const CATEGORY_ORDER = ['Backend', 'Frontend', 'Database', 'DevOps', 'Tools', 'Reporting', 'Quality'];

const FA_ICON_MAP: Record<string, string> = {
  'Java':           'fab fa-java',
  'Spring Boot':    'fas fa-leaf',
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
  activeSection = 0;

  readonly snapSections: SnapSection[] = [
    { id: 'snap-hero',     label: 'Inicio' },
    { id: 'snap-tech',     label: 'Tecnologías' },
    { id: 'snap-services', label: 'Servicios' },
    { id: 'snap-about',    label: 'Sobre mí' },
  ];

  @ViewChild('snapContainer') snapContainerRef!: ElementRef<HTMLElement>;

  private scrollTimeout: any;

  constructor(
    private portfolioService: PortfolioService,
    private seo: SeoService,
    private appComponent: AppComponent
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
      })
    );
  }

  ngOnInit(): void {
    this.seo.update({
      title: 'Eleazar Garcia | Desarrollador Full Stack',
      description: 'Portfolio de Eleazar Garcia, Desarrollador Full Stack especializado en Spring Boot y Angular.',
      keywords: 'desarrollador full stack, Spring Boot, Angular, Java, TypeScript, portfolio',
      url: '/'
    });
  }

  ngOnDestroy(): void {
    if (this.scrollTimeout) clearTimeout(this.scrollTimeout);
  }

  /** Detecta qué sección está activa según el scroll */
  onScroll(event: Event): void {
    const container = event.target as HTMLElement;
    const scrollTop = container.scrollTop;
    const viewH = container.clientHeight;

    // Notifica al AppComponent para el efecto scrolled del navbar
    this.appComponent.onInnerScroll(scrollTop);

    // Determina la sección activa
    this.activeSection = Math.round(scrollTop / viewH);
  }

  /** Hace scroll suave a una sección por índice */
  snapTo(index: number): void {
    const container = this.snapContainerRef?.nativeElement;
    if (!container) return;
    container.scrollTo({
      top: index * container.clientHeight,
      behavior: 'smooth'
    });
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
