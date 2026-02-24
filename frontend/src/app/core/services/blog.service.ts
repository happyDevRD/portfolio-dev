import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class BlogService {
    private apiUrl = `${environment.apiUrl}/articles`;

    constructor(private http: HttpClient) { }

    getArticles(): Observable<Article[]> {
        return this.http.get<Article[]>(this.apiUrl);
    }

    getArticleBySlug(slug: string): Observable<Article> {
        return this.http.get<Article>(`${this.apiUrl}/${slug}`);
    }
}
