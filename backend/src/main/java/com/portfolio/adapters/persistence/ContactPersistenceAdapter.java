package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.ContactMapper;
import com.portfolio.adapters.persistence.repository.ContactJpaRepository;
import com.portfolio.core.domain.model.Contact;
import com.portfolio.core.domain.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactPersistenceAdapter implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;
    private final ContactMapper contactMapper;

    @Override
    public Contact save(Contact contact) {
        var entity = contactMapper.toEntity(contact);
        if (entity == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        var savedEntity = contactJpaRepository.save(entity);
        return contactMapper.toDomain(savedEntity);
    }
}
