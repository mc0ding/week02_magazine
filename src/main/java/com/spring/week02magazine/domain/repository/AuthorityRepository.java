package com.spring.week02magazine.domain.repository;

import com.spring.week02magazine.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
