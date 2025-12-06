package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExperienceRepository extends JpaRepository<ExperienceEntity, Long> {
}
