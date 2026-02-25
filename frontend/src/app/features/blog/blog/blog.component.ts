import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BlogService } from '../../../core/services/blog.service';
import { Article } from '../../../core/models/article.model';
import { Observable, of } from 'rxjs';
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

  constructor(private blogService: BlogService, private seo: SeoService) { }

  ngOnInit(): void {
    this.seo.update({
      title: 'Blog',
      description: 'Artículos de ingeniería de software por Eleazar Garcia: arquitectura, backend, frontend y buenas prácticas.',
      keywords: 'blog, ingeniería, Spring Boot, Angular, arquitectura, software',
      url: '/blog'
    });

    this.articles$ = this.blogService.getArticles().pipe(
      tap(articles => {
        this.isLoading = false;
        this.totalArticles = articles.length;
      }),
      catchError(() => {
        this.hasError = true;
        this.isLoading = false;
        return of([]);
      })
    );
    this.filteredArticles$ = this.articles$;
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
