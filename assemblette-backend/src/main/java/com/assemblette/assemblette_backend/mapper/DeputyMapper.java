package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.DeputyDto;
import com.assemblette.assemblette_backend.dto.DeputyJsonDto;
import com.assemblette.assemblette_backend.entity.Deputy;

public class DeputyMapper {
    public static DeputyDto mapToDeputyDto(Deputy deputy) {
        return new DeputyDto(
                deputy.getId(),
                deputy.getFirstName(),
                deputy.getLastName(),
                deputy.getRegion(),
                deputy.getDepartment(),
                deputy.getConstituencyNumber(),
                deputy.getProfession(),
                deputy.getPoliticalGroupFull(),
                deputy.getPoliticalGroupAbbreviated());
    }

    public static Deputy mapToDeputy(DeputyDto deputyDto) {
        return new Deputy(
                deputyDto.getId(),
                deputyDto.getFirstName(),
                deputyDto.getLastName(),
                deputyDto.getRegion(),
                deputyDto.getDepartment(),
                deputyDto.getConstituencyNumber(),
                deputyDto.getProfession(),
                deputyDto.getPoliticalGroupFull(),
                deputyDto.getPoliticalGroupAbbreviated());
    }

    public static Deputy mapToDeputy(DeputyJsonDto deputyJsonDto) {
        return new Deputy(
                deputyJsonDto.getId(),
                deputyJsonDto.getFirstName(),
                deputyJsonDto.getLastName(),
                deputyJsonDto.getRegion(),
                deputyJsonDto.getDepartment(),
                deputyJsonDto.getConstituencyNumber(),
                deputyJsonDto.getProfession(),
                deputyJsonDto.getPoliticalGroupFull(),
                deputyJsonDto.getPoliticalGroupAbbreviated());
    }
}
