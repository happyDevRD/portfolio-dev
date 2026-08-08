import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Article } from '../models/article.model';
import { ARTICLES } from './portfolio.data';

// Static content — no backend deployed right now.
// To reconnect a real backend later, swap the `of(...)` calls below for
// `this.http.get<T>(this.apiUrl)`, same as before.
@Injectable({
    providedIn: 'root'
})
export class BlogService {

    getArticles(): Observable<Article[]> {
        return of(ARTICLES);
    }

    getArticleBySlug(slug: string): Observable<Article | undefined> {
        return of(ARTICLES.find(a => a.slug === slug));
    }
}
