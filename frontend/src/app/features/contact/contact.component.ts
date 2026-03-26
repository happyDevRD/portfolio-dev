import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { SeoService } from '../../core/services/seo.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  private readonly apiUrl = `${environment.apiUrl}/contact`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private seo: SeoService
  ) {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit(): void {
    this.seo.update({
      title: 'Contacto',
      description: 'Escríbeme a hola@elgarcia.org o llama al +1 (809) 757-2408. Full Stack Java / Angular · República Dominicana · trabajo remoto.',
      keywords: 'contacto, desarrollador full stack, freelance, trabajo remoto, República Dominicana',
      url: '/contact'
    });
  }

  isInvalid(field: string): boolean {
    const ctrl = this.contactForm.get(field);
    return !!(ctrl && ctrl.invalid && (ctrl.dirty || ctrl.touched));
  }

  isValid(field: string): boolean {
    const ctrl = this.contactForm.get(field);
    return !!(ctrl && ctrl.valid && (ctrl.dirty || ctrl.touched));
  }

  onSubmit() {
    this.contactForm.markAllAsTouched();
    if (this.contactForm.invalid) return;

    this.isSubmitting = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.http.post(this.apiUrl, this.contactForm.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.successMessage = '¡Mensaje enviado correctamente!';
        this.contactForm.reset();
      },
      error: (err: HttpErrorResponse) => {
        this.isSubmitting = false;
        if (err.status === 429) {
          this.errorMessage = 'Demasiados envíos. Espera un minuto e inténtalo de nuevo.';
        } else if (err.status === 0 || err.status === 503) {
          this.errorMessage = 'El servicio no está disponible en este momento. Intenta más tarde.';
        } else if (err.status >= 500) {
          this.errorMessage = 'Error en el servidor. Intenta más tarde.';
        } else if (err.status === 400) {
          this.errorMessage = 'Revisa los datos del formulario e inténtalo de nuevo.';
        } else {
          this.errorMessage = 'No se pudo enviar el mensaje. Por favor intenta más tarde.';
        }
      }
    });
  }
}
