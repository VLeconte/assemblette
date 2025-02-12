package com.assemblette.assemblette_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Deputy;

public interface DeputyRepository extends JpaRepository<Deputy, Long> {
}
