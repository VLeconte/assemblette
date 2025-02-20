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

import com.assemblette.assemblette_backend.entity.Authority;
import com.assemblette.assemblette_backend.service.AuthorityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authorities")
@CrossOrigin
public class AuthorityController {

    private AuthorityService authorityService;

    // Build Add Authority REST API
    @PostMapping
    public ResponseEntity<Authority> createAuthority(@RequestBody Authority authority) {
        Authority savedAuthority = authorityService.createAuthority(authority);
        return new ResponseEntity<>(savedAuthority, HttpStatus.CREATED);
    }

    // Build Get Authority REST API
    @GetMapping("{id}")
    public ResponseEntity<Authority> getAuthorityById(@PathVariable("id") String authorityId) {
        Authority authority = authorityService.getAuthorityById(authorityId);
        return ResponseEntity.ok(authority);
    }

    // Build Get All Authoritys REST API
    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthoritys() {
        List<Authority> authoritysDto = authorityService.getAllAuthoritys();
        return ResponseEntity.ok(authoritysDto);
    }

    // Build Update Authority REST API
    @PutMapping("{id}")
    public ResponseEntity<Authority> updateAuthority(@PathVariable("id") String authorityId,
            @RequestBody Authority authority) {
        Authority updatedAuthority = authorityService.updateAuthority(authorityId, authority);
        return ResponseEntity.ok(updatedAuthority);
    }

    // Build Delete Authority REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAuthorityById(@PathVariable("id") String authorityId) {
        authorityService.deleteAuthorityById(authorityId);
        return ResponseEntity.ok("Authority deleted successfully");
    }
}
