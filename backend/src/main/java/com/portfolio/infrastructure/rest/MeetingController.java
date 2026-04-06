package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Meeting;
import com.portfolio.core.usecase.MeetingService;
import com.portfolio.infrastructure.rest.dto.MeetingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/meetings")
@Tag(name = "Meetings", description = "Agendamiento de reuniones con Google Calendar")
@RequiredArgsConstructor
@Slf4j
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    @Operation(summary = "Solicitar una reunión en el calendario")
    public ResponseEntity<Map<String, Object>> schedule(@Valid @RequestBody MeetingRequest request) {
        Instant end = request.getStartDateTime().plus(Duration.ofMinutes(request.getDurationMinutes()));

        String notes = request.getNotes();
        if (notes != null) {
            notes = notes.trim();
            if (notes.isEmpty()) {
                notes = null;
            }
        }

        Meeting draft = Meeting.builder()
                .meetingType(request.getMeetingType())
                .startAt(request.getStartDateTime())
                .endAt(end)
                .requesterName(request.getRequesterName().trim())
                .requesterEmail(request.getRequesterEmail().trim())
                .notes(notes)
                .build();

        Meeting saved = meetingService.scheduleMeeting(draft);
        log.info("Scheduled meeting id={} type={} start={}", saved.getId(), saved.getMeetingType(), saved.getStartAt());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Reunión agendada correctamente.");
        body.put("meetingId", saved.getId());
        body.put("googleEventId", saved.getGoogleEventId());
        return ResponseEntity.ok(body);
    }
}
