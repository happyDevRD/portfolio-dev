package com.portfolio.infrastructure.rest.dto;

import com.portfolio.core.domain.model.MeetingType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRequest {

    @NotNull(message = "El tipo de reunión es obligatorio")
    private MeetingType meetingType;

    @NotNull(message = "La fecha y hora de inicio son obligatorias")
    private Instant startDateTime;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 15, message = "La duración mínima es 15 minutos")
    @Max(value = 180, message = "La duración máxima es 180 minutos")
    private Integer durationMinutes;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255)
    private String requesterName;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo no válido")
    @Size(max = 255)
    private String requesterEmail;

    @Size(max = 2000)
    private String notes;
}
