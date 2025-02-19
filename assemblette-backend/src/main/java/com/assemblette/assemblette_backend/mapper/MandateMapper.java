package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.MandateJson;
import com.assemblette.assemblette_backend.entity.Authority;
import com.assemblette.assemblette_backend.entity.Deputy;
import com.assemblette.assemblette_backend.entity.Mandate;

public class MandateMapper {
    public static Mandate mapToMandate(MandateJson mandateJson) {
        return Mandate.builder()
                .id(mandateJson.getId())
                .deputy(Deputy.builder().id(mandateJson.getDeputyId()).build())
                .authority(Authority.builder().id(mandateJson.getAuthorityId()).build())
                .startDate(mandateJson.getStartDate())
                .endDate(mandateJson.getEndDate())
                .build();
    }
}
