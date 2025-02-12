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
import org.springframework.web.bind.annotation.RestController;

import com.assemblette.assemblette_backend.dto.BallotDto;
import com.assemblette.assemblette_backend.service.BallotService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ballots")
@CrossOrigin
public class BallotController {

    private BallotService ballotService;

    // Build Add Ballot REST API
    @PostMapping
    public ResponseEntity<BallotDto> createBallot(@RequestBody BallotDto ballotDto) {
        BallotDto savedBallotDto = ballotService.createBallot(ballotDto);
        return new ResponseEntity<>(savedBallotDto, HttpStatus.CREATED);
    }

    // Build Get Ballot REST API
    @GetMapping("{id}")
    public ResponseEntity<BallotDto> getBallotById(@PathVariable("id") String ballotId) {
        BallotDto ballotDto = ballotService.getBallotById(ballotId);
        return ResponseEntity.ok(ballotDto);
    }

    // Build Get All Ballots REST API
    @GetMapping
    public ResponseEntity<List<BallotDto>> getAllBallots() {
        List<BallotDto> ballotsDto = ballotService.getAllBallots();
        return ResponseEntity.ok(ballotsDto);
    }

    // Build Update Ballot REST API
    @PutMapping("{id}")
    public ResponseEntity<BallotDto> updateBallot(@PathVariable("id") String ballotId,
            @RequestBody BallotDto ballotDto) {
        BallotDto updatedBallotDto = ballotService.updateBallot(ballotId, ballotDto);
        return ResponseEntity.ok(updatedBallotDto);
    }

    // Build Delete Ballot REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBallotById(@PathVariable("id") String ballotId) {
        ballotService.deleteBallotById(ballotId);
        return ResponseEntity.ok("Ballot deleted successfully");
    }
}
