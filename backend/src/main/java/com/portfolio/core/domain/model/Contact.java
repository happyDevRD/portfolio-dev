package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private Long id;
    private String name;
    private String email;
    private String message;
    private LocalDateTime createdAt;
}
