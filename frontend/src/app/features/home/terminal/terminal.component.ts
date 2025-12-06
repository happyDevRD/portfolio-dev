import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';

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

  private commands = [
    { cmd: 'whoami', output: 'Eleazar Garcia - Senior Java Full Stack Developer' },
    {
      cmd: 'cat skills.json', output: `
{
  "backend": ["Java 17", "Spring Boot 3", "PL/SQL"],
  "frontend": ["Angular 17", "TypeScript", "SCSS"],
  "cloud": ["Docker", "AWS", "Jenkins"]
}` },
    { cmd: './load-experience.sh', output: 'Loading professional timeline... [DONE]' }
  ];

  ngOnInit() {
    this.startSequence();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.terminalBody.nativeElement.scrollTop = this.terminalBody.nativeElement.scrollHeight;
    } catch (err) { }
  }

  async startSequence() {
    await this.delay(1000);

    for (const item of this.commands) {
      await this.typeCommand(item.cmd);
      this.lines.push({ type: 'input', content: item.cmd });
      this.isTyping = false;
      await this.delay(300);

      if (item.cmd.includes('skills')) {
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
      await this.delay(50 + Math.random() * 50); // Typography effect
    }
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
