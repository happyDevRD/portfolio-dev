import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BlogService } from '../../../core/services/blog.service';
import { Article } from '../../../core/models/article.model';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-blog',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './blog.component.html',
  styleUrl: './blog.component.scss'
})
export class BlogComponent implements OnInit {
  articles$: Observable<Article[]> | undefined;

  constructor(private blogService: BlogService) { }

  ngOnInit(): void {
    this.articles$ = this.blogService.getArticles();
  }
}
