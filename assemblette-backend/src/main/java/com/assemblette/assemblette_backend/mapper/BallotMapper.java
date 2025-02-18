package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.BallotJsonDto;
import com.assemblette.assemblette_backend.entity.Ballot;

public class BallotMapper {
    public static Ballot mapToBallot(BallotJsonDto ballotJsonDto) {
        return new Ballot(
                ballotJsonDto.getId(),
                ballotJsonDto.getTitle(),
                ballotJsonDto.getBallotDate());
    }
}
