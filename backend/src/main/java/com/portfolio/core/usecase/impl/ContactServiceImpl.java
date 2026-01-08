package com.portfolio.core.usecase.impl;

import com.portfolio.core.domain.model.Contact;
import com.portfolio.core.domain.repository.ContactRepository;
import com.portfolio.core.usecase.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public Contact sendContactMessage(Contact contact) {
        return contactRepository.save(contact);
    }
}
