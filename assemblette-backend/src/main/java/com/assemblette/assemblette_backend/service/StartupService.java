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

    @Autowired
    private VoteService voteService;

    @PostConstruct
    public void startup() {
        deputyService.addDeputiesFromResourcesFolder(
                "tempForDatabase/AMO30_tous_acteurs_tous_mandats_tous_organes_historique.json/json/acteur");
        ballotService.addBallotsFromResourcesFolder("tempForDatabase/Scrutins/json");
        voteService.addVotesFromResourcesFolder("tempForDatabase/Scrutins/json");
    }
}
