import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import {
  AvailabilityResponse,
  CancelMeetingResponse,
  ScheduleMeetingRequest,
  ScheduleMeetingResponse
} from '../models/meeting-api.model';

@Injectable({
  providedIn: 'root'
})
export class MeetingApiService {
  private readonly meetingsApiUrl = `${environment.apiUrl}/meetings`;

  constructor(private http: HttpClient) {}

  scheduleMeeting(payload: ScheduleMeetingRequest): Observable<ScheduleMeetingResponse> {
    return this.http.post<ScheduleMeetingResponse>(this.meetingsApiUrl, payload);
  }

  cancelMeeting(meetingId: number): Observable<CancelMeetingResponse> {
    return this.http.delete<CancelMeetingResponse>(`${this.meetingsApiUrl}/${meetingId}`);
  }

  getAvailability(date: string, durationMinutes: number): Observable<AvailabilityResponse> {
    return this.http.get<AvailabilityResponse>(
      `${this.meetingsApiUrl}/availability`,
      { params: { date, durationMinutes: String(durationMinutes) } }
    );
  }
}
