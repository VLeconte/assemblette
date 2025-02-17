package com.assemblette.assemblette_backend.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.BallotDto;
import com.assemblette.assemblette_backend.dto.BallotJsonDto;
import com.assemblette.assemblette_backend.dto.VoteDto;
import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.entity.Vote;
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

        ballot.setTitle(ballotDto.getTitle());
        ballot.setBallotDate(ballotDto.getBallotDate());

        Ballot updatedBallot = ballotRepository.save(ballot);
        return BallotMapper.mapToBallotDto(updatedBallot);
    }

    @Override
    public void deleteBallotById(String ballotId) {
        ballotRepository.findById(ballotId)
                .orElseThrow(() -> new ResourceNotFoundException("Ballot does not exist with given id : " + ballotId));
        ballotRepository.deleteById(ballotId);
    }

    @Override
    public void addBallotsFromResourcesFile(String folderName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource[] ballotsResources;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            ballotsResources = resolver.getResources(folderName + "/*.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve ballots json files : " + e.getMessage());
        }

        for (Resource ballotFile : ballotsResources) {
            try {
                InputStream inputStream = ballotFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);
                BallotJsonDto ballotJsonDto = objectMapper.treeToValue(rootNode.get("scrutin"), BallotJsonDto.class);

                ballotRepository.save(BallotMapper.mapToBallot(ballotJsonDto));

                System.out.println("Ballot successfully added from file: " + ballotFile.getFilename());
            } catch (Exception e) {
                throw new RuntimeException("Failed to add ballot from JSON file: " + e.getMessage());
            }
        }

        for (Resource ballotFile : ballotsResources) {
            try {
                InputStream inputStream = ballotFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);

                List<Vote> votes = new ArrayList<Vote>();
                String ballotId = rootNode.get("scrutin").get("uid").asText();
                String voteState;
                for (JsonNode groupNode : rootNode
                        .get("scrutin")
                        .get("ventilationVotes")
                        .get("organe")
                        .get("groupes")
                        .get("groupe")) {
                    for (Iterator<Entry<String, JsonNode>> it = groupNode
                            .get("vote")
                            .get("decompteNominatif").fields(); it.hasNext();) {
                        Entry<String, JsonNode> entry = it.next();
                        voteState = entry.getKey();

                        if (entry.getValue() != null) {
                            for (JsonNode deputy : entry.getValue().get("votant")) {
                                votes.add(Vote
                                        .builder()
                                        .ballot(Ballot
                                                .builder()
                                                .id(ballotId)
                                                .build())
                                        .deputy(Deputy.builder()
                                                .id(Long.parseLong(deputy.get("acteurRef").asText().substring(2)))
                                                .build())
                                        .state(voteState).build());
                            }
                        }
                    }
                }
                // BallotJsonDto ballotJsonDto =
                // objectMapper.treeToValue(rootNode.get("scrutin"), BallotJsonDto.class);

                // ballotRepository.save(BallotMapper.mapToBallot(ballotJsonDto));

                // System.out.println("Ballot successfully added from file: " +
                // ballotFile.getFilename());
            } catch (Exception e) {
                throw new RuntimeException("Failed to add ballot from JSON file: " + e.getMessage());
            }
        }
    }

}
