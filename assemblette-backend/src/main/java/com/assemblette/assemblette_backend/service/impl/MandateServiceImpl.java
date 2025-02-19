package com.assemblette.assemblette_backend.service.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.BallotJson;
import com.assemblette.assemblette_backend.dto.MandateJson;
import com.assemblette.assemblette_backend.entity.Authority;
import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.entity.Mandate;
import com.assemblette.assemblette_backend.entity.Vote;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.MandateMapper;
import com.assemblette.assemblette_backend.repository.MandateRepository;
import com.assemblette.assemblette_backend.service.MandateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MandateServiceImpl implements MandateService {

    private MandateRepository mandateRepository;

    @Override
    public Mandate createMandate(Mandate mandate) {

        Mandate savedMandate = mandateRepository.save(mandate);
        return savedMandate;
    }

    @Override
    public Mandate getMandateById(String mandateId) {
        Mandate mandate = mandateRepository.findById(mandateId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Mandate does not exist with given id : " + mandateId));
        return mandate;
    }

    @Override
    public List<Mandate> getAllMandates() {
        List<Mandate> mandates = mandateRepository.findAll();
        return mandates;
    }

    @Override
    public Mandate updateMandate(String mandateId, Mandate mandate) {
        Mandate currentMandate = mandateRepository.findById(mandateId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Mandate does not exist with given id : " + mandateId));

        currentMandate.setAuthority(mandate.getAuthority());
        currentMandate.setDeputy(mandate.getDeputy());
        currentMandate.setStartDate(mandate.getStartDate());
        currentMandate.setEndDate(mandate.getEndDate());

        Mandate updatedMandate = mandateRepository.save(mandate);
        return updatedMandate;
    }

    @Override
    public void deleteMandateById(String mandateId) {
        mandateRepository.findById(mandateId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Mandate does not exist with given id : " + mandateId));
        mandateRepository.deleteById(mandateId);
    }

    @Override
    public void addMandatesFromResourcesFolder(String folderName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource[] deputiesResources;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            deputiesResources = resolver.getResources(folderName + File.separator + "*.json");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve deputies json files : " + e.getMessage());
        }
        try {
            List<Mandate> mandates = new ArrayList<Mandate>();
            for (Resource deputyFile : deputiesResources) {
                InputStream inputStream = deputyFile.getInputStream();

                JsonNode rootNode = objectMapper.readTree(inputStream);
                JsonNode mandat = rootNode
                        .get("acteur")
                        .get("mandats")
                        .get("mandat");
                if (!mandat.isArray()) {
                    mandat = JsonNodeFactory.instance.arrayNode().add(mandat);
                }
                for (JsonNode mandatNode : mandat) {
                    if (mandatNode.get("typeOrgane").asText().equals("GP")
                            || mandatNode.get("typeOrgane").asText().equals("ASSEMBLEE")) {
                        Mandate newMandate = MandateMapper
                                .mapToMandate(objectMapper.treeToValue(mandatNode, MandateJson.class));
                        mandates.add(newMandate);
                    }
                }
            }
            mandateRepository.saveAll(mandates);
            System.out.println(mandates.size() + " mandates successfully added from folder: " + folderName);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to add mandates to database from folder: " + folderName + e.getMessage()
                            + e.getStackTrace()[0]);
        }

    }
}
