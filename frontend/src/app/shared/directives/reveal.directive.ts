import { Directive, ElementRef, OnInit } from '@angular/core';
import { RevealService } from '../../core/services/reveal.service';

@Directive({
  selector: '[appReveal]',
  standalone: true
})
export class RevealDirective implements OnInit {
  constructor(private el: ElementRef, private revealService: RevealService) {}

  ngOnInit(): void {
    this.el.nativeElement.classList.add('reveal-section');
    this.revealService.observe(this.el.nativeElement);
  }
}
