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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
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

    @DeleteMapping("/{meetingId}")
    @Operation(summary = "Cancelar una reunión y eliminar su evento en Google Calendar")
    public ResponseEntity<Map<String, Object>> cancel(@PathVariable Long meetingId) {
        meetingService.cancelMeeting(meetingId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Reunión cancelada correctamente.");
        body.put("meetingId", meetingId);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/availability")
    @Operation(summary = "Consultar horarios disponibles reales en Google Calendar")
    public ResponseEntity<Map<String, Object>> availability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "30") int durationMinutes) {
        List<String> slots = meetingService.getAvailableSlots(date, durationMinutes);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("date", date.toString());
        body.put("durationMinutes", durationMinutes);
        body.put("slots", slots);
        return ResponseEntity.ok(body);
    }
}
