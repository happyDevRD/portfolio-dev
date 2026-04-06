package com.portfolio.adapters.persistence.entity;

import com.portfolio.core.domain.model.MeetingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "meetings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type", nullable = false, length = 64)
    private MeetingType meetingType;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at", nullable = false)
    private Instant endAt;

    @Column(name = "requester_name", nullable = false)
    private String requesterName;

    @Column(name = "requester_email", nullable = false)
    private String requesterEmail;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "google_event_id", length = 512)
    private String googleEventId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
