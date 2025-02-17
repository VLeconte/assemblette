package com.assemblette.assemblette_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class StartupService {

    @Autowired
    private DeputyService deputyService;

    @Autowired
    private BallotService ballotService;

    @PostConstruct
    public void startup() {
        deputyService.addDeputiesFromResourcesFile("tempForDatabase/liste_deputes_libre_office.json");
        ballotService.addBallotsFromResourcesFile("tempForDatabase/Scrutins/json");
    }
}
