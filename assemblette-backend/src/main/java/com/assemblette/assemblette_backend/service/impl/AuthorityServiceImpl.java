package com.assemblette.assemblette_backend.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.AuthorityJson;
import com.assemblette.assemblette_backend.entity.Authority;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.AuthorityMapper;
import com.assemblette.assemblette_backend.repository.AuthorityRepository;
import com.assemblette.assemblette_backend.service.AuthorityService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    @Override
    public Authority createAuthority(Authority authority) {

        Authority savedAuthority = authorityRepository.save(authority);
        return savedAuthority;
    }

    @Override
    public Authority getAuthorityById(String authorityId) {
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Authority does not exist with given id : " + authorityId));
        return authority;
    }

    @Override
    public List<Authority> getAllAuthoritys() {
        List<Authority> authoritys = authorityRepository.findAll();
        return authoritys;
    }

    @Override
    public Authority updateAuthority(String authorityId, Authority authority) {
        Authority currentAuthority = authorityRepository.findById(authorityId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Authority does not exist with given id : " + authorityId));

        currentAuthority.setLabel(authority.getLabel());
        currentAuthority.setLabelAbbreviated(authority.getLabelAbbreviated());
        currentAuthority.setAuthorityType(authority.getAuthorityType());
        currentAuthority.setAssociatedColor(authority.getAssociatedColor());

        Authority updatedAuthority = authorityRepository.save(authority);
        return updatedAuthority;
    }

    @Override
    public void deleteAuthorityById(String authorityId) {
        authorityRepository.findById(authorityId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Authority does not exist with given id : " + authorityId));
        authorityRepository.deleteById(authorityId);
    }

    @Override
    public void addAuthoritysFromResourcesFolder(String folderName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource[] authoritiesResources;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            authoritiesResources = resolver.getResources(folderName + File.separator + "*.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve authorities json files : " + e.getMessage());
        }

        try {
            List<Authority> authorities = new ArrayList<Authority>();
            for (Resource authorityFile : authoritiesResources) {

                InputStream inputStream = authorityFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);

                AuthorityJson authorityJson = objectMapper.treeToValue(rootNode.get("organe"), AuthorityJson.class);
                if (authorityJson.getAuthorityType().equals("GP")
                        || authorityJson.getAuthorityType().equals("ASSEMBLEE")) {
                    authorities.add(AuthorityMapper.mapToAuthority(authorityJson));
                }

            }
            authorityRepository.saveAll(authorities);
            System.out.println(authorities.size() + " authorities successfully added from folder: " + folderName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add authorities from folder: " + folderName + e.getMessage() + e.getStackTrace()[0]);
        }
    }
}
