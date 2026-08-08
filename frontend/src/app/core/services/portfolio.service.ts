import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Project } from '../models/project.model';
import { Skill } from '../models/skill.model';
import { Experience } from '../models/experience.model';
import { Service } from '../models/service.model';
import { Testimonial } from '../models/testimonial.model';
import { PROJECTS, SKILLS, EXPERIENCES, SERVICES, TESTIMONIALS } from './portfolio.data';

// Static content — no backend deployed right now.
// To reconnect a real backend later, swap the `of(...)` calls below for
// `this.http.get<T>(`${this.apiUrl}/...`)`, same as before.
@Injectable({
    providedIn: 'root'
})
export class PortfolioService {

    getProjects(): Observable<Project[]> {
        return of(PROJECTS);
    }

    getProjectById(id: number): Observable<Project | undefined> {
        return of(PROJECTS.find(p => p.id === id));
    }

    getSkills(): Observable<Skill[]> {
        return of(SKILLS);
    }

    getExperiences(): Observable<Experience[]> {
        return of(EXPERIENCES);
    }

    getServices(): Observable<Service[]> {
        return of(SERVICES);
    }

    getTestimonials(): Observable<Testimonial[]> {
        return of(TESTIMONIALS);
    }
}
