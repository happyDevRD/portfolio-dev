import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Project } from '../../../core/models/project.model';

@Component({
    selector: 'app-project-detail',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './project-detail.component.html',
    styleUrl: './project-detail.component.scss'
})
export class ProjectDetailComponent implements OnInit {
    project = signal<Project | null>(null);

    constructor(
        private route: ActivatedRoute,
        private portfolioService: PortfolioService
    ) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.portfolioService.getProjectById(Number(id)).subscribe((p: Project) => {
                if (p) this.project.set(p);
            });
        }
    }
}
