package com.assemblette.assemblette_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, String> {
}