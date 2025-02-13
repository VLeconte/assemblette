package com.assemblette.assemblette_backend.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.assemblette.assemblette_backend.dto.DeputyDto;
import com.assemblette.assemblette_backend.dto.DeputyJsonDto;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.exception.ResourceNotFoundException;
import com.assemblette.assemblette_backend.mapper.DeputyMapper;
import com.assemblette.assemblette_backend.repository.DeputyRepository;
import com.assemblette.assemblette_backend.service.DeputyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeputyServiceImpl implements DeputyService {

    private DeputyRepository deputyRepository;

    @Override
    public DeputyDto createDeputy(DeputyDto deputyDto) {

        Deputy deputy = DeputyMapper.mapToDeputy((deputyDto));
        Deputy savedDeputy = deputyRepository.save(deputy);
        return DeputyMapper.mapToDeputyDto(savedDeputy);
    }

    @Override
    public DeputyDto getDeputyById(Long deputyId) {
        Deputy deputy = deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));
        return DeputyMapper.mapToDeputyDto(deputy);
    }

    @Override
    public List<DeputyDto> getAllDeputies() {
        List<Deputy> deputies = deputyRepository.findAll();
        return deputies.stream().map((deputy) -> DeputyMapper.mapToDeputyDto(deputy)).collect(Collectors.toList());
    }

    @Override
    public DeputyDto updateDeputy(Long deputyId, DeputyDto deputyDto) {
        Deputy deputy = deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));

        deputy.setFirstName(deputyDto.getFirstName());
        deputy.setLastName(deputyDto.getLastName());
        deputy.setRegion(deputyDto.getRegion());
        deputy.setDepartment(deputyDto.getDepartment());
        deputy.setConstituencyNumber(deputyDto.getConstituencyNumber());
        deputy.setProfession(deputyDto.getProfession());
        deputy.setPoliticalGroupFull(deputyDto.getPoliticalGroupFull());
        deputy.setPoliticalGroupAbbreviated(deputyDto.getPoliticalGroupAbbreviated());

        Deputy updatedDeputy = deputyRepository.save(deputy);
        return DeputyMapper.mapToDeputyDto(updatedDeputy);
    }

    @Override
    public void deleteDeputyById(Long deputyId) {
        deputyRepository.findById(deputyId)
                .orElseThrow(() -> new ResourceNotFoundException("Deputy does not exist with given id : " + deputyId));
        deputyRepository.deleteById(deputyId);
    }

    @Override
    public void addDeputiesFromResourcesFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            InputStream inputStream = resource.getInputStream();

            List<DeputyJsonDto> deputyDtos = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<DeputyJsonDto>>() {
                    });

            List<Deputy> deputies = deputyDtos.stream()
                    .map(DeputyMapper::mapToDeputy)
                    .collect(Collectors.toList());

            deputyRepository.saveAll(deputies);

            System.out.println("Deputies successfully added from file: " + fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add deputies from JSON file: " + e.getMessage());
        }
    }

}
