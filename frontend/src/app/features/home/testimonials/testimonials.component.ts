import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Testimonial } from '../../../core/models/testimonial.model';
import { Observable } from 'rxjs';
@Component({
    selector: 'app-testimonials',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './testimonials.component.html',
    styleUrl: './testimonials.component.scss'
})
export class TestimonialsComponent implements OnInit {
    testimonials$!: Observable<Testimonial[]>;

    constructor(private portfolioService: PortfolioService) { }

    ngOnInit(): void {
        this.testimonials$ = this.portfolioService.getTestimonials();
    }
}
