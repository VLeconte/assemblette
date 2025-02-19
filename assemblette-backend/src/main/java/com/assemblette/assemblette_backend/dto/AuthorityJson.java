package com.assemblette.assemblette_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityJson {
    @JsonProperty("uid")
    private String id;

    @JsonProperty("codeType")
    private String authorityType;

    @JsonProperty("libelle")
    private String label;

    @JsonProperty("libelleAbrev")
    private String labelAbbreviated;

    @JsonProperty("couleurAssociee")
    private String associatedColor;
}