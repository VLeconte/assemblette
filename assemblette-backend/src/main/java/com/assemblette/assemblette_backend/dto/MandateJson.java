package com.assemblette.assemblette_backend.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MandateJson {
    @JsonProperty("uid")
    private String id;

    @JsonProperty("acteurRef")
    private String deputyId;

    private String authorityId;

    @JsonProperty("dateDebut")
    private String startDate;

    @JsonProperty("dateFin")
    private String endDate;

    @JsonProperty("organes")
    private void unpackIdFromNestedObject(Map<String, String> organes) {
        authorityId = organes.get("organeRef");
    }
}
