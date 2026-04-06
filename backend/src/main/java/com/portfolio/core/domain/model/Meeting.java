package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    private Long id;
    private MeetingType meetingType;
    private Instant startAt;
    private Instant endAt;
    private String requesterName;
    private String requesterEmail;
    private String notes;
    private String googleEventId;
    private Instant createdAt;
}
