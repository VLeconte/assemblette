package com.assemblette.assemblette_backend.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.entity.Vote;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.repository.VoteRepository;
import com.assemblette.assemblette_backend.service.VoteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private VoteRepository voteRepository;

    @Override
    public Vote createVote(Vote vote) {

        Vote savedVote = voteRepository.save(vote);
        return savedVote;
    }

    @Override
    public Vote getVoteById(String voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));
        return vote;
    }

    @Override
    public List<Vote> getAllVotes() {
        List<Vote> votes = voteRepository.findAll();
        return votes;
    }

    @Override
    public Vote updateVote(String voteId, Vote vote) {
        Vote currentVote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));

        currentVote.setBallot(vote.getBallot());
        currentVote.setDeputy(vote.getDeputy());
        currentVote.setState(vote.getState());

        Vote updatedVote = voteRepository.save(vote);
        return updatedVote;
    }

    @Override
    public void deleteVoteById(String voteId) {
        voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote does not exist with given id : " + voteId));
        voteRepository.deleteById(voteId);
    }

    @Override
    public void addVotesFromResourcesFolder(String folderName) {
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
                Map<String, String> statePlurialToSingular = Map.of(
                        "nonVotants", "nonVotant",
                        "pours", "pour",
                        "contres", "contre",
                        "abstentions", "abstention");

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
                        voteState = statePlurialToSingular.get(entry.getKey());

                        if (!entry.getValue().isNull()) {
                            if (entry.getValue().get("votant").isArray()) {
                                for (JsonNode deputy : entry.getValue().get("votant")) {
                                    votes.add(Vote
                                            .builder()
                                            .ballot(Ballot
                                                    .builder()
                                                    .id(ballotId)
                                                    .build())
                                            .deputy(Deputy.builder()
                                                    .id(deputy.get("acteurRef").asText())
                                                    .build())
                                            .state(voteState).build());
                                }
                            } else {
                                JsonNode deputy = entry.getValue().get("votant");
                                votes.add(Vote
                                        .builder()
                                        .ballot(Ballot
                                                .builder()
                                                .id(ballotId)
                                                .build())
                                        .deputy(Deputy.builder()
                                                .id(deputy.get("acteurRef").asText())
                                                .build())
                                        .state(voteState).build());
                            }
                        }
                    }
                }
                voteRepository.saveAll(votes);
                System.out.println("Votes successfully added from file: " + ballotFile.getFilename());
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to add votes from JSON file: " + ballotFile.getFilename() + e.getMessage());
            }
        }
    }
}
