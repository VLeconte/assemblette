package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.dto.DeputyDto;

public interface DeputyService {
    DeputyDto createDeputy(DeputyDto deputyDto);

    DeputyDto getDeputyById(Long deputyId);

    List<DeputyDto> getAllDeputies();

    DeputyDto updateDeputy(Long deputyId, DeputyDto deputyDto);

    void deleteDeputyById(Long deputyId);

    void addDeputiesFromResourcesFile(String pathString);
}
