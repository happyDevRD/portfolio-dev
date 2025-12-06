package com.portfolio.application.service;

import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.domain.repository.ExperienceRepository;
import com.portfolio.core.usecase.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public Optional<Experience> getExperienceById(Long id) {
        return experienceRepository.findById(id);
    }

    @Override
    @Transactional
    public Experience createExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    @Transactional
    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}
