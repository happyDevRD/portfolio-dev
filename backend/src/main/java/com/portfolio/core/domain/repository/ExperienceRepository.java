package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Experience;
import java.util.List;
import java.util.Optional;

public interface ExperienceRepository {
    List<Experience> findAll();

    Optional<Experience> findById(Long id);

    Experience save(Experience experience);

    void deleteById(Long id);
}
