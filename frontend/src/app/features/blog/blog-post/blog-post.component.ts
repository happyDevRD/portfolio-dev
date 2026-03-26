import { Component, OnInit, AfterViewChecked, ElementRef, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { BlogService } from '../../../core/services/blog.service';
import { Article } from '../../../core/models/article.model';
import { Observable, switchMap, tap } from 'rxjs';
import { MarkdownModule } from 'ngx-markdown';
import { SeoService } from '../../../core/services/seo.service';

export interface TocEntry {
  id: string;
  text: string;
  level: number;
}

@Component({
  selector: 'app-blog-post',
  standalone: true,
  imports: [CommonModule, RouterModule, MarkdownModule],
  templateUrl: './blog-post.component.html',
  styleUrl: './blog-post.component.scss'
})
export class BlogPostComponent implements OnInit, AfterViewChecked {
  article$: Observable<Article> | undefined;
  linkCopied = false;
  toc: TocEntry[] = [];
  activeHeading = '';

  private tocBuilt = false;
  private articleSlug = '';

  @ViewChild('markdownContainer') markdownContainer?: ElementRef<HTMLElement>;

  private route = inject(ActivatedRoute);
  private blogService = inject(BlogService);
  private seo = inject(SeoService);

  /** Calcula tiempo de lectura local basado en palabras del contenido */
  calcReadingTime(content?: string): number {
    if (!content) return 1;
    const words = content.trim().split(/\s+/).length;
    return Math.max(1, Math.ceil(words / 200));
  }

  /** Extrae entradas de tabla de contenidos del markdown */
  buildTocFromMarkdown(content?: string): TocEntry[] {
    if (!content) return [];
    const lines = content.split('\n');
    const entries: TocEntry[] = [];
    for (const line of lines) {
      const match = line.match(/^(#{1,3})\s+(.+)/);
      if (match) {
        const level = match[1].length;
        const text = match[2].replace(/[`*_]/g, '').trim();
        const id = text.toLowerCase().replace(/[^a-z0-9\s-]/g, '').replace(/\s+/g, '-');
        entries.push({ id, text, level });
      }
    }
    return entries;
  }

  scrollToHeading(id: string): void {
    const el = document.getElementById(id);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'start' });
      this.activeHeading = id;
    }
  }

  ngOnInit(): void {
    this.article$ = this.route.paramMap.pipe(
      switchMap(params => {
        const slug = params.get('slug') ?? '';
        this.articleSlug = slug;
        this.tocBuilt = false;
        this.toc = [];
        return this.blogService.getArticleBySlug(slug);
      }),
      tap(article => {
        this.toc = this.buildTocFromMarkdown(article.content);
        this.seo.update({
          title: article.title,
          description: article.summary ?? 'Artículo técnico por Eleazar Garcia.',
          keywords: article.tags?.join(', '),
          url: `/blog/${article.slug}`,
          type: 'article',
          imageUrl: article.coverImageUrl
        });
      })
    );
  }

  ngAfterViewChecked(): void {
    if (!this.tocBuilt && this.markdownContainer) {
      const headings = this.markdownContainer.nativeElement.querySelectorAll('h1, h2, h3');
      if (headings.length > 0) {
        this.toc.forEach((entry, idx) => {
          const heading = headings[idx] as HTMLElement;
          if (heading && !heading.id) {
            heading.id = entry.id;
          }
        });
        this.tocBuilt = true;
      }
    }
  }

  getTwitterShareUrl(title: string): string {
    const url = encodeURIComponent(window.location.href);
    const text = encodeURIComponent(`${title} — Eleazar Garcia`);
    return `https://twitter.com/intent/tweet?text=${text}&url=${url}`;
  }

  getLinkedInShareUrl(): string {
    const url = encodeURIComponent(window.location.href);
    return `https://www.linkedin.com/sharing/share-offsite/?url=${url}`;
  }

  copyLink(): void {
    navigator.clipboard.writeText(window.location.href).then(() => {
      this.linkCopied = true;
      setTimeout(() => (this.linkCopied = false), 2500);
    });
  }
}
