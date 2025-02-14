package com.assemblette.assemblette_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.VoteDto;
import com.assemblette.assemblette_backend.entity.Vote;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.VoteMapper;
import com.assemblette.assemblette_backend.repository.VoteRepository;
import com.assemblette.assemblette_backend.service.VoteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private VoteRepository voteRepository;

    @Override
    public VoteDto createVote(VoteDto voteDto) {

        Vote vote = VoteMapper.mapToVote((voteDto));
        Vote savedVote = voteRepository.save(vote);
        return VoteMapper.mapToVoteDto(savedVote);
    }

    @Override
    public VoteDto getVoteById(String voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));
        return VoteMapper.mapToVoteDto(vote);
    }

    @Override
    public List<VoteDto> getAllVotes(String ballotId, String state) {
        List<Vote> votes = voteRepository.findAll();
        return votes.stream().map((vote) -> VoteMapper.mapToVoteDto(vote)).collect(Collectors.toList());
    }

    @Override
    public VoteDto updateVote(String voteId, VoteDto voteDto) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));

        vote.setBallot(voteDto.getBallot());
        vote.setDeputy(voteDto.getDeputy());
        vote.setState(voteDto.getState());

        Vote updatedVote = voteRepository.save(vote);
        return VoteMapper.mapToVoteDto(updatedVote);
    }

    @Override
    public void deleteVoteById(String voteId) {
        voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));
        voteRepository.deleteById(voteId);
    }
}
