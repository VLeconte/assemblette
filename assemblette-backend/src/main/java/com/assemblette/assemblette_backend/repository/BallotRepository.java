package com.assemblette.assemblette_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Ballot;

public interface BallotRepository extends JpaRepository<Ballot, String> {
}
