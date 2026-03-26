import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BlogComponent } from './blog.component';
import { BlogService } from '../../../core/services/blog.service';
import { of } from 'rxjs';
import { Article } from '../../../core/models/article.model';

const mockArticles: Article[] = [
  {
    id: 1,
    title: 'Test Article',
    slug: 'test-article',
    summary: 'A test summary',
    content: '# Test',
    publishedAt: '2024-01-01',
    readingTimeMinutes: 3,
    tags: ['Angular']
  }
];

describe('BlogComponent', () => {
  let component: BlogComponent;
  let fixture: ComponentFixture<BlogComponent>;
  let blogServiceSpy: jasmine.SpyObj<BlogService>;

  beforeEach(async () => {
    blogServiceSpy = jasmine.createSpyObj('BlogService', ['getArticles']);
    blogServiceSpy.getArticles.and.returnValue(of(mockArticles));

    await TestBed.configureTestingModule({
      imports: [BlogComponent, RouterTestingModule, HttpClientTestingModule],
      providers: [{ provide: BlogService, useValue: blogServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(BlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load articles on init', () => {
    expect(blogServiceSpy.getArticles).toHaveBeenCalledTimes(1);
    expect(component.hasError).toBeFalse();
  });

});
