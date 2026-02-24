import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PortfolioService } from './portfolio.service';
import { Project } from '../models/project.model';
import { environment } from '../../../environments/environment';

describe('PortfolioService', () => {
    let service: PortfolioService;
    let httpMock: HttpTestingController;

    const mockProjects: Project[] = [
        {
            id: 1, title: 'Proyecto 1', description: 'Desc', imageUrl: '',
            startDate: '2024-01-01', tags: []
        },
        {
            id: 2, title: 'Proyecto 2', description: 'Desc', imageUrl: '',
            startDate: '2024-02-01', tags: []
        }
    ];

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [PortfolioService]
        });
        service = TestBed.inject(PortfolioService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should retrieve projects from the API', () => {
        service.getProjects().subscribe(projects => {
            expect(projects.length).toBe(2);
            expect(projects[0].title).toBe('Proyecto 1');
        });

        const req = httpMock.expectOne(`${environment.apiUrl}/projects`);
        expect(req.request.method).toBe('GET');
        req.flush(mockProjects);
    });

    it('should retrieve skills from the API', () => {
        service.getSkills().subscribe(skills => {
            expect(skills).toBeDefined();
        });

        const req = httpMock.expectOne(`${environment.apiUrl}/skills`);
        expect(req.request.method).toBe('GET');
        req.flush([]);
    });

    it('should retrieve experiences from the API', () => {
        service.getExperiences().subscribe(exp => {
            expect(exp).toBeDefined();
        });

        const req = httpMock.expectOne(`${environment.apiUrl}/experiences`);
        expect(req.request.method).toBe('GET');
        req.flush([]);
    });
});
