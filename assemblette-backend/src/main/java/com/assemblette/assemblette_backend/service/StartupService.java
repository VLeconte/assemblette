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

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MandateService mandateService;

    @PostConstruct
    public void startup() {
        authorityService.addAuthoritysFromResourcesFolder(
                "tempForDatabase/AMO30_tous_acteurs_tous_mandats_tous_organes_historique.json/json/organe");
        deputyService.addDeputiesFromResourcesFolder(
                "tempForDatabase/AMO30_tous_acteurs_tous_mandats_tous_organes_historique.json/json/acteur");
        mandateService.addMandatesFromResourcesFolder(
                "tempForDatabase/AMO30_tous_acteurs_tous_mandats_tous_organes_historique.json/json/acteur");
        ballotService.addBallotsFromResourcesFolder("tempForDatabase/Scrutins/json");
        voteService.addVotesFromResourcesFolder("tempForDatabase/Scrutins/json");
    }
}
