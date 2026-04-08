import { AfterViewInit, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { SeoService } from '../../core/services/seo.service';
import { MeetingApiService } from '../../core/services/meeting-api.service';
import { ScheduleMeetingRequest, ScheduleMeetingResponse } from '../../core/models/meeting-api.model';

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
  isCancelling = false;
  isLoadingAvailability = false;
  successMessage = '';
  errorMessage = '';
  availabilityMessage = '';
  lastMeetingId: number | null = null;
  lastGoogleEventId = '';

  readonly meetingTypes: { value: string; label: string }[] = [
    { value: 'TECH_CONSULTING', label: 'Consultoría técnica' },
    { value: 'JOB_INTERVIEW', label: 'Entrevista laboral' },
    { value: 'COLLABORATION', label: 'Colaboración u oportunidad' },
    { value: 'OTHER', label: 'Otro' }
  ];

  readonly meetingDurationMinutes = 60;
  readonly slotMinutes = 60;
  readonly fixedSlotTimes = ['09:00', '10:00', '11:00', '14:00', '15:00', '16:00'];
  readonly maxNotesLength = 2000;
  slotOptions: { time: string; available: boolean }[] = [];

  /** Mensajes del backend cuando la agenda no está configurada (deshabilitada). */
  private readonly calendarDisabledPatterns = [
    'app.google.calendar',
    'GOOGLE_CALENDAR_ENABLED',
    'Google Calendar no está habilitada'
  ] as const;

  constructor(
    private fb: FormBuilder,
    private meetingApi: MeetingApiService,
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
      durationMinutes: [this.meetingDurationMinutes, Validators.required],
      requesterName: ['', [Validators.required, Validators.minLength(3)]],
      requesterEmail: ['', [Validators.required, Validators.email]],
      notes: ['']
    });

    this.refreshAvailability();
    this.scheduleForm.get('date')?.valueChanges.subscribe(() => {
      this.refreshAvailability();
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
    this.lastMeetingId = null;
    this.lastGoogleEventId = '';

    const v = this.scheduleForm.value;
    const combined = `${v.date}T${v.time}:00`;
    const start = new Date(combined);
    const payload: ScheduleMeetingRequest = {
      meetingType: v.meetingType,
      startDateTime: start.toISOString(),
      durationMinutes: this.meetingDurationMinutes,
      requesterName: v.requesterName,
      requesterEmail: v.requesterEmail,
      notes: v.notes?.trim() ? v.notes.trim() : undefined
    };

    this.meetingApi.scheduleMeeting(payload).subscribe({
      next: (res: ScheduleMeetingResponse) => {
        this.isSubmitting = false;
        this.successMessage = res.message ?? '¡Solicitud registrada correctamente!';
        this.lastMeetingId = res.meetingId ?? null;
        this.lastGoogleEventId = res.googleEventId ?? '';
        this.scheduleForm.patchValue({ notes: '' });
        this.refreshAvailability();
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

  onCancelLastMeeting(): void {
    if (!this.lastMeetingId || this.isCancelling) {
      return;
    }
    this.isCancelling = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.meetingApi.cancelMeeting(this.lastMeetingId).subscribe({
      next: (res) => {
        this.isCancelling = false;
        this.successMessage = res.message ?? 'Reunión cancelada correctamente.';
        this.lastMeetingId = null;
        this.lastGoogleEventId = '';
        this.refreshAvailability();
      },
      error: (err: HttpErrorResponse) => {
        this.isCancelling = false;
        const msg = this.extractErrorMessage(err);
        this.errorMessage = this.mapServerMessageForUser(msg) || 'No se pudo cancelar la reunión.';
      }
    });
  }

  private refreshAvailability(): void {
    const date = String(this.scheduleForm.get('date')?.value ?? '');
    const durationMinutes = this.meetingDurationMinutes;
    const timeCtrl = this.scheduleForm.get('time');
    this.scheduleForm.patchValue({ durationMinutes }, { emitEvent: false });
    if (!date) {
      this.slotOptions = [];
      this.availabilityMessage = '';
      timeCtrl?.disable({ emitEvent: false });
      this.ensureSelectedTimeIsValid();
      return;
    }
    this.isLoadingAvailability = true;
    this.availabilityMessage = '';
    timeCtrl?.disable({ emitEvent: false });
    const allSlots = this.fixedSlotTimes;
    this.meetingApi.getAvailability(date, durationMinutes).subscribe({
      next: (res) => {
        this.isLoadingAvailability = false;
        const free = new Set(Array.isArray(res.slots) ? res.slots : []);
        this.slotOptions = allSlots.map((time) => ({ time, available: free.has(time) }));
        if (!this.slotOptions.some((s) => s.available)) {
          this.availabilityMessage = 'No hay bloques disponibles para esa fecha y duración.';
        }
        if (this.slotOptions.some((s) => s.available)) {
          timeCtrl?.enable({ emitEvent: false });
        } else {
          timeCtrl?.disable({ emitEvent: false });
        }
        this.ensureSelectedTimeIsValid();
      },
      error: (err: HttpErrorResponse) => {
        this.isLoadingAvailability = false;
        this.slotOptions = [];
        const msg = this.extractErrorMessage(err);
        if (msg.includes("Request method 'GET' is not supported")) {
          this.availabilityMessage = 'Reinicia el backend para cargar el endpoint de disponibilidad.';
        } else {
          this.availabilityMessage =
            this.mapServerMessageForUser(msg) || 'No se pudo cargar la disponibilidad en tiempo real.';
        }
        timeCtrl?.disable({ emitEvent: false });
        this.ensureSelectedTimeIsValid();
      }
    });
  }

  private ensureSelectedTimeIsValid(): void {
    const current = String(this.scheduleForm.get('time')?.value ?? '');
    const available = this.slotOptions.filter((s) => s.available).map((s) => s.time);
    if (available.length === 0) {
      this.scheduleForm.patchValue({ time: '' }, { emitEvent: false });
      return;
    }
    if (!available.includes(current)) {
      this.scheduleForm.patchValue({ time: available[0] }, { emitEvent: false });
    }
  }

  get availableSlotCount(): number {
    return this.slotOptions.filter((s) => s.available).length;
  }

  get selectedMeetingTypeLabel(): string {
    const selected = String(this.scheduleForm.get('meetingType')?.value ?? '');
    return this.meetingTypes.find((t) => t.value === selected)?.label ?? 'Reunión';
  }

  get selectedSlotSummary(): string {
    const date = String(this.scheduleForm.get('date')?.value ?? '');
    const time = String(this.scheduleForm.get('time')?.value ?? '');
    const duration = Number(this.scheduleForm.get('durationMinutes')?.value ?? 0);
    if (!date || !time || !duration) {
      return '';
    }
    return `${date} · ${time} · ${duration} min`;
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

    if (this.calendarDisabledPatterns.some((p) => message.includes(p))) {
      return 'La agenda no está disponible por ahora. Escríbeme a hola@elgarcia.org y coordinamos por correo.';
    }

    if (message.includes('bloques de') || message.includes('múltiplo de')) {
      return 'El horario disponible está segmentado en bloques de 1 hora.';
    }

    return message;
  }
}
