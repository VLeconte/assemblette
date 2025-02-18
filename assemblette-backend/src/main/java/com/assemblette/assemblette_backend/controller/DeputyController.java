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

import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.service.DeputyService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/deputies")
@CrossOrigin
public class DeputyController {

    private DeputyService deputyService;

    // Build Add Deputy REST API
    @PostMapping
    public ResponseEntity<Deputy> createDeputy(@RequestBody Deputy deputy) {
        Deputy savedDeputy = deputyService.createDeputy(deputy);
        return new ResponseEntity<>(savedDeputy, HttpStatus.CREATED);
    }

    // Build Get Deputy REST API
    @GetMapping("{id}")
    public ResponseEntity<Deputy> getDeputyById(@PathVariable("id") Long deputyId) {
        Deputy deputy = deputyService.getDeputyById(deputyId);
        return ResponseEntity.ok(deputy);
    }

    // Build Get All Deputies REST API
    @GetMapping
    public ResponseEntity<List<Deputy>> getAllDeputies() {
        List<Deputy> deputiesDto = deputyService.getAllDeputies();
        return ResponseEntity.ok(deputiesDto);
    }

    // Build Update Deputy REST API
    @PutMapping("{id}")
    public ResponseEntity<Deputy> updateDeputy(@PathVariable("id") Long deputyId, @RequestBody Deputy deputy) {
        Deputy updatedDeputy = deputyService.updateDeputy(deputyId, deputy);
        return ResponseEntity.ok(updatedDeputy);
    }

    // Build Delete Deputy REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDeputyById(@PathVariable("id") Long deputyId) {
        deputyService.deleteDeputyById(deputyId);
        return ResponseEntity.ok("Deputy deleted successfully");
    }
}
