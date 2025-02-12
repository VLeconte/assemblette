package com.assemblette.assemblette_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.BallotDto;
import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.BallotMapper;
import com.assemblette.assemblette_backend.repository.BallotRepository;
import com.assemblette.assemblette_backend.service.BallotService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BallotServiceImpl implements BallotService {

    private BallotRepository ballotRepository;

    @Override
    public BallotDto createBallot(BallotDto ballotDto) {

        Ballot ballot = BallotMapper.mapToBallot((ballotDto));
        Ballot savedBallot = ballotRepository.save(ballot);
        return BallotMapper.mapToBallotDto(savedBallot);
    }

    @Override
    public BallotDto getBallotById(String ballotId) {
        Ballot ballot = ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));
        return BallotMapper.mapToBallotDto(ballot);
    }

    @Override
    public List<BallotDto> getAllBallots() {
        List<Ballot> ballots = ballotRepository.findAll();
        return ballots.stream().map((ballot) -> BallotMapper.mapToBallotDto(ballot)).collect(Collectors.toList());
    }

    @Override
    public BallotDto updateBallot(String ballotId, BallotDto ballotDto) {
        Ballot ballot = ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));

        ballot.setTitre(ballotDto.getTitre());
        ballot.setDateScrutin(ballotDto.getDateScrutin());

        Ballot updatedBallot = ballotRepository.save(ballot);
        return BallotMapper.mapToBallotDto(updatedBallot);
    }

    @Override
    public void deleteBallotById(String ballotId) {
        ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));
        ballotRepository.deleteById(ballotId);
    }

}
