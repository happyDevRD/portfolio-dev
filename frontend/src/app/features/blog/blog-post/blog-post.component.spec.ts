import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { provideMarkdown } from 'ngx-markdown';

import { BlogPostComponent } from './blog-post.component';
import { BlogService } from '../../../core/services/blog.service';
import { SeoService } from '../../../core/services/seo.service';
import { Article } from '../../../core/models/article.model';

describe('BlogPostComponent', () => {
  let fixture: ComponentFixture<BlogPostComponent>;

  const mockArticle: Article = {
    id: 1,
    title: 'Test',
    slug: 'test',
    summary: 'Summary',
    content: '# Hi',
    publishedAt: '2024-01-01',
    readingTimeMinutes: 1,
    tags: []
  };

  beforeEach(async () => {
    const blogSpy = jasmine.createSpyObj<BlogService>('BlogService', ['getArticleBySlug']);
    blogSpy.getArticleBySlug.and.returnValue(of(mockArticle));

    await TestBed.configureTestingModule({
      imports: [BlogPostComponent, HttpClientTestingModule],
      providers: [
        provideMarkdown(),
        { provide: BlogService, useValue: blogSpy },
        { provide: SeoService, useValue: jasmine.createSpyObj('SeoService', ['update']) },
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ slug: 'test' }))
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BlogPostComponent);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(fixture.componentInstance).toBeTruthy();
  });
});
