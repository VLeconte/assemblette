package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.BallotDto;
import com.assemblette.assemblette_backend.dto.BallotJsonDto;
import com.assemblette.assemblette_backend.entity.Ballot;

public class BallotMapper {
    public static BallotDto mapToBallotDto(Ballot ballot) {
        return new BallotDto(
                ballot.getId(),
                ballot.getTitle(),
                ballot.getBallotDate());
    }

    public static Ballot mapToBallot(BallotDto ballotDto) {
        return new Ballot(
                ballotDto.getId(),
                ballotDto.getTitle(),
                ballotDto.getBallotDate());
    }

    public static Ballot mapToBallot(BallotJsonDto ballotJsonDto) {
        return new Ballot(
                ballotJsonDto.getId(),
                ballotJsonDto.getTitle(),
                ballotJsonDto.getBallotDate());
    }
}
