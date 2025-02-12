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
    private String prenom;
    @JsonProperty("Nom")
    private String nom;
    @JsonProperty("Région")
    private String region;
    @JsonProperty("Département")
    private String departement;
    @JsonProperty("Numéro de circonscription")
    private int numeroDeCirconscription;
    @JsonProperty("Profession")
    private String profession;
    @JsonProperty("Groupe politique (complet)")
    private String groupePolitiqueComplet;
    @JsonProperty("Groupe politique (abrégé)")
    private String groupePolitiqueAbrege;
}