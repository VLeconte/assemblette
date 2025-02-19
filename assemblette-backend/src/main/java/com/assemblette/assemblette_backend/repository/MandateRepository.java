package com.assemblette.assemblette_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Mandate;

public interface MandateRepository extends JpaRepository<Mandate, String> {

}