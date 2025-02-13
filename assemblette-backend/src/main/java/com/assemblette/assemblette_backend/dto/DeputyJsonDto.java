package com.assemblette.assemblette_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeputyJsonDto {
    @JsonProperty("identifiant")
    private Long id;
    @JsonProperty("Prénom")
    private String firstName;
    @JsonProperty("Nom")
    private String lastName;
    @JsonProperty("Région")
    private String region;
    @JsonProperty("Département")
    private String department;
    @JsonProperty("Numéro de circonscription")
    private int constituencyNumber;
    @JsonProperty("Profession")
    private String profession;
    @JsonProperty("Groupe politique (complet)")
    private String politicalGroupFull;
    @JsonProperty("Groupe politique (abrégé)")
    private String politicalGroupAbbreviated;
}