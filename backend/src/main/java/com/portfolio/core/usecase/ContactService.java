package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Contact;

public interface ContactService {
    Contact sendContactMessage(Contact contact);
}
