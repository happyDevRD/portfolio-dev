package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ServiceEntity;
import com.portfolio.core.domain.model.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service toDomain(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }
        return Service.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .iconUrl(entity.getIconUrl())
                .build();
    }

    public ServiceEntity toEntity(Service domain) {
        if (domain == null) {
            return null;
        }
        return ServiceEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .iconUrl(domain.getIconUrl())
                .build();
    }
}
