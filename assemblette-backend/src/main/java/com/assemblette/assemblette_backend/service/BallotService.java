package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.dto.BallotDto;

public interface BallotService {
    BallotDto createBallot(BallotDto ballotDto);

    BallotDto getBallotById(String ballotId);

    List<BallotDto> getAllBallots();

    BallotDto updateBallot(String ballotId, BallotDto ballotDto);

    void deleteBallotById(String ballotId);
}
