package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.DeputyJson;
import com.assemblette.assemblette_backend.entity.Deputy;

public class DeputyMapper {
    public static Deputy mapToDeputy(DeputyJson deputyJson) {
        return new Deputy(
                deputyJson.getId(),
                deputyJson.getFirstName(),
                deputyJson.getLastName(),
                deputyJson.getProfession());
    }
}
