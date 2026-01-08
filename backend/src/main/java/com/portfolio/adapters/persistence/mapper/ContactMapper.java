package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ContactEntity;
import com.portfolio.core.domain.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toDomain(ContactEntity entity) {
        if (entity == null) {
            return null;
        }
        return Contact.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ContactEntity toEntity(Contact domain) {
        if (domain == null) {
            return null;
        }
        return ContactEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .message(domain.getMessage())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
