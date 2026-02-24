import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Service } from '../../../core/models/service.model';
import { Observable } from 'rxjs';
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
}
