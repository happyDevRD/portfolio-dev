import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-terminal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './terminal.component.html',
  styleUrl: './terminal.component.scss'
})
export class TerminalComponent implements OnInit {
  visible = false;
  specsVisible = false;

  readonly specs = [
    {
      icon: 'fas fa-server',
      primary: 'Backend Java Enterprise',
      secondary: 'Spring Boot 3 · Jakarta EE · Oracle'
    },
    {
      icon: 'fas fa-file-alt',
      primary: 'Migración & reporting',
      secondary: 'JasperReports · PL/SQL · integraciones'
    },
    {
      icon: 'fab fa-angular',
      primary: 'Frontend Angular',
      secondary: 'TypeScript · SCSS · RxJS'
    },
    {
      icon: 'fas fa-globe',
      primary: 'Remote-first · GMT-4',
      secondary: 'Contratos por objetivos'
    }
  ];

  ngOnInit() {
    setTimeout(() => this.visible = true, 200);
    setTimeout(() => this.specsVisible = true, 750);
  }
}
