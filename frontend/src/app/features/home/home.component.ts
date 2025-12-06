import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslatePipe } from '../../shared/pipes/translate.pipe';
import { TerminalComponent } from './terminal/terminal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, TranslatePipe, TerminalComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
