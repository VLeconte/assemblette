package com.assemblette.assemblette_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeputyDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String region;
    private String department;
    private int constituencyNumber;
    private String profession;
    private String politicalGroupFull;
    private String politicalGroupAbbreviated;
}
