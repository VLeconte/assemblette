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

import com.assemblette.assemblette_backend.entity.Mandate;
import com.assemblette.assemblette_backend.service.MandateService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/mandates")
@CrossOrigin
public class MandateController {

    private MandateService mandateService;

    // Build Add Mandate REST API
    @PostMapping
    public ResponseEntity<Mandate> createMandate(@RequestBody Mandate mandate) {
        Mandate savedMandate = mandateService.createMandate(mandate);
        return new ResponseEntity<>(savedMandate, HttpStatus.CREATED);
    }

    // Build Get Mandate REST API
    @GetMapping("{id}")
    public ResponseEntity<Mandate> getMandateById(@PathVariable("id") String mandateId) {
        Mandate mandate = mandateService.getMandateById(mandateId);
        return ResponseEntity.ok(mandate);
    }

    // Build Get All Mandates REST API
    @GetMapping
    public ResponseEntity<List<Mandate>> getAllMandates() {
        List<Mandate> mandatesDto = mandateService.getAllMandates();
        return ResponseEntity.ok(mandatesDto);
    }

    // Build Update Mandate REST API
    @PutMapping("{id}")
    public ResponseEntity<Mandate> updateMandate(@PathVariable("id") String mandateId,
            @RequestBody Mandate mandate) {
        Mandate updatedMandate = mandateService.updateMandate(mandateId, mandate);
        return ResponseEntity.ok(updatedMandate);
    }

    // Build Delete Mandate REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMandateById(@PathVariable("id") String mandateId) {
        mandateService.deleteMandateById(mandateId);
        return ResponseEntity.ok("Mandate deleted successfully");
    }
}
