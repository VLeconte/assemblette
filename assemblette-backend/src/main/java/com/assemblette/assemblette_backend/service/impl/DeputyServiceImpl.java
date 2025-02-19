package com.assemblette.assemblette_backend.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.entity.Vote;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.repository.DeputyRepository;
import com.assemblette.assemblette_backend.service.DeputyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeputyServiceImpl implements DeputyService {

    private DeputyRepository deputyRepository;

    @Override
    public Deputy createDeputy(Deputy deputy) {

        Deputy savedDeputy = deputyRepository.save(deputy);
        return savedDeputy;
    }

    @Override
    public Deputy getDeputyById(String deputyId) {
        Deputy deputy = deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));
        return deputy;
    }

    @Override
    public List<Deputy> getAllDeputies() {
        List<Deputy> deputies = deputyRepository.findAll();
        return deputies;
    }

    @Override
    public Deputy updateDeputy(String deputyId, Deputy deputy) {
        Deputy currentDeputy = deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));

        currentDeputy.setFirstName(deputy.getFirstName());
        currentDeputy.setLastName(deputy.getLastName());
        currentDeputy.setProfession(deputy.getProfession());

        Deputy updatedDeputy = deputyRepository.save(currentDeputy);
        return updatedDeputy;
    }

    @Override
    public void deleteDeputyById(String deputyId) {
        deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));
        deputyRepository.deleteById(deputyId);
    }

    @Override
    public void addDeputiesFromResourcesFolder(String folderName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource[] deputiesResources;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            deputiesResources = resolver.getResources(folderName + File.separator + "*.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve deputies json files : " + e.getMessage());
        }
        try {
            List<Deputy> deputies = new ArrayList<Deputy>();
            for (Resource deputyFile : deputiesResources) {

                InputStream inputStream = deputyFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);

                String deputyId = rootNode.get("acteur").get("uid").get("#text").asText();
                JsonNode etatCivil = rootNode.get("acteur").get("etatCivil");
                JsonNode professsion = rootNode.get("acteur").get("profession");

                Deputy deputy = Deputy.builder()
                        .id(deputyId)
                        .firstName(etatCivil.get("ident").get("prenom").asText())
                        .lastName(etatCivil.get("ident").get("nom").asText())
                        .profession(professsion.get("libelleCourant").asText())
                        .build();
                deputies.add(deputy);
            }
            deputyRepository.saveAll(deputies);
            System.out.println(deputies.size() + " deputies successfully added from folder: " + folderName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add deputies from JSON fodler: " + folderName + e.getMessage() + e.getStackTrace()[0]);
        }

    }
}
