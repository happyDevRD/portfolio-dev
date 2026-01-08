import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../models/project.model';
import { Skill } from '../models/skill.model';
import { Experience } from '../models/experience.model';
import { Service } from '../models/service.model';
import { Testimonial } from '../models/testimonial.model';

@Injectable({
    providedIn: 'root'
})
export class PortfolioService {
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) { }

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(`${this.apiUrl}/projects`);
    }

    getProjectById(id: number): Observable<Project> {
        return this.http.get<Project>(`${this.apiUrl}/projects/${id}`);
    }

    getSkills(): Observable<Skill[]> {
        return this.http.get<Skill[]>(`${this.apiUrl}/skills`);
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
