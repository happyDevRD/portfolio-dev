-- Historial de reuniones agendadas (Google Calendar + persistencia local)

CREATE TABLE meetings (
    id BIGSERIAL PRIMARY KEY,
    meeting_type VARCHAR(64) NOT NULL,
    start_at TIMESTAMPTZ NOT NULL,
    end_at TIMESTAMPTZ NOT NULL,
    requester_name VARCHAR(255) NOT NULL,
    requester_email VARCHAR(255) NOT NULL,
    notes TEXT,
    google_event_id VARCHAR(512),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_meetings_start_at ON meetings (start_at);
