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
    private String id;
    @JsonProperty("Prénom")
    private String firstName;
    @JsonProperty("Nom")
    private String lastName;
    @JsonProperty("Profession")
    private String profession;
}