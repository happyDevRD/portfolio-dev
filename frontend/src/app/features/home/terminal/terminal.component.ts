import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';
import { PortfolioService } from '../../../core/services/portfolio.service';
import { Skill } from '../../../core/models/skill.model';

interface TerminalLine {
  type: 'input' | 'output' | 'success' | 'info' | 'warn';
  content: string;
}

@Component({
  selector: 'app-terminal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './terminal.component.html',
  styleUrl: './terminal.component.scss'
})
export class TerminalComponent implements OnInit, OnDestroy {
  @ViewChild('terminalBody') terminalBody!: ElementRef;

  lines: TerminalLine[] = [];
  isTyping = false;
  currentText = '';

  private destroyed$ = new Subject<void>();
  private destroyed = false;

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit() {
    this.portfolioService.getSkills()
      .pipe(takeUntil(this.destroyed$))
      .subscribe({
        next: (skills) => this.startSequence(skills),
        error: () => this.startSequence([])
      });
  }

  ngOnDestroy() {
    this.destroyed = true;
    this.destroyed$.next();
    this.destroyed$.complete();
  }

  private scrollToBottom(): void {
    try {
      const el = this.terminalBody?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    } catch { }
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

  private getSystemInfo(): string {
    const now = new Date();
    const pad = (n: number) => String(n).padStart(2, '0');
    const date = `${now.getFullYear()}-${pad(now.getMonth()+1)}-${pad(now.getDate())}`;
    const time = `${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`;
    const tz = Intl.DateTimeFormat().resolvedOptions().timeZone;
    return `Host: elgarcia.org | ${date} ${time} | TZ: ${tz}`;
  }

  async startSequence(skills: Skill[]) {
    const skillsJson = skills.length > 0
      ? this.buildSkillsJson(skills)
      : `{\n  "backend": ["Java 17", "Spring Boot 3 & Quarkus", "PL/SQL"],\n  "frontend": ["Angular 17", "TypeScript"],\n  "devops": ["Docker", "Jenkins"]\n}`;

    const commands: { cmd: string; output: string; type: TerminalLine['type'] }[] = [
      {
        cmd: 'whoami',
        output: 'eleazar.garcia — Full Stack Developer (Java · Angular)',
        type: 'success'
      },
      {
        cmd: 'date',
        output: this.getSystemInfo(),
        type: 'info'
      },
      {
        cmd: 'cat skills.json',
        output: skillsJson,
        type: 'output'
      },
      {
        cmd: './check-status.sh',
        output: '✔  Abierto a colaboraciones remotas y proyectos por objetivos',
        type: 'success'
      }
    ];

    await this.delay(800);

    for (const item of commands) {
      if (this.destroyed) return;
      await this.typeCommand(item.cmd);
      if (this.destroyed) return;
      this.lines.push({ type: 'input', content: item.cmd });
      this.isTyping = false;
      this.scrollToBottom();
      await this.delay(200);

      if (item.type === 'output') {
        this.lines.push({ type: 'output', content: '<pre>' + item.output + '</pre>' });
      } else {
        this.lines.push({ type: item.type, content: item.output });
      }
      this.scrollToBottom();
      await this.delay(600);
    }

    // Idle blinking prompt after sequence completes
    if (!this.destroyed) {
      this.isTyping = true;
      this.currentText = '';
    }
  }

  async typeCommand(cmd: string) {
    this.isTyping = true;
    this.currentText = '';
    for (const char of cmd) {
      if (this.destroyed) return;
      this.currentText += char;
      await this.delay(45 + Math.random() * 55);
    }
  }

  delay(ms: number) {
    return new Promise<void>(resolve => setTimeout(resolve, ms));
  }
}
