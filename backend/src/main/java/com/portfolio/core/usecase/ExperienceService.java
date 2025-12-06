package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Experience;
import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    List<Experience> getAllExperiences();

    Optional<Experience> getExperienceById(Long id);

    Experience createExperience(Experience experience);

    void deleteExperience(Long id);
}
