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
public class BallotJson {
    @JsonProperty("uid")
    private String id;
    @JsonProperty("titre")
    private String title;
    @JsonProperty("dateScrutin")
    private String ballotDate;
}
