import { AfterViewInit, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../environments/environment';
import { SeoService } from '../../core/services/seo.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent implements OnInit, AfterViewInit {
  readonly contactEmail = 'hola@elgarcia.org';
  readonly linkedinUrl = 'https://www.linkedin.com/in/garciaeleazar/';
  readonly githubUrl = 'https://github.com/happyDevRD';
  readonly gmailComposeUrl: string;
  readonly mailtoUrl: string;

  scheduleForm: FormGroup;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';

  readonly meetingTypes: { value: string; label: string }[] = [
    { value: 'TECH_CONSULTING', label: 'Consultoría técnica' },
    { value: 'JOB_INTERVIEW', label: 'Entrevista laboral' },
    { value: 'COLLABORATION', label: 'Colaboración u oportunidad' },
    { value: 'OTHER', label: 'Otro' }
  ];

  readonly durationOptions = [30, 45, 60, 90];

  private readonly meetingsApiUrl = `${environment.apiUrl}/meetings`;
  private readonly calendarDisabledMessageToken = 'app.google.calendar';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private seo: SeoService,
    private route: ActivatedRoute
  ) {
    const subject = 'Contacto desde elgarcia.org';
    this.gmailComposeUrl =
      'https://mail.google.com/mail/?view=cm&fs=1' +
      `&to=${encodeURIComponent(this.contactEmail)}` +
      `&su=${encodeURIComponent(subject)}`;
    this.mailtoUrl = `mailto:${this.contactEmail}?subject=${encodeURIComponent(subject)}`;

    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const dateStr = tomorrow.toISOString().slice(0, 10);

    this.scheduleForm = this.fb.group({
      meetingType: ['TECH_CONSULTING', Validators.required],
      date: [dateStr, Validators.required],
      time: ['10:00', Validators.required],
      durationMinutes: [30, Validators.required],
      requesterName: ['', [Validators.required, Validators.minLength(3)]],
      requesterEmail: ['', [Validators.required, Validators.email]],
      notes: ['']
    });
  }

  ngOnInit(): void {
    this.seo.update({
      title: 'Contacto y agenda',
      description:
        'Escríbeme, conecta en LinkedIn o agenda una cita. Full Stack Java / Angular · República Dominicana.',
      keywords:
        'contacto, agendar reunión, consultoría técnica, desarrollador full stack, freelance, República Dominicana',
      url: '/contact'
    });

    this.route.fragment.subscribe((fragment) => {
      if (fragment === 'agenda') {
        this.scrollToAgenda();
      }
    });
  }

  ngAfterViewInit(): void {
    if (this.route.snapshot.fragment === 'agenda') {
      this.scrollToAgenda();
    }
  }

  private scrollToAgenda(): void {
    setTimeout(() => {
      document.getElementById('agenda')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 0);
  }

  isInvalid(field: string): boolean {
    const ctrl = this.scheduleForm.get(field);
    return !!(ctrl && ctrl.invalid && (ctrl.dirty || ctrl.touched));
  }

  isValid(field: string): boolean {
    const ctrl = this.scheduleForm.get(field);
    return !!(ctrl && ctrl.valid && (ctrl.dirty || ctrl.touched));
  }

  onScheduleSubmit(): void {
    this.scheduleForm.markAllAsTouched();
    if (this.scheduleForm.invalid) return;

    this.isSubmitting = true;
    this.successMessage = '';
    this.errorMessage = '';

    const v = this.scheduleForm.value;
    const combined = `${v.date}T${v.time}:00`;
    const start = new Date(combined);
    const payload = {
      meetingType: v.meetingType,
      startDateTime: start.toISOString(),
      durationMinutes: Number(v.durationMinutes),
      requesterName: v.requesterName,
      requesterEmail: v.requesterEmail,
      notes: v.notes?.trim() ? v.notes.trim() : undefined
    };

    this.http.post<{ message: string }>(this.meetingsApiUrl, payload).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        this.successMessage = res.message ?? '¡Solicitud registrada correctamente!';
        this.scheduleForm.patchValue({ notes: '' });
      },
      error: (err: HttpErrorResponse) => {
        this.isSubmitting = false;
        const msg = this.extractErrorMessage(err);
        const safeMsg = this.mapServerMessageForUser(msg);
        if (err.status === 429) {
          this.errorMessage = 'Demasiadas solicitudes. Espera un minuto e inténtalo de nuevo.';
        } else if (err.status === 0 || err.status === 503) {
          this.errorMessage =
            safeMsg ||
            'El servicio de calendario no está disponible en este momento. Intenta más tarde o escribe a hola@elgarcia.org.';
        } else if (err.status === 409) {
          this.errorMessage = safeMsg || 'Ese horario ya no está disponible. Elige otra fecha u hora.';
        } else if (err.status >= 500) {
          this.errorMessage = safeMsg || 'Error en el servidor. Intenta más tarde.';
        } else if (err.status === 400) {
          this.errorMessage = safeMsg || 'Revisa los datos del formulario e inténtalo de nuevo.';
        } else {
          this.errorMessage = safeMsg || 'No se pudo completar la solicitud.';
        }
      }
    });
  }

  private extractErrorMessage(err: HttpErrorResponse): string {
    const body = err.error;
    if (body && typeof body === 'object' && 'message' in body) {
      return String((body as { message?: string }).message ?? '');
    }
    if (body && typeof body === 'object' && 'errors' in body) {
      const errors = (body as { errors?: Record<string, string> }).errors;
      if (errors) {
        return Object.values(errors).join(' ');
      }
    }
    return '';
  }

  private mapServerMessageForUser(message: string): string {
    if (!message) {
      return '';
    }

    if (message.includes(this.calendarDisabledMessageToken)) {
      return 'La agenda no está disponible por ahora. Escríbeme a hola@elgarcia.org y coordinamos por correo.';
    }

    return message;
  }
}
