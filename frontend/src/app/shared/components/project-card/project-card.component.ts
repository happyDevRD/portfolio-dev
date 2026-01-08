import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Project } from '../../../core/models/project.model';

import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-project-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './project-card.component.html',
  styleUrl: './project-card.component.scss'
})
export class ProjectCardComponent {
  @Input() project!: Project;
}
