import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Skill } from '../../../core/models/skill.model';

const ICON_MAP: Record<string, string> = {
  'Java':           'fab fa-java',
  'Spring Boot':    'fas fa-leaf',
  'Jakarta EE':     'fab fa-java',
  'Angular':        'fab fa-angular',
  'TypeScript':     'fab fa-js-square',
  'Docker':         'fab fa-docker',
  'Git':            'fab fa-git-alt',
  'Jenkins':        'fab fa-jenkins',
  'PostgreSQL':     'fas fa-database',
  'Oracle':         'fas fa-database',
  'SQL':            'fas fa-table',
  'PL/SQL':         'fas fa-code',
  'Jasper Reports': 'fas fa-file-pdf',
  'Oracle Reports': 'fas fa-file-pdf',
  'SonarQube':      'fas fa-shield-alt',
};

@Component({
  selector: 'app-skill-tag',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './skill-tag.component.html',
  styleUrl: './skill-tag.component.scss'
})
export class SkillTagComponent {
  @Input() skill!: Skill;

  get faIcon(): string {
    return ICON_MAP[this.skill?.name] ?? 'fas fa-code';
  }

  imgError = false;

  onImgError() {
    this.imgError = true;
  }
}
