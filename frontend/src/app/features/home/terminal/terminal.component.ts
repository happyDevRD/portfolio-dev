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
      icon: 'fas fa-building',
      primary: 'Sector público y financiero',
      secondary: 'Sistemas críticos · alta disponibilidad'
    },
    {
      icon: 'fas fa-file-alt',
      primary: 'Reporting e integraciones',
      secondary: '200+ reportes Jasper · cores bancarios'
    },
    {
      icon: 'fas fa-layer-group',
      primary: 'Entrega full stack',
      secondary: 'APIs Spring Boot · front Angular'
    },
    {
      icon: 'fas fa-globe',
      primary: 'Remoto · GMT-4',
      secondary: 'Inglés técnico · RD'
    }
  ];

  ngOnInit() {
    setTimeout(() => this.visible = true, 200);
    setTimeout(() => this.specsVisible = true, 750);
  }
}
