import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BlogService } from '../../../core/services/blog.service';
import { Article } from '../../../core/models/article.model';
import { Observable, of, shareReplay, take } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { SeoService } from '../../../core/services/seo.service';
import { BlogCardSkeletonComponent } from '../../../shared/components/skeleton/blog-card-skeleton.component';
import { RevealDirective } from '../../../shared/directives/reveal.directive';

@Component({
  selector: 'app-blog',
  standalone: true,
  imports: [CommonModule, RouterModule, BlogCardSkeletonComponent, RevealDirective],
  templateUrl: './blog.component.html',
  styleUrl: './blog.component.scss'
})
export class BlogComponent implements OnInit {
  articles$!: Observable<Article[]>;
  filteredArticles$!: Observable<Article[]>;
  hasError = false;
  isLoading = true;
  searchQuery = '';
  totalArticles = 0;

  constructor(
    private blogService: BlogService,
    private seo: SeoService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.seo.update({
      title: 'Blog',
      description: 'Artículos técnicos: Spring Boot, Angular, Docker, JasperReports y arquitectura limpia. Por Eleazar Garcia.',
      keywords: 'blog, Spring Boot, Angular, Docker, arquitectura, JasperReports',
      url: '/blog'
    });

    /**
     * shareReplay + suscripción inicial: sin esto, el | async del listado queda detrás de
     * *ngIf="!isLoading" y nadie se suscribe hasta que isLoading sea false — el tap que pone
     * isLoading en false nunca corre → la petición HTTP no arranca (deadlock).
     */
    this.articles$ = this.blogService.getArticles().pipe(
      tap(articles => {
        this.isLoading = false;
        this.totalArticles = articles.length;
        this.cdr.markForCheck();
      }),
      catchError(() => {
        this.hasError = true;
        this.isLoading = false;
        this.cdr.markForCheck();
        return of([]);
      }),
      shareReplay(1)
    );
    this.filteredArticles$ = this.articles$;
    this.articles$.pipe(take(1)).subscribe();
  }

  onSearch(query: string): void {
    this.searchQuery = query;
    this.filteredArticles$ = this.articles$.pipe(
      map(articles => {
        if (!query.trim()) return articles;
        const q = query.toLowerCase();
        return articles.filter(a =>
          a.title.toLowerCase().includes(q) ||
          a.summary?.toLowerCase().includes(q) ||
          a.tags?.some(t => t.toLowerCase().includes(q))
        );
      })
    );
  }
}
