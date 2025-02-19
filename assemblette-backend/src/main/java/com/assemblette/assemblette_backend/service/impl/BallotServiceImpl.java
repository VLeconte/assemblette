package com.assemblette.assemblette_backend.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.BallotJson;
import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.BallotMapper;
import com.assemblette.assemblette_backend.repository.BallotRepository;
import com.assemblette.assemblette_backend.service.BallotService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BallotServiceImpl implements BallotService {

    private BallotRepository ballotRepository;

    @Override
    public Ballot createBallot(Ballot ballot) {
        Ballot savedBallot = ballotRepository.save(ballot);
        return savedBallot;
    }

    @Override
    public Ballot getBallotById(String ballotId) {
        Ballot ballot = ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));
        return ballot;
    }

    @Override
    public List<Ballot> getAllBallots() {
        List<Ballot> ballots = ballotRepository.findAll();
        return ballots;
    }

    @Override
    public Ballot updateBallot(String ballotId, Ballot ballot) {
        Ballot currentBallot = ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));

        currentBallot.setTitle(ballot.getTitle());
        currentBallot.setBallotDate(ballot.getBallotDate());

        Ballot updatedBallot = ballotRepository.save(currentBallot);
        return updatedBallot;
    }

    @Override
    public void deleteBallotById(String ballotId) {
        ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));
        ballotRepository.deleteById(ballotId);
    }

    @Override
    public void addBallotsFromResourcesFolder(String folderName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource[] ballotsResources;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            ballotsResources = resolver.getResources(folderName + File.separator + "*.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballots json files : " + e.getMessage());
        }
        try {
            List<Ballot> ballots = new ArrayList<Ballot>();
            for (Resource ballotFile : ballotsResources) {

                InputStream inputStream = ballotFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);
                BallotJson ballotJson = objectMapper.treeToValue(rootNode.get("scrutin"), BallotJson.class);
                ballots.add(BallotMapper.mapToBallot(ballotJson));
            }
            ballotRepository.saveAll(ballots);
            System.out.println(ballots.size() + " ballots successfully added from folder: " + folderName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add ballots from JSON folder: " + e.getMessage() + e.getStackTrace()[0]);
        }
    }

}
