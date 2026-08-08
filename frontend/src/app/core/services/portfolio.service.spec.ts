import { TestBed } from '@angular/core/testing';
import { PortfolioService } from './portfolio.service';

describe('PortfolioService', () => {
    let service: PortfolioService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [PortfolioService]
        });
        service = TestBed.inject(PortfolioService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should retrieve the static projects list', (done) => {
        service.getProjects().subscribe(projects => {
            expect(projects.length).toBeGreaterThan(0);
            done();
        });
    });
});
