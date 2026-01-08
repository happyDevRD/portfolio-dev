package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.TestimonialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialJpaRepository extends JpaRepository<TestimonialEntity, Long> {
}
