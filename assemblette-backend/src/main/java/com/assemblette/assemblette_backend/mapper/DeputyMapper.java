package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.DeputyDto;
import com.assemblette.assemblette_backend.dto.DeputyJsonDto;
import com.assemblette.assemblette_backend.entity.Deputy;

public class DeputyMapper {
    public static DeputyDto mapToDeputyDto(Deputy deputy) {
        return new DeputyDto(
                deputy.getId(),
                deputy.getPrenom(),
                deputy.getNom(),
                deputy.getRegion(),
                deputy.getDepartement(),
                deputy.getNumeroDeCirconscription(),
                deputy.getProfession(),
                deputy.getGroupePolitiqueComplet(),
                deputy.getGroupePolitiqueAbrege());
    }

    public static Deputy mapToDeputy(DeputyDto deputyDto) {
        return new Deputy(
                deputyDto.getId(),
                deputyDto.getPrenom(),
                deputyDto.getNom(),
                deputyDto.getRegion(),
                deputyDto.getDepartement(),
                deputyDto.getNumeroDeCirconscription(),
                deputyDto.getProfession(),
                deputyDto.getGroupePolitiqueComplet(),
                deputyDto.getGroupePolitiqueAbrege());
    }

    public static Deputy mapToDeputy(DeputyJsonDto deputyJsonDto) {
        return new Deputy(
                deputyJsonDto.getId(),
                deputyJsonDto.getPrenom(),
                deputyJsonDto.getNom(),
                deputyJsonDto.getRegion(),
                deputyJsonDto.getDepartement(),
                deputyJsonDto.getNumeroDeCirconscription(),
                deputyJsonDto.getProfession(),
                deputyJsonDto.getGroupePolitiqueComplet(),
                deputyJsonDto.getGroupePolitiqueAbrege());
    }
}
