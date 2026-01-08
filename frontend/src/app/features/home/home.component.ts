import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslatePipe } from '../../shared/pipes/translate.pipe';
import { TerminalComponent } from './terminal/terminal.component';
import { ServicesComponent } from './services/services.component';
import { TestimonialsComponent } from './testimonials/testimonials.component';

import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../core/services/portfolio.service';
import { Skill } from '../../core/models/skill.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslatePipe, TerminalComponent, ServicesComponent, TestimonialsComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  skills$!: Observable<Skill[]>;

  constructor(private portfolioService: PortfolioService) {
    this.skills$ = this.portfolioService.getSkills();
  }
}
