package com.assemblette.assemblette_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assemblette.assemblette_backend.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, String> {
    List<Vote> findByBallotId(String ballotId);

    List<Vote> findByBallotIdAndState(String ballotId, String state);
}