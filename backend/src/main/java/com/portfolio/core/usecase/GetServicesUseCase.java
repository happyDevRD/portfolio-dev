package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Service;
import java.util.List;

public interface GetServicesUseCase {
    List<Service> execute();

    Service createService(Service service);
}
