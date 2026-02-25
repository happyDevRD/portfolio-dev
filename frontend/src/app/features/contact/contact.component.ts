import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent {
  contactForm: FormGroup;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  private readonly apiUrl = `${environment.apiUrl}/contact`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.minLength(10)]]
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
      error: () => {
        this.isSubmitting = false;
        this.errorMessage = 'No se pudo enviar el mensaje. Por favor intenta más tarde.';
      }
    });
  }
}
