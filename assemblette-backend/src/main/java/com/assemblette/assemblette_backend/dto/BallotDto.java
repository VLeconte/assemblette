package com.assemblette.assemblette_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BallotDto {
    private String id;
    private String title;
    private String ballotDate;
}
