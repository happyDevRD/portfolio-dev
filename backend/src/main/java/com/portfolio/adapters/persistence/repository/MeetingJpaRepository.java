package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.MeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJpaRepository extends JpaRepository<MeetingEntity, Long> {
}
