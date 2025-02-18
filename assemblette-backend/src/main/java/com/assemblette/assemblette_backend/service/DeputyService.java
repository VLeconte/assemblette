package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Deputy;

public interface DeputyService {
    Deputy createDeputy(Deputy deputy);

    Deputy getDeputyById(Long deputyId);

    List<Deputy> getAllDeputies();

    Deputy updateDeputy(Long deputyId, Deputy deputy);

    void deleteDeputyById(Long deputyId);

    void addDeputiesFromResourcesFolder(String folderName);
}
