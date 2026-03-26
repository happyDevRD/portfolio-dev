import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, shareReplay, tap } from 'rxjs';
import { Project } from '../models/project.model';
import { Skill } from '../models/skill.model';
import { Experience } from '../models/experience.model';
import { Service } from '../models/service.model';
import { Testimonial } from '../models/testimonial.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PortfolioService {
    private apiUrl = environment.apiUrl;

    private skills$?: Observable<Skill[]>;

    constructor(private http: HttpClient) { }

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(`${this.apiUrl}/projects`);
    }

    getProjectById(id: number): Observable<Project> {
        return this.http.get<Project>(`${this.apiUrl}/projects/${id}`);
    }

    getSkills(): Observable<Skill[]> {
        if (!this.skills$) {
            this.skills$ = this.http.get<Skill[]>(`${this.apiUrl}/skills`).pipe(
                shareReplay({ bufferSize: 1, refCount: true }),
                tap({ error: () => { this.skills$ = undefined; } })
            );
        }
        return this.skills$;
    }

    getExperiences(): Observable<Experience[]> {
        return this.http.get<Experience[]>(`${this.apiUrl}/experiences`);
    }

    getServices(): Observable<Service[]> {
        return this.http.get<Service[]>(`${this.apiUrl}/services`);
    }

    getTestimonials(): Observable<Testimonial[]> {
        return this.http.get<Testimonial[]>(`${this.apiUrl}/testimonials`);
    }
}
