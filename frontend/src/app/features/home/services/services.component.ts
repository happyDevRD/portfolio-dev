import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Service } from '../../../core/models/service.model';
import { Observable } from 'rxjs';

const ICON_MAP: Record<string, string> = {
  'api-icon':       'fas fa-code',
  'web-icon':       'fas fa-globe',
  'backend-icon':   'fas fa-server',
  'frontend-icon':  'fab fa-angular',
  'db-icon':        'fas fa-database',
  'devops-icon':    'fas fa-cogs',
  'mobile-icon':    'fas fa-mobile-alt',
  'cloud-icon':     'fas fa-cloud',
  'security-icon':  'fas fa-shield-alt',
  'design-icon':    'fas fa-paint-brush',
  'migration-icon': 'fas fa-exchange-alt',
  'report-icon':    'fas fa-file-alt',
};

@Component({
    selector: 'app-services',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './services.component.html',
    styleUrl: './services.component.scss'
})
export class ServicesComponent implements OnInit {
    services$!: Observable<Service[]>;

    constructor(private portfolioService: PortfolioService) { }

    ngOnInit(): void {
        this.services$ = this.portfolioService.getServices();
    }

    getIcon(iconUrl: string): string {
        return ICON_MAP[iconUrl] ?? 'fas fa-wrench';
    }
}
