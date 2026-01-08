package com.portfolio.core.usecase.impl;

import com.portfolio.core.domain.model.Service;
import com.portfolio.core.domain.repository.ServiceRepository;
import com.portfolio.core.usecase.GetServicesUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetServicesUseCaseImpl implements GetServicesUseCase {
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> execute() {
        return serviceRepository.findAll();
    }

    @Override
    public Service createService(Service service) {
        return serviceRepository.save(service);
    }
}
