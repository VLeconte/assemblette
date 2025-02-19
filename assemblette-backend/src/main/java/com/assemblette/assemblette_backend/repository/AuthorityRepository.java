package com.assemblette.assemblette_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}