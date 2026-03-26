import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Project } from '../../../core/models/project.model';
import { SeoService } from '../../../core/services/seo.service';
import { SkeletonComponent } from '../../../shared/components/skeleton/skeleton.component';
import { switchMap } from 'rxjs';

@Component({
    selector: 'app-project-detail',
    standalone: true,
    imports: [CommonModule, RouterModule, SkeletonComponent],
    templateUrl: './project-detail.component.html',
    styleUrl: './project-detail.component.scss'
})
export class ProjectDetailComponent implements OnInit {
    project = signal<Project | null>(null);
    isLoading = true;
    allProjects = signal<Project[]>([]);

    private route = inject(ActivatedRoute);
    private portfolioService = inject(PortfolioService);
    private seo = inject(SeoService);
    private router = inject(Router);

    ngOnInit(): void {
        this.portfolioService.getProjects().subscribe({
            next: projects => this.allProjects.set(projects),
            error: () => this.allProjects.set([])
        });

        this.route.paramMap.pipe(
            switchMap(params => {
                this.isLoading = true;
                const id = Number(params.get('id'));
                return this.portfolioService.getProjectById(id);
            })
        ).subscribe({
            next: (p: Project) => {
                this.project.set(p);
                this.isLoading = false;
                this.seo.update({
                    title: p.title,
                    description: p.description?.substring(0, 155) ?? 'Proyecto de Eleazar Garcia.',
                    keywords: p.tags?.join(', '),
                    url: `/projects/${p.id}`,
                    type: 'article',
                    imageUrl: p.imageUrl
                });
            },
            error: () => {
                this.isLoading = false;
                this.router.navigate(['/projects']);
            }
        });
    }

    get prevProject(): Project | null {
        const all = this.allProjects();
        const current = this.project();
        if (!current || all.length < 2) return null;
        const idx = all.findIndex(p => p.id === current.id);
        return idx > 0 ? all[idx - 1] : null;
    }

    get nextProject(): Project | null {
        const all = this.allProjects();
        const current = this.project();
        if (!current || all.length < 2) return null;
        const idx = all.findIndex(p => p.id === current.id);
        return idx < all.length - 1 ? all[idx + 1] : null;
    }
}
