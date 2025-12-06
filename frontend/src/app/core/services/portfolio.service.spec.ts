import { TestBed } from '@angular/core/testing';
import { PortfolioService } from './portfolio.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Project } from '../models/project.model';

describe('PortfolioService', () => {
    let service: PortfolioService;
    let httpMock: HttpTestingController;

    const mockProjects: Project[] = [
        { title: 'Project 1', description: 'Test', imageUrl: '', startDate: '2024-01-01', tags: [] },
        { title: 'Project 2', description: 'Test', imageUrl: '', startDate: '2024-02-01', tags: [] }
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
            expect(projects).toEqual(mockProjects);
        });

        const req = httpMock.expectOne('/api/projects');
        expect(req.request.method).toBe('GET');
        req.flush(mockProjects);
    });
});
