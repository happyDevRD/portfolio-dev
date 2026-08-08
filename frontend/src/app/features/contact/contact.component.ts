import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { TranslatePipe } from '../../shared/pipes/translate.pipe';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslatePipe],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent {
  contactForm: FormGroup;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      message: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  onSubmit() {
    if (this.contactForm.invalid) return;

    this.isSubmitting = true;
    this.successMessage = '';
    this.errorMessage = '';

    // TODO: replace with your real Formspree endpoint, e.g. https://formspree.io/f/xxxxxxx
    const url = 'https://formspree.io/f/REPLACE_ME';

    this.http.post(url, this.contactForm.value, { headers: { Accept: 'application/json' } }).subscribe({
      next: (response) => {
        console.log('Form submitted:', response);
        this.isSubmitting = false;
        this.successMessage = 'Message sent successfully!';
        this.contactForm.reset();
      },
      error: (error) => {
        console.error('Error submitting form:', error);
        this.isSubmitting = false;
        this.errorMessage = 'Failed to send message. Please try again later.';
      }
    });
  }
}
