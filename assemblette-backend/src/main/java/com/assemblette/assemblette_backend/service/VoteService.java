package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.dto.VoteDto;

public interface VoteService {
    VoteDto createVote(VoteDto voteDto);

    VoteDto getVoteById(String voteId);

    List<VoteDto> getAllVotes();

    VoteDto updateVote(String voteId, VoteDto voteDto);

    void deleteVoteById(String voteId);
}
