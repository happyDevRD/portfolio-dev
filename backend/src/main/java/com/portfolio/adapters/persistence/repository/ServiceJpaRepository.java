package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, Long> {
}
