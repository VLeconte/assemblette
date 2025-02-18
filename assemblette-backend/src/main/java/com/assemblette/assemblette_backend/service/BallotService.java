package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Ballot;

public interface BallotService {
    Ballot createBallot(Ballot ballot);

    Ballot getBallotById(String ballotId);

    List<Ballot> getAllBallots();

    Ballot updateBallot(String ballotId, Ballot ballot);

    void deleteBallotById(String ballotId);

    void addBallotsFromResourcesFolder(String folderName);
}
