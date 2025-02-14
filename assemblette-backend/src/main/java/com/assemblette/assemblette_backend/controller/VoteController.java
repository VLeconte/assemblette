package com.assemblette.assemblette_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assemblette.assemblette_backend.dto.VoteDto;
import com.assemblette.assemblette_backend.service.VoteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VoteController {

    private VoteService voteService;

    // Build Add Vote REST API
    @PostMapping
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDto voteDto) {
        VoteDto savedVoteDto = voteService.createVote(voteDto);
        return new ResponseEntity<>(savedVoteDto, HttpStatus.CREATED);
    }

    // Build Get Vote REST API
    @GetMapping("{id}")
    public ResponseEntity<VoteDto> getVoteById(@PathVariable("id") String voteId) {
        VoteDto voteDto = voteService.getVoteById(voteId);
        return ResponseEntity.ok(voteDto);
    }

    // Build Get All Votes REST API
    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllVotes(
            @RequestParam(required = false) String ballotId,
            @RequestParam(required = false) String state) {
        List<VoteDto> votesDto = voteService.getAllVotes(ballotId, state);
        return ResponseEntity.ok(votesDto);
    }

    // Build Update Vote REST API
    @PutMapping("{id}")
    public ResponseEntity<VoteDto> updateVote(@PathVariable("id") String voteId,
            @RequestBody VoteDto voteDto) {
        VoteDto updatedVoteDto = voteService.updateVote(voteId, voteDto);
        return ResponseEntity.ok(updatedVoteDto);
    }

    // Build Delete Vote REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVoteById(@PathVariable("id") String voteId) {
        voteService.deleteVoteById(voteId);
        return ResponseEntity.ok("Vote deleted successfully");
    }

}
