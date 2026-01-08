package com.portfolio.adapters.persistence.repository;

import com.portfolio.adapters.persistence.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {
}
