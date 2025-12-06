import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Skill } from '../../../core/models/skill.model';

@Component({
  selector: 'app-skill-tag',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './skill-tag.component.html',
  styleUrl: './skill-tag.component.scss'
})
export class SkillTagComponent {
  @Input() skill!: Skill;
}
