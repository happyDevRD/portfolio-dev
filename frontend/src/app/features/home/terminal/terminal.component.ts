import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Skill } from '../../../core/models/skill.model';

interface TerminalLine {
  type: 'input' | 'output' | 'success' | 'info';
  content: string;
}

@Component({
  selector: 'app-terminal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './terminal.component.html',
  styleUrl: './terminal.component.scss'
})
export class TerminalComponent implements OnInit, AfterViewChecked {
  @ViewChild('terminalBody') terminalBody!: ElementRef;

  lines: TerminalLine[] = [];
  isTyping = false;
  currentText = '';

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit() {
    this.portfolioService.getSkills().subscribe({
      next: (skills) => this.startSequence(skills),
      error: () => this.startSequence([])
    });
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.terminalBody.nativeElement.scrollTop = this.terminalBody.nativeElement.scrollHeight;
    } catch (err) { }
  }

  private buildSkillsJson(skills: Skill[]): string {
    const grouped: Record<string, string[]> = {};
    for (const skill of skills) {
      const category = skill.category ?? 'Other';
      if (!grouped[category]) grouped[category] = [];
      grouped[category].push(skill.name);
    }
    const lines = Object.entries(grouped)
      .map(([cat, names]) => `  "${cat.toLowerCase()}": [${names.map(n => `"${n}"`).join(', ')}]`);
    return '{\n' + lines.join(',\n') + '\n}';
  }

  async startSequence(skills: Skill[]) {
    const skillsJson = skills.length > 0
      ? this.buildSkillsJson(skills)
      : `{\n  "backend": ["Java 17", "Spring Boot 3", "PL/SQL"],\n  "frontend": ["Angular 17", "TypeScript"],\n  "devops": ["Docker", "Jenkins"]\n}`;

    const commands = [
      { cmd: 'whoami', output: 'Eleazar Garcia - Desarrollador Full Stack Java', isJson: false },
      { cmd: 'cat skills.json', output: skillsJson, isJson: true },
      { cmd: './load-experience.sh', output: 'Cargando línea de tiempo profesional... [HECHO]', isJson: false }
    ];

    await this.delay(1000);

    for (const item of commands) {
      await this.typeCommand(item.cmd);
      this.lines.push({ type: 'input', content: item.cmd });
      this.isTyping = false;
      await this.delay(300);

      if (item.isJson) {
        this.lines.push({ type: 'output', content: '<pre>' + item.output + '</pre>' });
      } else {
        this.lines.push({ type: 'success', content: item.output });
      }
      await this.delay(800);
    }
  }

  async typeCommand(cmd: string) {
    this.isTyping = true;
    this.currentText = '';
    for (const char of cmd) {
      this.currentText += char;
      await this.delay(50 + Math.random() * 50);
    }
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
