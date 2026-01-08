package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.entity.ServiceEntity;
import com.portfolio.adapters.persistence.mapper.ServiceMapper;
import com.portfolio.adapters.persistence.repository.ServiceJpaRepository;
import com.portfolio.core.domain.model.Service;
import com.portfolio.core.domain.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServicePersistenceAdapter implements ServiceRepository {
    private final ServiceJpaRepository jpaRepository;
    private final ServiceMapper mapper;

    @Override
    public List<Service> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Service save(Service service) {
        ServiceEntity entity = mapper.toEntity(service);
        if (entity == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        ServiceEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
