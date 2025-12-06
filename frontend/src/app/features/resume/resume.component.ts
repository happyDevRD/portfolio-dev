import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Observable } from 'rxjs';
import { Experience } from '../../core/models/experience.model';
import { Project } from '../../core/models/project.model';
import { TranslatePipe } from '../../shared/pipes/translate.pipe';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, DatePipe, TranslatePipe],
  templateUrl: './resume.component.html',
  styleUrl: './resume.component.scss'
})
export class ResumeComponent implements OnInit {
  experiences$: Observable<Experience[]> | undefined;
  projects$: Observable<Project[]> | undefined;

  constructor(private portfolioService: PortfolioService) { }

  ngOnInit(): void {
    this.experiences$ = this.portfolioService.getExperiences();
    this.projects$ = this.portfolioService.getProjects();
  }

  downloadPDF() {
    window.print();
  }
}
