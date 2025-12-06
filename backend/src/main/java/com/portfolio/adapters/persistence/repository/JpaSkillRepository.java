package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSkillRepository extends JpaRepository<SkillEntity, Long> {
}
