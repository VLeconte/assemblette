package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.VoteDto;
import com.assemblette.assemblette_backend.entity.Vote;

public class VoteMapper {
    public static VoteDto mapToVoteDto(Vote vote) {
        return new VoteDto(
                vote.getId(),
                vote.getBallot(),
                vote.getDeputy(),
                vote.getState());
    }

    public static Vote mapToVote(VoteDto voteDto) {
        return new Vote(
                voteDto.getId(),
                voteDto.getBallot(),
                voteDto.getDeputy(),
                voteDto.getState());
    }
}