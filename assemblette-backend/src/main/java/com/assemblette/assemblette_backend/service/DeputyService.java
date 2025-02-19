package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Deputy;

public interface DeputyService {
    Deputy createDeputy(Deputy deputy);

    Deputy getDeputyById(String deputyId);

    List<Deputy> getAllDeputies();

    Deputy updateDeputy(String deputyId, Deputy deputy);

    void deleteDeputyById(String deputyId);

    void addDeputiesFromResourcesFolder(String folderName);
}
