package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Mandate;

public interface MandateService {
    Mandate createMandate(Mandate mandate);

    Mandate getMandateById(String mandateId);

    List<Mandate> getAllMandates();

    Mandate updateMandate(String mandateId, Mandate mandate);

    void deleteMandateById(String mandateId);

    void addMandatesFromResourcesFolder(String folderName);
}