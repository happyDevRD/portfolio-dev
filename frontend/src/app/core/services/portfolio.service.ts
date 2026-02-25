import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, shareReplay, catchError, of } from 'rxjs';
import { Project } from '../models/project.model';
import { Skill } from '../models/skill.model';
import { Experience } from '../models/experience.model';
import { Service } from '../models/service.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PortfolioService {
    private apiUrl = environment.apiUrl;

    // Cached observables — se comparten entre suscriptores y no re-emiten llamadas HTTP
    private skills$?: Observable<Skill[]>;
    private services$?: Observable<Service[]>;

    constructor(private http: HttpClient) { }

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(`${this.apiUrl}/projects`).pipe(
            catchError(() => of([] as Project[]))
        );
    }

    getProjectById(id: number): Observable<Project> {
        return this.http.get<Project>(`${this.apiUrl}/projects/${id}`);
    }

    getSkills(): Observable<Skill[]> {
        if (!this.skills$) {
            this.skills$ = this.http.get<Skill[]>(`${this.apiUrl}/skills`).pipe(
                catchError(() => of([] as Skill[])),
                shareReplay(1)
            );
        }
        return this.skills$;
    }

    getExperiences(): Observable<Experience[]> {
        return this.http.get<Experience[]>(`${this.apiUrl}/experiences`).pipe(
            catchError(() => of([] as Experience[]))
        );
    }

    getServices(): Observable<Service[]> {
        if (!this.services$) {
            this.services$ = this.http.get<Service[]>(`${this.apiUrl}/services`).pipe(
                catchError(() => of([] as Service[])),
                shareReplay(1)
            );
        }
        return this.services$;
    }
}
