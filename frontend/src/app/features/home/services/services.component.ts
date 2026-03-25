import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Service } from '../../../core/models/service.model';
import { Observable, shareReplay, take, tap } from 'rxjs';
import { RevealDirective } from '../../../shared/directives/reveal.directive';
import { SkeletonComponent } from '../../../shared/components/skeleton/skeleton.component';

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
    imports: [CommonModule, RevealDirective, SkeletonComponent],
    templateUrl: './services.component.html',
    styleUrl: './services.component.scss'
})
export class ServicesComponent implements OnInit {
    services$!: Observable<Service[]>;
    isLoading = true;

    constructor(private portfolioService: PortfolioService, private cdr: ChangeDetectorRef) { }

    ngOnInit(): void {
        // shareReplay + suscripción inicial: sin esto, el | async queda dentro de *ngIf="!isLoading"
        // y nadie se suscribe hasta que isLoading sea false — la petición HTTP nunca arranca (deadlock).
        this.services$ = this.portfolioService.getServices().pipe(
            tap(() => {
                this.isLoading = false;
                this.cdr.markForCheck();
            }),
            shareReplay(1)
        );
        this.services$.pipe(take(1)).subscribe();
    }

    getIcon(iconUrl: string): string {
        return ICON_MAP[iconUrl] ?? 'fas fa-wrench';
    }
}
