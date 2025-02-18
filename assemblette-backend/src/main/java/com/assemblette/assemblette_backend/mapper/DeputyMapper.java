package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.DeputyJsonDto;
import com.assemblette.assemblette_backend.entity.Deputy;

public class DeputyMapper {
    public static Deputy mapToDeputy(DeputyJsonDto deputyJsonDto) {
        return new Deputy(
                deputyJsonDto.getId(),
                deputyJsonDto.getFirstName(),
                deputyJsonDto.getLastName(),
                deputyJsonDto.getProfession());
    }
}
