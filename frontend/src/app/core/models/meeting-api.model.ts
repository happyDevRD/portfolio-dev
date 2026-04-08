export interface ScheduleMeetingRequest {
  meetingType: 'TECH_CONSULTING' | 'JOB_INTERVIEW' | 'COLLABORATION' | 'OTHER';
  startDateTime: string;
  durationMinutes: number;
  requesterName: string;
  requesterEmail: string;
  notes?: string;
}

export interface ScheduleMeetingResponse {
  message: string;
  meetingId: number;
  googleEventId?: string;
}

export interface CancelMeetingResponse {
  message: string;
  meetingId: number;
}

export interface AvailabilityResponse {
  date: string;
  durationMinutes: number;
  slots: string[];
}
