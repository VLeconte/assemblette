package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.BallotJson;
import com.assemblette.assemblette_backend.entity.Ballot;

public class BallotMapper {
    public static Ballot mapToBallot(BallotJson ballotJson) {
        return new Ballot(
                ballotJson.getId(),
                ballotJson.getTitle(),
                ballotJson.getBallotDate());
    }
}
