package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.BallotDto;
import com.assemblette.assemblette_backend.entity.Ballot;

public class BallotMapper {
    public static BallotDto mapToBallotDto(Ballot ballot) {
        return new BallotDto(
                ballot.getId(),
                ballot.getTitre(),
                ballot.getDateScrutin());
    }

    public static Ballot mapToBallot(BallotDto ballotDto) {
        return new Ballot(
                ballotDto.getId(),
                ballotDto.getTitre(),
                ballotDto.getDateScrutin());
    }
}
