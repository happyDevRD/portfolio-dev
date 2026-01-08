package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Contact;

public interface ContactRepository {
    Contact save(Contact contact);
}
