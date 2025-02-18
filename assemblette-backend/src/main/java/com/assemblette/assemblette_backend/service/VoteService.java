package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Vote;

public interface VoteService {
    Vote createVote(Vote vote);

    Vote getVoteById(String voteId);

    List<Vote> getAllVotes(String ballotId, String state);

    Vote updateVote(String voteId, Vote vote);

    void deleteVoteById(String voteId);

    void addVotesFromResourcesFolder(String folderName);
}
