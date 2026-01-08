package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Service;
import java.util.List;

public interface ServiceRepository {
    List<Service> findAll();

    Service save(Service service);
}
