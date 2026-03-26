package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Service;
import com.portfolio.core.usecase.GetServicesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Services", description = "Servicios ofrecidos")
@RequiredArgsConstructor
public class ServiceController {

    private final GetServicesUseCase getServicesUseCase;

    @GetMapping
    @Operation(summary = "Listar servicios")
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(getServicesUseCase.execute());
    }
}
